package kr.co.tbell.mm.repository;

import kr.co.tbell.mm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> getEmployeeByEmployeeNumber(String employeeNumber);
}
