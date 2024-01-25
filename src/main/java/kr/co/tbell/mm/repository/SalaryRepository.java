package kr.co.tbell.mm.repository;

import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.entity.salary.Month;
import kr.co.tbell.mm.entity.salary.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findByEmployeeAndYearAndMonth(Employee employee, Integer year, Month month);
}
