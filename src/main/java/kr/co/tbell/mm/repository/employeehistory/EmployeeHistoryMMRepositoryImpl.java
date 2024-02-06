package kr.co.tbell.mm.repository.employeehistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.history.HistorySearchCond;
import kr.co.tbell.mm.dto.history.QResHistoryMM;
import kr.co.tbell.mm.dto.history.ResHistoryMM;
import kr.co.tbell.mm.entity.QEmployeeHistoryMM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static kr.co.tbell.mm.entity.QEmployeeHistoryMM.employeeHistoryMM;

@Slf4j
@RequiredArgsConstructor
public class EmployeeHistoryMMRepositoryImpl implements EmployeeHistoryMMRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ResHistoryMM> getHistoriesMM(Long employeeHistoryId) {

        return queryFactory
                .select(new QResHistoryMM(employeeHistoryMM))
                .from(employeeHistoryMM)
                .where(employeeHistoryMM.employeeHistory.id.eq(employeeHistoryId))
                .fetch();
    }
}
