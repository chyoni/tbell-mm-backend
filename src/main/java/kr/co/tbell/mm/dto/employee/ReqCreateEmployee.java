package kr.co.tbell.mm.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class ReqCreateEmployee {
    @NotNull(message = "'employeeNumber' must be required.")
    private String employeeNumber;
    @NotNull(message = "'name' must be required.")
    private String name;
    @NotNull(message = "'startDate' must be required.")
    private LocalDate startDate;
    private LocalDate resignationDate;
}
