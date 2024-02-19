package kr.co.tbell.mm.repository.employeehistory;

import kr.co.tbell.mm.entity.EmployeeHistory;
import kr.co.tbell.mm.entity.EmployeeHistoryManMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeHistoryMMRepository extends
        JpaRepository<EmployeeHistoryManMonth, Long>,
        EmployeeHistoryMMRepositoryQueryDsl,
        QuerydslPredicateExecutor<EmployeeHistoryManMonth> {

    List<EmployeeHistoryManMonth> findAllByEmployeeHistory(EmployeeHistory employeeHistory);
}
