package kr.co.tbell.mm.service.employee;

import kr.co.tbell.mm.dto.employee.ReqUpdateEmployee;
import kr.co.tbell.mm.dto.employee.ResEmployee;
import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;
import kr.co.tbell.mm.dto.salary.EmployeeSalary;
import kr.co.tbell.mm.dto.salary.ReqUpdateSalary;
import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.entity.salary.Salary;
import kr.co.tbell.mm.repository.employee.EmployeeRepository;
import kr.co.tbell.mm.repository.salary.SalaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;

    @Override
    public ResCreateEmployee createEmployee(ReqCreateEmployee createEmployee) throws InstanceAlreadyExistsException {

        Optional<Employee> existEmployee =
                employeeRepository.getEmployeeByEmployeeNumber(createEmployee.getEmployeeNumber());

        if (existEmployee.isPresent()) {
            throw new InstanceAlreadyExistsException("Employee already exist with this employeeNumber : "
                    + createEmployee.getEmployeeNumber());
        }

        Employee employee = Employee.createEmployee(
                createEmployee.getEmployeeNumber(),
                createEmployee.getName(),
                createEmployee.getStartDate(),
                createEmployee.getResignationDate());

        employeeRepository.save(employee);

        return ResCreateEmployee
                .builder()
                .employeeNumber(createEmployee.getEmployeeNumber())
                .name(createEmployee.getName())
                .build();
    }

    @Override
    public Page<ResEmployee> findAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);

        return employees.map(ResEmployee::new);
    }

    @Override
    public ResEmployee findEmployee(String employeeNumber) {
        Optional<Employee> optionalEmployee =
                employeeRepository.getEmployeeByEmployeeNumber(employeeNumber);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            return new ResEmployee(employee);
        }
        return null;
    }

    @Override
    public ResEmployee deleteEmployee(String employeeNumber) {
        Optional<Employee> optionalEmployee =
                employeeRepository.getEmployeeByEmployeeNumber(employeeNumber);

        if (optionalEmployee.isEmpty()) return null;

        Employee employee = optionalEmployee.get();
        ResEmployee resEmployee = new ResEmployee(employee);

        employeeRepository.delete(employee);

        return resEmployee;
    }

    @Override
    public ResEmployee editEmployee(String employeeNumber, ReqUpdateEmployee reqUpdateEmployee) {
        Optional<Employee> optionalEmployee =
                employeeRepository.getEmployeeByEmployeeNumber(employeeNumber);

        if (optionalEmployee.isEmpty()) return null;

        Employee employee = optionalEmployee.get();

        // Dirty checking
        employee.updateEmployee(
                reqUpdateEmployee.getEmployeeNumber(),
                reqUpdateEmployee.getName(),
                reqUpdateEmployee.getStartDate(),
                reqUpdateEmployee.getResignationDate());

        return new ResEmployee(employee);
    }

    @Override
    public EmployeeSalary addMonthSalary(String employeeNumber, ReqUpdateSalary reqUpdateSalary) {
        Optional<Employee> optionalEmployee = employeeRepository.getEmployeeByEmployeeNumber(employeeNumber);

        if (optionalEmployee.isEmpty())
            throw new NoSuchElementException("Employee with this employee number '"
                    + employeeNumber + "' does not exist.");

        Employee employee = optionalEmployee.get();

        Optional<Salary> salaryByEmployee = salaryRepository.findByEmployeeAndYearAndMonth(
                employee,
                reqUpdateSalary.getYear(),
                reqUpdateSalary.getMonth());

        if (salaryByEmployee.isEmpty()) {
            Salary salary = Salary
                    .builder()
                    .employee(employee)
                    .year(reqUpdateSalary.getYear())
                    .month(reqUpdateSalary.getMonth())
                    .build();
            salaryRepository.save(salary);
        } else {
            salaryByEmployee.get().changeSalary(
                    reqUpdateSalary.getYear(),
                    reqUpdateSalary.getMonth(),
                    reqUpdateSalary.getSalary());
        }

        return new EmployeeSalary(
                employeeNumber,
                employee.getName(),
                reqUpdateSalary.getYear(),
                reqUpdateSalary.getMonth(),
                reqUpdateSalary.getSalary());
    }
}
