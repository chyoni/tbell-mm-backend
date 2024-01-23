package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.employee.ResEmployee;
import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;

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
    public ResponseEntity<Response<Page<ResEmployee>>> getEmployees(@PageableDefault() Pageable pageable) {
        Page<ResEmployee> employees = employeeService.findAllEmployees(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, employees));
    }

    @PostMapping("")
    public ResponseEntity<Response<ResCreateEmployee>> createEmployee(@RequestBody @Valid
                                                                      ReqCreateEmployee reqCreateEmployee) {
        log.info("[createEmployee]: Employee payload = {}", reqCreateEmployee);

        ResCreateEmployee newEmployee;
        try {
            newEmployee = employeeService.createEmployee(reqCreateEmployee);
        } catch (InstanceAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(true, null, newEmployee));
    }

    @GetMapping("/{employeeNumber}")
    public ResponseEntity<Response<ResEmployee>> getEmployeeByEmployeeNumber(@PathVariable String employeeNumber) {
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
        ResEmployee deleted = employeeService.deleteEmployee(employeeNumber);

        if (deleted == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false,
                            "Employee does not exist with this employee number: " + employeeNumber,
                            null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, deleted));
    }
}
