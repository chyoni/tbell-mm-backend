package kr.co.tbell.mm.repository.employeehistory;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.history.HistorySearchCond;
import kr.co.tbell.mm.dto.history.QResHistoryMM;
import kr.co.tbell.mm.dto.history.ResHistoryMM;
import kr.co.tbell.mm.entity.QEmployeeHistoryMM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

import static kr.co.tbell.mm.entity.QEmployeeHistoryMM.employeeHistoryMM;

@Slf4j
@RequiredArgsConstructor
public class EmployeeHistoryMMRepositoryImpl implements EmployeeHistoryMMRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ResHistoryMM> getHistoriesMM(Long employeeHistoryId, HistorySearchCond searchCond) {

        return queryFactory
                .select(new QResHistoryMM(employeeHistoryMM))
                .from(employeeHistoryMM)
                .where(findByHistoryId(employeeHistoryId),
                        findByYear(searchCond.getYear()))
                .orderBy(employeeHistoryMM.durationStart.asc())
                .fetch();
    }

    private BooleanExpression findByYear(String year) {
        return StringUtils.hasText(year) ? employeeHistoryMM.year.eq(Integer.valueOf(year)) : null;
    }

    private BooleanExpression findByHistoryId(Long employeeHistoryId) {
        if (employeeHistoryId == null) return null;

        return employeeHistoryMM.employeeHistory.id.eq(employeeHistoryId);
    }
}
