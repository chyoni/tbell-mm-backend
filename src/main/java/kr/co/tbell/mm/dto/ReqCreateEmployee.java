package kr.co.tbell.mm.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ReqCreateEmployee {
    private String employeeNumber;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime resignationDate;
}
