package kr.co.tbell.mm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ResCreateEmployee {
    private String employeeNumber;
    private String name;
}
