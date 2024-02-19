package kr.co.tbell.mm.repository.employeehistory;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.history.HistorySearchCond;
import kr.co.tbell.mm.dto.history.QResHistory;
import kr.co.tbell.mm.dto.history.ResHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


import java.time.LocalDate;
import java.util.List;

import static kr.co.tbell.mm.entity.QDepartment.department;
import static kr.co.tbell.mm.entity.QEmployee.employee;
import static kr.co.tbell.mm.entity.QEmployeeHistory.employeeHistory;
import static kr.co.tbell.mm.entity.project.QProject.project;

@Slf4j
@RequiredArgsConstructor
public class EmployeeHistoryRepositoryImpl implements EmployeeHistoryRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ResHistory> getHistories(Pageable pageable, HistorySearchCond searchCond) {

        List<ResHistory> results = queryFactory
                .select(new QResHistory(project, employee, employeeHistory))
                .from(employeeHistory)
                .leftJoin(employeeHistory.employee, employee)
                .leftJoin(employeeHistory.project, project)
                .leftJoin(employeeHistory.project.department, department)
                .where(contractNumberEq(searchCond.getContractNumber()),
                        employeeNumberEq(searchCond.getEmployeeNumber()),
                        employeeNameEq(searchCond.getEmployeeName()),
                        dateBetween(searchCond.getStartDate(), searchCond.getEndDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(employeeHistory.createdDate.desc())
                .fetch();

        int total = queryFactory
                .select(employeeHistory)
                .from(employeeHistory)
                .leftJoin(employeeHistory.employee, employee)
                .leftJoin(employeeHistory.project, project)
                .leftJoin(employeeHistory.project.department, department)
                .where(contractNumberEq(searchCond.getContractNumber()),
                        employeeNumberEq(searchCond.getEmployeeNumber()),
                        employeeNameEq(searchCond.getEmployeeName()),
                        dateBetween(searchCond.getStartDate(), searchCond.getEndDate()))
                .fetch()
                .size();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression employeeNameEq(String employeeName) {
        return StringUtils.hasText(employeeName) ? employeeHistory.employee.name.eq(employeeName) : null;
    }

    private BooleanExpression durationByYear(String year) {
        if (year == null) return null;

        LocalDate start = LocalDate.of(Integer.parseInt(year), 1, 1);
        LocalDate end = LocalDate.of(Integer.parseInt(year), 12, 31);

        return employeeHistory.startDate.between(start, end);
    }

    private BooleanExpression dateBetween(LocalDate startDate, LocalDate endDate) {

        BooleanExpression startDateGoe = startDate != null ? employeeHistory.startDate.goe(startDate) : null;
        BooleanExpression endDateLoe = endDate != null ? employeeHistory.endDate.loe(endDate) : null;

        return Expressions.allOf(startDateGoe, endDateLoe);
    }

    private OrderSpecifier<?> startDateOrderBy(String orderBy) {
        return StringUtils.hasText(orderBy) ?
                orderBy.equalsIgnoreCase("ASC") ?
                        employeeHistory.startDate.asc() :
                        employeeHistory.startDate.desc() :
                employeeHistory.startDate.asc();
    }

    private BooleanExpression employeeNumberEq(String employeeNumber) {
        return StringUtils.hasText(employeeNumber) ? employee.employeeNumber.eq(employeeNumber) : null;
    }

    private BooleanExpression contractNumberEq(String contractNumber) {
        return StringUtils.hasText(contractNumber) ? project.contractNumber.eq(contractNumber) : null;
    }
}
