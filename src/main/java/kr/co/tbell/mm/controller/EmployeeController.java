package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

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
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, newEmployee));
    }
}
