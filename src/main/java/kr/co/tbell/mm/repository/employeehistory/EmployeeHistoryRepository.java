package kr.co.tbell.mm.repository.employeehistory;

import kr.co.tbell.mm.entity.Employee;
import kr.co.tbell.mm.entity.EmployeeHistory;
import kr.co.tbell.mm.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeHistoryRepository extends
        JpaRepository<EmployeeHistory, Long>,
        EmployeeHistoryRepositoryQueryDsl,
        QuerydslPredicateExecutor<EmployeeHistory> {
    Optional<EmployeeHistory> findByEmployeeAndProject(Employee employee, Project project);

    @Override
    @Query(value = "SELECT eh FROM EmployeeHistory eh JOIN FETCH eh.employee WHERE eh.id = :id")
    Optional<EmployeeHistory> findById(@Param("id") Long id);

    @Query(value = "" +
            "SELECT eh " +
            "FROM EmployeeHistory eh " +
            "JOIN FETCH eh.project p " +
            "JOIN FETCH eh.employee e " +
            "LEFT OUTER JOIN FETCH p.department pd " +
            "WHERE p.contractNumber = :contractNumber")
    Page<EmployeeHistory> findAllByProject(Pageable pageable, @Param("contractNumber") String contractNumber);

    @Query(value = "" +
            "SELECT eh " +
            "FROM EmployeeHistory eh " +
            "JOIN FETCH eh.project p " +
            "JOIN FETCH eh.employee e " +
            "LEFT OUTER JOIN FETCH p.department pd " +
            "WHERE e.employeeNumber = :employeeNumber")
    Page<EmployeeHistory> findAllByEmployee(Pageable pageable, @Param("employeeNumber") String employeeNumber);

    Iterable<EmployeeHistory> findAllByEmployee(Employee employee);

    @Override
    @Query(value = "" +
            "SELECT eh " +
            "FROM EmployeeHistory eh " +
            "JOIN FETCH eh.project p " +
            "JOIN FETCH eh.employee e " +
            "LEFT OUTER JOIN FETCH p.department pd")
    Page<EmployeeHistory> findAll(Pageable pageable);
}
