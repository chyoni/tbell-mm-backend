package kr.co.tbell.mm.service;

import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;
import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

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
}
