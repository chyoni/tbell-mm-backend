package kr.co.tbell.mm.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
