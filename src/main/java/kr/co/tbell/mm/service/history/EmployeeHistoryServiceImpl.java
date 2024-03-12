package kr.co.tbell.mm.service.history;

import kr.co.tbell.mm.dto.history.*;
import kr.co.tbell.mm.dto.salary.ReqUpdateSalary;
import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.entity.EmployeeHistory;
import kr.co.tbell.mm.entity.EmployeeHistoryManMonth;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.UnitPrice;
import kr.co.tbell.mm.entity.salary.Month;
import kr.co.tbell.mm.entity.salary.Salary;
import kr.co.tbell.mm.repository.employeehistory.EmployeeHistoryMMRepository;
import kr.co.tbell.mm.repository.employeehistory.EmployeeHistoryRepository;
import kr.co.tbell.mm.repository.employee.EmployeeRepository;
import kr.co.tbell.mm.repository.project.ProjectRepository;
import kr.co.tbell.mm.repository.salary.SalaryRepository;
import kr.co.tbell.mm.repository.unitprice.UnitPriceRepository;
import kr.co.tbell.mm.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.directory.InvalidAttributesException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {

    private final EmployeeHistoryRepository employeeHistoryRepository;
    private final EmployeeHistoryMMRepository employeeHistoryMMRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final UnitPriceRepository unitPriceRepository;
    private final SalaryRepository salaryRepository;
    private final EmployeeService employeeService;

    @Override
    public ResHistory makeHistory(ReqHistory history)
            throws InstanceAlreadyExistsException, InvalidAttributesException {
        Optional<Employee> optionalEmployee =
                employeeRepository.getEmployeeByEmployeeNumber(history.getEmployeeNumber());

        if (optionalEmployee.isEmpty())
            throw new NoSuchElementException("Employee with this employee number '" +
                    history.getEmployeeNumber() + "' does not exist.");

        Employee employee = optionalEmployee.get();

        Optional<Project> optionalProject = projectRepository.findByContractNumber(history.getContractNumber());

        if (optionalProject.isEmpty())
            throw new NoSuchElementException("Project with this contract number '" +
                    history.getContractNumber() + "' does not exist.");

        Project project = optionalProject.get();

        if (project.getStartDate().isAfter(history.getStartDate()) ||
                (history.getEndDate() != null && project.getEndDate().isBefore(history.getEndDate()))) {
            throw new InvalidAttributesException("Employee input date must be between project start and end date.");
        }

        Optional<EmployeeHistory> optionalEmployeeHistory =
                employeeHistoryRepository.findByEmployeeAndProject(employee, project);

        if (optionalEmployeeHistory.isPresent() && optionalEmployeeHistory.get().getEndDate() == null)
            throw new InstanceAlreadyExistsException("Employee history with employee [" +
                    employee.getName() + "] and project [" + project.getTeamName() + "] already exist.");

        Integer worth = null;
        List<Map<Level, Integer>> pUnitPrices = new ArrayList<>();
        List<UnitPrice> unitPricesByProject = unitPriceRepository.findAllByProject(project);

        for (UnitPrice unitPrice : unitPricesByProject) {
            if (unitPrice.getLevel().equals(history.getLevel())) {
                  worth = unitPrice.getWorth();
            }

            pUnitPrices.add(Map.of(unitPrice.getLevel(), unitPrice.getWorth()));
        }

        if (worth == null) {
            throw new InvalidAttributesException("The 'level' ["+history.getLevel()+"] " +
                    "received is not in this project ["+project.getTeamName()+"].");
        }

        EmployeeHistory employeeHistory = EmployeeHistory
                .builder()
                .employee(employee)
                .project(project)
                .startDate(history.getStartDate())
                .endDate(history.getEndDate())
                .level(history.getLevel())
                .worth(worth)
                .build();

        employeeHistoryRepository.save(employeeHistory);

        List<EmployeeHistoryManMonth> manMonthEntities = getEmployeeHistoryManMonthList(history, employeeHistory);

        for (EmployeeHistoryManMonth mm : manMonthEntities) {
            Optional<Salary> byEmployeeAndYearAndMonth = salaryRepository.findByEmployeeAndYearAndMonth(
                    mm.getEmployeeHistory().getEmployee(),
                    mm.getYear(),
                    Month.convert(mm.getMonth()));

            if (byEmployeeAndYearAndMonth.isPresent()) {
                mm.changeMonthSalary(byEmployeeAndYearAndMonth.get().getSalary());
                mm.applyInputPrice();
            }
        }

        employeeHistoryMMRepository.saveAll(manMonthEntities);

        return new ResHistory(project, pUnitPrices, employee, employeeHistory);
    }

    @Override
    public ResHistory completeHistory(Long id, ReqCompleteHistory reqCompleteHistory) throws
            InvalidAttributesException {
        Optional<EmployeeHistory> optionalEmployeeHistory = employeeHistoryRepository.findById(id);

        if (optionalEmployeeHistory.isEmpty())
            throw new NoSuchElementException("Employee history with this id ["+ id +"] does not exist.");

        EmployeeHistory employeeHistory = optionalEmployeeHistory.get();

        if (employeeHistory.getStartDate().isAfter(reqCompleteHistory.getEndDate()))
            throw new InvalidAttributesException("'endDate' must be after than 'startDate'");

        employeeHistory.completeHistory(reqCompleteHistory.getEndDate());

        // 종료일이 지정됐을 때 기존에 존재하는 MM 데이터 업데이트
        List<EmployeeHistoryManMonth> mms = employeeHistoryMMRepository.findAllByEmployeeHistory(employeeHistory);
        for (EmployeeHistoryManMonth mm : mms) {
            if (mm.getYear() > reqCompleteHistory.getEndDate().getYear()) {
                employeeHistoryMMRepository.delete(mm);
                continue;
            }

            if (mm.getYear().equals(reqCompleteHistory.getEndDate().getYear())) {

                if (mm.getMonth() > reqCompleteHistory.getEndDate().getMonthValue()) {
                    employeeHistoryMMRepository.delete(mm);
                    continue;
                }

                if (mm.getMonth().equals(reqCompleteHistory.getEndDate().getMonthValue())) {
                    mm.changeDurationEndAndInputManMonth(reqCompleteHistory.getEndDate());
                }
            }
        }

        return new ResHistory(employeeHistory, employeeHistory.getEmployee());
    }

    /**
     * QueryDSL
     * */
    @Override
    public Page<ResHistory> getHistories(Pageable pageable, HistorySearchCond searchCond) {
        Page<ResHistory> histories = employeeHistoryRepository.getHistories(pageable, searchCond);

        for (ResHistory history : histories) {
            List<ResHistoryManMonth> mms = employeeHistoryMMRepository.getHistoriesMM(history.getId(), searchCond);
            history.setMms(mms);
        }

        return histories;
    }

    @Override
    public Page<ResHistory> getHistoriesByProject(Pageable pageable, String contractNumber) {
        Page<EmployeeHistory> histories = employeeHistoryRepository.findAllByProject(pageable, contractNumber);

        return getResHistories(histories);
    }

    @Override
    public Page<ResHistory> getHistoriesByEmployee(Pageable pageable, String employeeNumber) {
        Page<EmployeeHistory> histories = employeeHistoryRepository.findAllByEmployee(pageable, employeeNumber);

        return getResHistories(histories);
    }

    @Override
    public Void saveManMonthsByHistoryId(Long historyId, List<ReqHistoryManMonth> mms) {
        Optional<EmployeeHistory> optionalHistory = employeeHistoryRepository.findById(historyId);

        if (optionalHistory.isEmpty())
            throw new NoSuchElementException("History with this id: " + historyId + " does not exist.");

        // Payload로 들어오는 MM 데이터에 대한 반복문
        for (ReqHistoryManMonth mm : mms) {
            Optional<EmployeeHistoryManMonth> optionalManMonth = employeeHistoryMMRepository.findById(mm.getId());
            if (optionalManMonth.isEmpty())
                throw new NoSuchElementException("ManMonth data with this id: " + mm.getId() + "does not exist.");

            // MM 데이터에 있는 월 급여 데이터를 받아서 데이터베이스에 저장하기 위한 엔티티
            ReqUpdateSalary reqUpdateSalary = ReqUpdateSalary.builder()
                    .year(mm.getYear())
                    .month(Month.convert(mm.getMonth()))
                    .salary(mm.getMonthSalary())
                    .build();

            // 월 급여 데이터 저장
            try {
                employeeService.addMonthSalary(
                        optionalHistory.get().getEmployee().getEmployeeNumber(),
                        reqUpdateSalary);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException(e.getMessage());
            }

            // MM 데이터의 업데이트 데이터 반영
            EmployeeHistoryManMonth manMonth = optionalManMonth.get();
            // Dirty checking
            manMonth.updateManMonth(
                    mm.getYear(),
                    mm.getMonth(),
                    mm.getDurationStart(),
                    mm.getDurationEnd(),
                    mm.getInputManMonth(),
                    mm.getMonthSalary(),
                    mm.getInputPrice(),
                    mm.getCalculateManMonth(),
                    mm.getCalculateLevel(),
                    mm.getCalculatePrice(),
                    mm.getPlPrice());
        }
        return null;
    }

    @Override
    public List<ResHistoryStatistics> getHistoryStatistics(String year) {
        return employeeHistoryMMRepository.getHistoryStatistics(year);
    }

    @Override
    public List<ResContractHistoryStatistics> getContractHistoryStatistics(String contractNumber, String year) {
        return employeeHistoryMMRepository.getContractHistoryStatistics(contractNumber, year);
    }

    /**
     * history.getStartDate()를 가지고 현재 시점까지 월별로 엔티티 만들어 내야한다.
     * */
    private List<EmployeeHistoryManMonth> getEmployeeHistoryManMonthList(ReqHistory history,
                                                                         EmployeeHistory employeeHistory) {
        List<EmployeeHistoryManMonth> manMonthEntities = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(history.getStartDate(), currentDate);

        for (int i = 0; i <= period.toTotalMonths() + 1; i++) {
            LocalDate manMonthStart, manMonthEnd;

            if (i == 0) {
                manMonthStart = history.getStartDate().plusMonths(i);
            } else {
                LocalDate mmDate = history.getStartDate().plusMonths(i);
                manMonthStart = LocalDate.of(mmDate.getYear(), mmDate.getMonth(), 1);
            }
            manMonthEnd = manMonthStart.with(TemporalAdjusters.lastDayOfMonth());

            if (manMonthStart.isAfter(LocalDate.now())) break;

            int durationDay = manMonthStart.until(manMonthEnd).getDays() + 1;
            int dayOfMonth = manMonthEnd.getDayOfMonth();

            double inputManMonth = (double) durationDay / dayOfMonth;

            String inputManMonthToString = String.format("%.2f", inputManMonth);

            EmployeeHistoryManMonth employeeHistoryManMonth = EmployeeHistoryManMonth
                    .builder()
                    .year(manMonthStart.getYear())
                    .month(manMonthStart.getMonthValue())
                    .durationStart(manMonthStart)
                    .durationEnd(manMonthEnd)
                    .inputManMonth(inputManMonthToString)
                    .calculateLevel(history.getLevel())
                    .employeeHistory(employeeHistory)
                    .build();

            manMonthEntities.add(employeeHistoryManMonth);
        }

        return manMonthEntities;
    }

    private Page<ResHistory> getResHistories(Page<EmployeeHistory> histories) {
        return histories.map(history -> {
            List<Map<Level, Integer>> unitPrices = new ArrayList<>();

            List<UnitPrice> unitPriceByProject = unitPriceRepository.findAllByProject(history.getProject());

            for (UnitPrice unitPrice : unitPriceByProject) {
                unitPrices.add(Map.of(unitPrice.getLevel(), unitPrice.getWorth()));
            }

            return new ResHistory(history.getProject(), unitPrices, history.getEmployee(), history);
        });
    }

    /**
     * 매월 1일 00:00분에 EmployeeHistory 데이터 전체를 가져온 후, MM 엔티티를 추가해야 하는 경우 추가하는 배치 스케쥴러. <br>
     * MM 엔티티를 추가해야 하는 경우는 다음과 같다. <br>
     * 1. 투입 종료일이 지정되지 않은 EmployeeHistory 데이터인 경우 = 계속 투입되는 상태를 의미 <br>
     * 2. 투입 종료일이 지정됐지만 이번달 포함이거나 이번달 이후인 경우 = 투입 종료일까지의 MM 데이터가 있어야 하므로 <br>
     * */
    @Override
    @Scheduled(cron = "0 0 0 1 * *")
    public void intervalHistoryScheduler() {
        log.info("[intervalHistoryScheduler] EmployeeHistory Entity Scheduler Starting. {}",
                LocalDate.now().getMonth());

        List<EmployeeHistory> histories = employeeHistoryRepository.findAll();

        for (EmployeeHistory history : histories) {

            LocalDate manMonthEnd = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

            if (history.getEndDate() != null) {
                if (LocalDate.now().isAfter(history.getEndDate()) || LocalDate.now().isEqual(history.getEndDate()))
                    continue;

                manMonthEnd = history.getEndDate();
            }

            int durationDay = LocalDate.now().until(manMonthEnd).getDays() + 1;
            int dayOfMonth = manMonthEnd.getDayOfMonth();

            double inputManMonth = (double) durationDay / dayOfMonth;

            String inputManMonthToString = String.format("%.2f", inputManMonth);

            EmployeeHistoryManMonth manMonth = EmployeeHistoryManMonth.builder()
                    .year(LocalDate.now().getYear())
                    .month(LocalDate.now().getMonthValue())
                    .durationStart(LocalDate.now())
                    .durationEnd(manMonthEnd)
                    .inputManMonth(inputManMonthToString)
                    .calculateLevel(history.getLevel())
                    .employeeHistory(history)
                    .build();

            employeeHistoryMMRepository.save(manMonth);
        }
    }
}
