package kr.co.tbell.mm.repository.employee;

import kr.co.tbell.mm.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends
        JpaRepository<Employee, Long>,
        EmployeeRepositoryQueryDsl,
        QuerydslPredicateExecutor<Employee> {
    Optional<Employee> getEmployeeByEmployeeNumber(String employeeNumber);
}
