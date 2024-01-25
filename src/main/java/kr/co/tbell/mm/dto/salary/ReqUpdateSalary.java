package kr.co.tbell.mm.dto.salary;


import jakarta.validation.constraints.NotNull;
import kr.co.tbell.mm.entity.salary.Month;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReqUpdateSalary {
    @NotNull(message = "'year' must be required.")
    private Integer year;
    @NotNull(message = "'month' must be required.")
    private Month month;
    @NotNull(message = "'salary' must be required.")
    private Integer salary;
}
