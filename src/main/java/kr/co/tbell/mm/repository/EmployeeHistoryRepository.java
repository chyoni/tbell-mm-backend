package kr.co.tbell.mm.repository;

import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.entity.EmployeeHistory;
import kr.co.tbell.mm.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, Long> {
    Optional<EmployeeHistory> findByEmployeeAndProject(Employee employee, Project project);

    @Override
    @Query(value = "SELECT eh FROM EmployeeHistory eh JOIN FETCH eh.employee WHERE eh.id")
    Optional<EmployeeHistory> findById(Long id);
}
