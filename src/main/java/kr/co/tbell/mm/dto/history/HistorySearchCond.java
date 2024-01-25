package kr.co.tbell.mm.dto.history;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class HistorySearchCond {
    private String employeeNumber;
    private String contractNumber;
    /**
     * 투입 시작일
     * */
    private LocalDate startDate;
    /**
     * 투입 종료일
     * */
    private LocalDate endDate;
    /**
     * startDate ASC or DESC
     * */
    private String orderBy;
}
