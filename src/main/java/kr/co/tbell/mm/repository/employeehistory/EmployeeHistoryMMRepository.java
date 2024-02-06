package kr.co.tbell.mm.repository.employeehistory;

import kr.co.tbell.mm.entity.EmployeeHistoryMM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeHistoryMMRepository extends
        JpaRepository<EmployeeHistoryMM, Long>,
        EmployeeHistoryMMRepositoryQueryDsl,
        QuerydslPredicateExecutor<EmployeeHistoryMM> {
}
