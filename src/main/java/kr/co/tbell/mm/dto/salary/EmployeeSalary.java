package kr.co.tbell.mm.dto.salary;

import kr.co.tbell.mm.entity.salary.Month;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EmployeeSalary {
    private String employeeNumber;
    private String employeeName;
    private Integer year;
    private Month month;
    private Integer salary;
}
