package kr.co.tbell.mm.controller;

import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.dto.history.ResContractHistoryStatistics;
import kr.co.tbell.mm.dto.history.ResHistoryStatistics;
import kr.co.tbell.mm.service.history.EmployeeHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class HistoryStatisticsController {

    private final EmployeeHistoryService employeeHistoryService;

    @GetMapping("/all")
    public ResponseEntity<Response<List<ResHistoryStatistics>>> getStatisticsByYear(String year) {
        log.info("[getStatisticsByYear]: Search year: {}", year);

        List<ResHistoryStatistics> historyStatistics = employeeHistoryService.getHistoryStatistics(year);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, historyStatistics));
    }

    @GetMapping("/{contractNumber}")
    public ResponseEntity<Response<List<ResContractHistoryStatistics>>> getStatisticsByContract(
            @PathVariable String contractNumber,
            @RequestParam(value = "year") String year,
            @RequestParam(value = "total") boolean total) {
        log.info("[getStatisticsByContract]: Search Contract: {}, Search year: {} is total data: {}",
                contractNumber, year, total);

        List<ResContractHistoryStatistics> contractHistoryStatistics =
                employeeHistoryService.getContractHistoryStatistics(contractNumber, year, total);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, contractHistoryStatistics));
    }
}
