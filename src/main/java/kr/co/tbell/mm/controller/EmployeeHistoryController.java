package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.dto.history.ReqCompleteHistory;
import kr.co.tbell.mm.dto.history.ReqHistory;
import kr.co.tbell.mm.dto.history.ResHistory;
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

    @GetMapping("")
    public ResponseEntity<Response<Page<ResHistory>>> getHistories(
            @PageableDefault Pageable pageable,
            @RequestParam(name = "contractNumber", required = false) String contractNumber) {
        log.info("contractNumber = {}", contractNumber);

        if (contractNumber != null) {
            Page<ResHistory> historiesByProject =
                    employeeHistoryService.getHistoriesByProject(pageable, contractNumber);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Response<>(true, null, historiesByProject));
        }

        Page<ResHistory> histories = employeeHistoryService.getHistories(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, histories));
    }
}
