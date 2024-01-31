package kr.co.tbell.mm.repository.department;

import kr.co.tbell.mm.dto.department.DepartmentSearchCond;
import kr.co.tbell.mm.dto.department.ResDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentRepositoryQueryDsl {
    Page<ResDepartment> getDepartments(Pageable pageable, DepartmentSearchCond departmentSearchCond);
}
