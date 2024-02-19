package kr.co.tbell.mm.repository.employeehistory;

import kr.co.tbell.mm.dto.history.HistorySearchCond;
import kr.co.tbell.mm.dto.history.ResHistoryManMonth;
import kr.co.tbell.mm.dto.history.ResHistoryStatistics;

import java.util.List;

public interface EmployeeHistoryMMRepositoryQueryDsl {
    List<ResHistoryManMonth> getHistoriesMM(Long employeeHistoryId, HistorySearchCond searchCond);

    List<ResHistoryStatistics> getHistoryStatistics(String year);
}
