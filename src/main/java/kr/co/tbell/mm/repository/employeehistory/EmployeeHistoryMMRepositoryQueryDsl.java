package kr.co.tbell.mm.repository.employeehistory;

import kr.co.tbell.mm.dto.history.HistorySearchCond;
import kr.co.tbell.mm.dto.history.ResHistoryManMonth;

import java.util.List;

public interface EmployeeHistoryMMRepositoryQueryDsl {
    List<ResHistoryManMonth> getHistoriesMM(Long employeeHistoryId, HistorySearchCond searchCond);
}
