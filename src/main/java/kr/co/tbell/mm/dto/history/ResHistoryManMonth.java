package kr.co.tbell.mm.dto.history;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.tbell.mm.entity.employeehistory.EmployeeHistoryManMonth;
import kr.co.tbell.mm.entity.project.Level;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResHistoryManMonth {
    private Long id;
    private Integer year;
    private Integer month;
    private LocalDate durationStart;
    private LocalDate durationEnd;
    private String inputManMonth;
    private Integer monthSalary;
    private Integer inputPrice;
    private String calculateManMonth;
    private Level calculateLevel;
    private Integer calculatePrice;
    private Integer plPrice;

    @QueryProjection
    public ResHistoryManMonth(EmployeeHistoryManMonth mm) {
        this.id = mm.getId();
        this.year = mm.getYear();
        this.month = mm.getMonth();
        this.durationStart = mm.getDurationStart();
        this.durationEnd = mm.getDurationEnd();
        this.inputManMonth = mm.getInputManMonth();
        this.monthSalary = mm.getMonthSalary();
        this.inputPrice = mm.getInputPrice();
        this.calculateManMonth = mm.getCalculateManMonth();
        this.calculateLevel = mm.getCalculateLevel();
        this.calculatePrice = mm.getCalculatePrice();
        this.plPrice = mm.getPlPrice();
    }
}
