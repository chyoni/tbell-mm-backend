package kr.co.tbell.mm.entity.employeehistory;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.BaseEntity;
import kr.co.tbell.mm.entity.project.Level;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class EmployeeHistoryManMonth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;
    private Integer month;
    private LocalDate durationStart;
    private LocalDate durationEnd;
    private String inputManMonth;
    private Integer monthSalary;
    private Integer inputPrice;
    private String calculateManMonth;
    @Enumerated(EnumType.STRING)
    private Level calculateLevel;
    private Integer calculatePrice;
    private Integer plPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeHistory_id")
    private EmployeeHistory employeeHistory;

    public void updateManMonth(Integer year,
                               Integer month,
                               LocalDate durationStart,
                               LocalDate durationEnd,
                               String inputManMonth,
                               Integer monthSalary,
                               Integer inputPrice,
                               String calculateManMonth,
                               Level calculateLevel,
                               Integer calculatePrice,
                               Integer plPrice) {

        if (year != null) this.year = year;
        if (month != null) this.month = month;
        if (durationStart != null) this.durationStart = durationStart;
        if (durationEnd != null) this.durationEnd = durationEnd;
        if (inputManMonth != null) this.inputManMonth = inputManMonth;
        if (monthSalary != null) this.monthSalary = monthSalary;
        if (inputPrice != null) this.inputPrice = inputPrice;
        if (calculateManMonth != null) this.calculateManMonth = calculateManMonth;
        if (calculateLevel != null) this.calculateLevel = calculateLevel;
        if (calculatePrice != null) this.calculatePrice = calculatePrice;
        if (plPrice != null) this.plPrice = plPrice;
    }

    public void changeMonthSalary(int monthSalary) {
        this.monthSalary = monthSalary;
    }

    public void applyInputPrice() {
        this.inputPrice = (int) (this.monthSalary * Double.parseDouble(this.inputManMonth));
    }

    public void changeDurationEndAndInputManMonth(LocalDate durationEnd) {
        if (durationEnd != null) {
            this.durationEnd = durationEnd;

            int durationDay = this.durationStart.until(durationEnd).getDays() + 1;
            int dayOfMonth = this.durationStart.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

            double inputManMonth = (double) durationDay / dayOfMonth;

            this.inputManMonth = String.format("%.2f", inputManMonth);
        }
    }
}
