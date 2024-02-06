package kr.co.tbell.mm.dto.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import kr.co.tbell.mm.entity.EmployeeHistoryMM;
import kr.co.tbell.mm.entity.project.Level;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResHistoryMM {
    private Long id;
    private Integer year;
    private Integer month;
    private LocalDate durationStart;
    private LocalDate durationEnd;
    private String inputMM;
    private Integer inputPrice;
    private String calculateMM;
    private Level calculateLevel;
    private Integer calculatePrice;
    private Integer plPrice;

    @QueryProjection
    public ResHistoryMM(EmployeeHistoryMM mm) {
        this.id = mm.getId();
        this.year = mm.getYear();
        this.month = mm.getMonth();
        this.durationStart = mm.getDurationStart();
        this.durationEnd = mm.getDurationEnd();
        this.inputMM = mm.getInputMM();
        this.inputPrice = mm.getInputPrice();
        this.calculateMM = mm.getCalculateMM();
        this.calculateLevel = mm.getCalculateLevel();
        this.calculatePrice = mm.getCalculatePrice();
        this.plPrice = mm.getPlPrice();
    }
}
