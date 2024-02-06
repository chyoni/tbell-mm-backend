package kr.co.tbell.mm.repository.employeehistory;

import kr.co.tbell.mm.dto.history.HistorySearchCond;
import kr.co.tbell.mm.dto.history.ResHistoryMM;

import java.util.List;

public interface EmployeeHistoryMMRepositoryQueryDsl {
    List<ResHistoryMM> getHistoriesMM(Long employeeHistoryId, HistorySearchCond searchCond);
}
