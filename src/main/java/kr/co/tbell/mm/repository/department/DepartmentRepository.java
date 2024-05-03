package kr.co.tbell.mm.repository.department;

import kr.co.tbell.mm.entity.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends
        JpaRepository<Department, Long>,
        DepartmentRepositoryQueryDsl,
        QuerydslPredicateExecutor<Department> {
    Optional<Department> findByName(String name);
}
