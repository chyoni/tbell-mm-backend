package kr.co.tbell.mm.service.history;

import kr.co.tbell.mm.dto.history.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.directory.InvalidAttributesException;
import java.util.List;

public interface EmployeeHistoryService {
    ResHistory makeHistory(ReqHistory history);

    ResHistory completeHistory(Long id, ReqCompleteHistory reqCompleteHistory);

    Page<ResHistory> getHistories(Pageable pageable, HistorySearchCond searchCond);

    Page<ResHistory> getHistoriesByProject(Pageable pageable, String contractNumber);

    Page<ResHistory> getHistoriesByEmployee(Pageable pageable, String employeeNumber);

    void saveManMonthsByHistoryId(Long historyId, List<ReqHistoryManMonth> mms);

    List<ResHistoryStatistics> getHistoryStatistics(String year);

    List<ResContractHistoryStatistics> getContractHistoryStatistics(String contractNumber, String year, boolean total);

    void intervalHistoryScheduler();
}
