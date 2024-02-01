package kr.co.tbell.mm.dto.employee;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchCond {
    private String employeeNumber;
    private String name;
    private LocalDate startDate;
}
