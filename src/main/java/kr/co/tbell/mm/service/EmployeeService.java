package kr.co.tbell.mm.service;

import kr.co.tbell.mm.dto.employee.ResEmployee;
import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.InstanceAlreadyExistsException;
import java.util.Optional;

public interface EmployeeService {
    ResCreateEmployee createEmployee(ReqCreateEmployee createEmployee) throws InstanceAlreadyExistsException;

    Page<ResEmployee> findAllEmployees(Pageable pageable);

    ResEmployee findEmployee(String employeeNumber);

    ResEmployee deleteEmployee(String employeeNumber);
}
