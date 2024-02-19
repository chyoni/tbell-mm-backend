package kr.co.tbell.mm.repository.employeehistory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.history.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

import static kr.co.tbell.mm.entity.QEmployeeHistoryManMonth.employeeHistoryManMonth;

@Slf4j
@RequiredArgsConstructor
public class EmployeeHistoryMMRepositoryImpl implements EmployeeHistoryMMRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ResHistoryManMonth> getHistoriesMM(Long employeeHistoryId, HistorySearchCond searchCond) {

        return queryFactory
                .select(new QResHistoryManMonth(employeeHistoryManMonth))
                .from(employeeHistoryManMonth)
                .where(findByHistoryId(employeeHistoryId),
                        findByYear(searchCond.getYear()))
                .orderBy(employeeHistoryManMonth.durationStart.asc())
                .fetch();
    }

    @Override
    public List<ResHistoryStatistics> getHistoryStatistics(String year) {
        return queryFactory
                .select(new QResHistoryStatistics(
                        employeeHistoryManMonth.month,
                        employeeHistoryManMonth.inputManMonth.castToNum(Double.class).sum(),
                        employeeHistoryManMonth.inputPrice.sum(),
                        employeeHistoryManMonth.calculateManMonth.castToNum(Double.class).sum(),
                        employeeHistoryManMonth.calculatePrice.sum()))
                .from(employeeHistoryManMonth)
                .where(findByYear(year))
                .groupBy(employeeHistoryManMonth.month)
                .orderBy(employeeHistoryManMonth.month.asc())
                .limit(50000)
                .fetch();
    }

    private BooleanExpression findByYear(String year) {
        return StringUtils.hasText(year) ? employeeHistoryManMonth.year.eq(Integer.valueOf(year)) : null;
    }

    private BooleanExpression findByHistoryId(Long employeeHistoryId) {
        if (employeeHistoryId == null) return null;

        return employeeHistoryManMonth.employeeHistory.id.eq(employeeHistoryId);
    }
}
