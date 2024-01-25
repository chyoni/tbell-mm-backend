package kr.co.tbell.mm.service.employee;

import kr.co.tbell.mm.dto.employee.ReqUpdateEmployee;
import kr.co.tbell.mm.dto.employee.ResEmployee;
import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;
import kr.co.tbell.mm.dto.salary.EmployeeSalary;
import kr.co.tbell.mm.dto.salary.ReqUpdateSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.InstanceAlreadyExistsException;

public interface EmployeeService {
    ResCreateEmployee createEmployee(ReqCreateEmployee createEmployee) throws InstanceAlreadyExistsException;

    Page<ResEmployee> findAllEmployees(Pageable pageable);

    ResEmployee findEmployee(String employeeNumber);

    ResEmployee deleteEmployee(String employeeNumber);

    ResEmployee editEmployee(String employeeNumber, ReqUpdateEmployee ReqUpdateEmployee);

    EmployeeSalary addMonthSalary(String employeeNumber, ReqUpdateSalary reqUpdateSalary);
}
