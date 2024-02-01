package kr.co.tbell.mm.repository.employee;

import kr.co.tbell.mm.dto.employee.EmployeeSearchCond;
import kr.co.tbell.mm.dto.employee.ResEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepositoryQueryDsl {
    Page<ResEmployee> getEmployees(Pageable pageable, EmployeeSearchCond employeeSearchCond);
}
