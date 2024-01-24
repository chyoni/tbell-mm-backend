package kr.co.tbell.mm.service.history;

import kr.co.tbell.mm.dto.history.ReqCompleteHistory;
import kr.co.tbell.mm.dto.history.ReqHistory;
import kr.co.tbell.mm.dto.history.ResHistory;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.directory.InvalidAttributesException;

public interface EmployeeHistoryService {
    ResHistory makeHistory(ReqHistory history) throws InstanceAlreadyExistsException, InvalidAttributesException;

    ResHistory completeHistory(Long id, ReqCompleteHistory reqCompleteHistory) throws InvalidAttributesException;
}
