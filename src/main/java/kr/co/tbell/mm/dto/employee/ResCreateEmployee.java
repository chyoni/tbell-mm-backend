package kr.co.tbell.mm.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ResCreateEmployee {
    private String employeeNumber;
    private String name;
}
