package kr.co.tbell.mm.controller;

import kr.co.tbell.mm.dto.ReqCreateEmployee;
import kr.co.tbell.mm.dto.ResCreateEmployee;
import kr.co.tbell.mm.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@Slf4j
public class EmployeeController {

    @PostMapping("")
    public ResponseEntity<Response<ResCreateEmployee>> createEmployee(@RequestBody ReqCreateEmployee reqCreateEmployee) {
        log.info("Request CreateEmployee: {}", reqCreateEmployee);

        ResCreateEmployee resCreateEmployee =
                new ResCreateEmployee(reqCreateEmployee.getEmployeeNumber(), reqCreateEmployee.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, resCreateEmployee));
    }
}
