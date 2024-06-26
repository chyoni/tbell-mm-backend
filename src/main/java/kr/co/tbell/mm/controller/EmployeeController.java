package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.employee.*;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.dto.salary.EmployeeSalary;
import kr.co.tbell.mm.dto.salary.ReqUpdateSalary;
import kr.co.tbell.mm.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * api-address:api-port/api/v1/employees?page=0 <br>
     * api-address:api-port/api/v1/employees?page=0&size=10 <br>
     * api-address:api-port/api/v1/employees?page=0&size=10&sort=id,desc <br>
     * api-address:api-port/api/v1/employees?page=0&size=10&sort=id,desc&sort=name
     */
    @GetMapping("")
    public ResponseEntity<Response<Page<ResEmployee>>> getEmployees(EmployeeSearchCond employeeSearchCond,
                                                                    Pageable pageable) {

        Page<ResEmployee> employees = employeeService.findAllEmployees(pageable, employeeSearchCond);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, employees));
    }

    @PostMapping("")
    public ResponseEntity<Response<ResCreateEmployee>> createEmployee(@RequestBody @Valid
                                                                      ReqCreateEmployee reqCreateEmployee) {
        log.info("[createEmployee]: Employee payload = {}", reqCreateEmployee);

        ResCreateEmployee newEmployee = employeeService.createEmployee(reqCreateEmployee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(true, null, newEmployee));
    }

    @GetMapping("/{employeeNumber}")
    public ResponseEntity<Response<ResEmployee>> getEmployeeByEmployeeNumber(@PathVariable String employeeNumber) {
        log.info("[getEmployeeByEmployeeNumber]: Employee number = {}", employeeNumber);

        ResEmployee employee = employeeService.findEmployee(employeeNumber);

        if (employee == null) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, employee));
    }

    @DeleteMapping("/{employeeNumber}")
    public ResponseEntity<Response<ResEmployee>> deleteEmployeeByEmployeeNumber(@PathVariable String employeeNumber) {
        log.info("[deleteEmployeeByEmployeeNumber]: Employee number = {}", employeeNumber);

        ResEmployee deleted = employeeService.deleteEmployee(employeeNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, deleted));
    }

    @PutMapping("/{employeeNumber}")
    public ResponseEntity<Response<ResEmployee>> editEmployeeByEmployeeNumber(
            @PathVariable String employeeNumber,
            @RequestBody @Valid ReqUpdateEmployee reqUpdateEmployee) {
        log.info("[editEmployeeByEmployeeNumber]: Employee number = {}", employeeNumber);
        log.info("[editEmployeeByEmployeeNumber]: Request payload = {}", reqUpdateEmployee);

        ResEmployee updated = employeeService.editEmployee(employeeNumber, reqUpdateEmployee);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, updated));
    }

    @PostMapping("/{employeeNumber}/salary")
    public ResponseEntity<Response<EmployeeSalary>> addMonthSalary(
            @PathVariable String employeeNumber,
            @RequestBody @Valid ReqUpdateSalary reqUpdateSalary) {
        log.info("[addMonthSalary]: EmployeeNumber: {}", employeeNumber);
        log.info("[addMonthSalary]: Request Payload: {}", reqUpdateSalary);

        EmployeeSalary employeeSalary = employeeService.addMonthSalary(employeeNumber, reqUpdateSalary);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, employeeSalary));
    }
}
