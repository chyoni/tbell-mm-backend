package kr.co.tbell.mm.repository.employeehistory;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.history.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

import static kr.co.tbell.mm.entity.employeehistory.QEmployeeHistory.employeeHistory;
import static kr.co.tbell.mm.entity.employeehistory.QEmployeeHistoryManMonth.employeeHistoryManMonth;
import static kr.co.tbell.mm.entity.project.QProject.project;

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

    @Override
    public List<ResContractHistoryStatistics> getContractHistoryStatistics(String contractNumber,
                                                                           String year,
                                                                           boolean total) {
        if (total) {
            return queryFactory
                    .select(new QResContractHistoryStatistics(
                            employeeHistoryManMonth.inputManMonth.castToNum(Double.class).sum(),
                            employeeHistoryManMonth.calculateManMonth.castToNum(Double.class).sum(),
                            project.contractNumber,
                            project.teamName))
                    .from(employeeHistory)
                    .join(employeeHistoryManMonth).on(employeeHistory.id.eq(employeeHistoryManMonth.employeeHistory.id))
                    .join(project).on(project.id.eq(employeeHistory.project.id))
                    .where(project.contractNumber.eq(contractNumber).and(employeeHistoryManMonth.year.eq(Integer.valueOf(year))))
                    .fetch();
        } else {
            return queryFactory
                    .select(new QResContractHistoryStatistics(
                            employeeHistoryManMonth.month,
                            employeeHistoryManMonth.inputManMonth.castToNum(Double.class).sum(),
                            employeeHistoryManMonth.inputPrice.sum(),
                            employeeHistoryManMonth.calculateManMonth.castToNum(Double.class).sum(),
                            employeeHistoryManMonth.calculatePrice.sum(),
                            project.contractNumber,
                            project.teamName))
                    .from(employeeHistory)
                    .join(employeeHistoryManMonth).on(employeeHistory.id.eq(employeeHistoryManMonth.employeeHistory.id))
                    .join(project).on(project.id.eq(employeeHistory.project.id))
                    .where(project.contractNumber.eq(contractNumber).and(employeeHistoryManMonth.year.eq(Integer.valueOf(year))))
                    .groupBy(employeeHistoryManMonth.month)
                    .limit(50000)
                    .fetch();
        }
    }

    private BooleanExpression findByYear(String year) {
        return StringUtils.hasText(year) ? employeeHistoryManMonth.year.eq(Integer.valueOf(year)) : null;
    }

    private BooleanExpression findByHistoryId(Long employeeHistoryId) {
        if (employeeHistoryId == null) return null;

        return employeeHistoryManMonth.employeeHistory.id.eq(employeeHistoryId);
    }
}
