package kr.co.tbell.mm.dto.employee;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReqUpdateEmployee {
    private String employeeNumber;

    @Size(min = 3, message = "'name' field must be at least 3 characters.")
    private String name;

    private LocalDate startDate;
    private LocalDate resignationDate;
}
