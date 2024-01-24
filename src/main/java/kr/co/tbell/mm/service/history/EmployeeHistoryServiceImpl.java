package kr.co.tbell.mm.service.history;

import kr.co.tbell.mm.dto.history.ReqCompleteHistory;
import kr.co.tbell.mm.dto.history.ReqHistory;
import kr.co.tbell.mm.dto.history.ResHistory;
import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.entity.EmployeeHistory;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.UnitPrice;
import kr.co.tbell.mm.repository.EmployeeHistoryRepository;
import kr.co.tbell.mm.repository.EmployeeRepository;
import kr.co.tbell.mm.repository.ProjectRepository;
import kr.co.tbell.mm.repository.UnitPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.directory.InvalidAttributesException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {

    private final EmployeeHistoryRepository employeeHistoryRepository;
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

        Optional<EmployeeHistory> optionalEmployeeHistory =
                employeeHistoryRepository.findByEmployeeAndProject(employee, project);

        if (optionalEmployeeHistory.isPresent() && optionalEmployeeHistory.get().getEndDate() != null)
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

        return new ResHistory(project, pUnitPrices, employee, employeeHistory);
    }

    @Override
    public ResHistory completeHistory(Long id, ReqCompleteHistory reqCompleteHistory) throws InvalidAttributesException {
        Optional<EmployeeHistory> optionalEmployeeHistory = employeeHistoryRepository.findById(id);

        if (optionalEmployeeHistory.isEmpty())
            throw new NoSuchElementException("Employee history with this id ["+ id +"] does not exist.");

        EmployeeHistory employeeHistory = optionalEmployeeHistory.get();

        if (employeeHistory.getStartDate().isAfter(reqCompleteHistory.getEndDate()))
            throw new InvalidAttributesException("'endDate' must be after than 'startDate'");

        employeeHistory.completeHistory(reqCompleteHistory.getEndDate());

        return new ResHistory(employeeHistory, employeeHistory.getEmployee());
    }
}
