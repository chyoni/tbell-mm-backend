package kr.co.tbell.mm.service.history;

import kr.co.tbell.mm.dto.history.*;
import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.entity.EmployeeHistory;
import kr.co.tbell.mm.entity.EmployeeHistoryMM;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.UnitPrice;
import kr.co.tbell.mm.repository.employeehistory.EmployeeHistoryMMRepository;
import kr.co.tbell.mm.repository.employeehistory.EmployeeHistoryRepository;
import kr.co.tbell.mm.repository.employee.EmployeeRepository;
import kr.co.tbell.mm.repository.project.ProjectRepository;
import kr.co.tbell.mm.repository.unitprice.UnitPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.directory.InvalidAttributesException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {

    private final EmployeeHistoryRepository employeeHistoryRepository;
    private final EmployeeHistoryMMRepository employeeHistoryMMRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final UnitPriceRepository unitPriceRepository;

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

        List<EmployeeHistoryMM> manMonthEntities = getEmployeeHistoryManMonthList(history, employeeHistory);

        employeeHistoryMMRepository.saveAll(manMonthEntities);

        // 사원의 종료일이 결정되는 순간 종료일 기준으로 이후 엔티티는 날려야한다.
        //  - 종료일이 결정되면 종료일에 해당하는 월은 투입 MM이 업데이트 되어야 한다.
        // 사원의 종료일이 결정되기 전까지는 월마다 스케쥴러를 통해 엔티티를 하나씩 만들어내는 배치를 만들어야 한다.
        // 사원의 정산 MM이 입력되는 요청이 들어오면 정산 금액과 손익액에 대한 업데이트 역시 일어나야 한다.

        return new ResHistory(project, pUnitPrices, employee, employeeHistory);
    }

    /**
     * history.getStartDate()를 가지고 현재 시점까지 월별로 엔티티 만들어 내야한다.
     * */
    private List<EmployeeHistoryMM> getEmployeeHistoryManMonthList(ReqHistory history,
                                                                   EmployeeHistory employeeHistory) {
       List<EmployeeHistoryMM> manMonthEntities = new ArrayList<>();

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

            int durationDay = manMonthStart.until(manMonthEnd).getDays() + 1;
            int dayOfMonth = manMonthEnd.getDayOfMonth();

            double inputManMonth = (double) durationDay / dayOfMonth;

            String inputManMonthToString = String.format("%.2f", inputManMonth);

            EmployeeHistoryMM employeeHistoryMM = EmployeeHistoryMM
                    .builder()
                    .year(manMonthStart.getYear())
                    .month(manMonthStart.getMonthValue())
                    .durationStart(manMonthStart)
                    .durationEnd(manMonthEnd)
                    .inputMM(inputManMonthToString)
                    .calculateLevel(history.getLevel())
                    .employeeHistory(employeeHistory)
                    .build();

            manMonthEntities.add(employeeHistoryMM);
        }

        return manMonthEntities;
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

        return new ResHistory(employeeHistory, employeeHistory.getEmployee());
    }

    /**
     * QueryDSL
     * */
    @Override
    public Page<ResHistory> getHistories(Pageable pageable, HistorySearchCond searchCond) {
        Page<ResHistory> histories = employeeHistoryRepository.getHistories(pageable, searchCond);

        for (ResHistory history : histories) {
            List<ResHistoryMM> mms = employeeHistoryMMRepository.getHistoriesMM(history.getId(), searchCond);
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
}
