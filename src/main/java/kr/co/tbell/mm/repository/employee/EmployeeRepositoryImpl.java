package kr.co.tbell.mm.repository.employee;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.employee.EmployeeSearchCond;
import kr.co.tbell.mm.dto.employee.QResEmployee;
import kr.co.tbell.mm.dto.employee.ResEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static kr.co.tbell.mm.entity.employee.QEmployee.employee;

@Slf4j
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ResEmployee> getEmployees(Pageable pageable, EmployeeSearchCond employeeSearchCond) {

        List<ResEmployee> results = queryFactory
                .select(new QResEmployee(employee))
                .from(employee)
                .where(employeeNumberEq(employeeSearchCond.getEmployeeNumber()),
                        employeeNameContains(employeeSearchCond.getName()),
                        employeeStartDateEq(employeeSearchCond.getStartDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(employee.employeeNumber.asc())
                .fetch();

        int total = queryFactory
                .select(new QResEmployee(employee))
                .from(employee)
                .where(employeeNumberEq(employeeSearchCond.getEmployeeNumber()),
                        employeeNameContains(employeeSearchCond.getName()),
                        employeeStartDateEq(employeeSearchCond.getStartDate()))
                .fetch()
                .size();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression employeeStartDateEq(LocalDate startDate) {
        if (startDate == null) return null;

        return employee.startDate.eq(startDate);
    }

    private BooleanExpression employeeNameContains(String name) {
        return StringUtils.hasText(name) ? employee.name.contains(name) : null;
    }

    private BooleanExpression employeeNumberEq(String employeeNumber) {
        return StringUtils.hasText(employeeNumber) ? employee.employeeNumber.eq(employeeNumber) : null;
    }
}
