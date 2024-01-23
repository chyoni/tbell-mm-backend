package kr.co.tbell.mm.dto.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.tbell.mm.entity.Employee;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResEmployee {
    private final String employeeNumber;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate resignationDate;

    public ResEmployee(Employee employee) {
        this.employeeNumber = employee.getEmployeeNumber();
        this.name = employee.getName();
        this.startDate = employee.getStartDate();
        this.resignationDate = employee.getResignationDate();
    }
}
