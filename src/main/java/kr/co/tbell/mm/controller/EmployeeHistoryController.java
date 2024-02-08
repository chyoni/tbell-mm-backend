package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.dto.history.*;
import kr.co.tbell.mm.service.history.EmployeeHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.naming.directory.InvalidAttributesException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class EmployeeHistoryController {

    private final EmployeeHistoryService employeeHistoryService;

    @PostMapping("")
    public ResponseEntity<Response<ResHistory>> makeHistory(@RequestBody @Valid ReqHistory reqHistory) {
        log.info("[makeHistory]: Request payload = {}", reqHistory);

        try {
            ResHistory history = employeeHistoryService.makeHistory(reqHistory);

            return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, null, history));
        } catch (InstanceAlreadyExistsException | InvalidAttributesException | NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response<ResHistory>> completeHistory(
            @PathVariable Long id,
            @RequestBody @Valid ReqCompleteHistory reqCompleteHistory) {
        log.info("[completeHistory]: History id = {}", id);
        log.info("[completeHistory]: endDate: {}", reqCompleteHistory);

        try {
            ResHistory resHistory = employeeHistoryService.completeHistory(id, reqCompleteHistory);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Response<>(true, null, resHistory));
        } catch (NoSuchElementException | InvalidAttributesException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }
    }

    /**
     * 아래처럼 HistorySearchCond 객체를 파라미터로 받으면 REST API의 QueryParameter로 들어오는 Key/Value에 대해
     * HistorySearchCond 객체가 가지는 필드와 매치되는 Key가 있으면 매핑해준다.
     * */
    @GetMapping("")
    public ResponseEntity<Response<Page<ResHistory>>> getHistories(HistorySearchCond searchCond,
                                                                   Pageable pageable) {
        log.info("[getHistories]: searchCond: {}", searchCond);

        Page<ResHistory> histories = employeeHistoryService.getHistories(pageable, searchCond);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, histories));
    }

    @PostMapping("/{id}/mms")
    public ResponseEntity<Response<Void>> saveHistoryManMonth(
            @PathVariable Long id,
            @RequestBody @Valid List<ReqHistoryManMonth> mms) {

        log.info("[saveHistoryManMonth]: History ID: [{}]", id);
        log.info("[saveHistoryManMonth]: Change ManMonth Data: [{}]", mms);

        try {
            employeeHistoryService.saveManMonthsByHistoryId(id, mms);
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(true, null, null));
    }
}
