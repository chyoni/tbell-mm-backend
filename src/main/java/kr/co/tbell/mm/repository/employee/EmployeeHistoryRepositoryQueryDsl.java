package kr.co.tbell.mm.repository.employee;

import kr.co.tbell.mm.dto.history.HistorySearchCond;
import kr.co.tbell.mm.dto.history.ResHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeHistoryRepositoryQueryDsl {
    Page<ResHistory> getHistories(Pageable pageable, HistorySearchCond searchCond);
}
