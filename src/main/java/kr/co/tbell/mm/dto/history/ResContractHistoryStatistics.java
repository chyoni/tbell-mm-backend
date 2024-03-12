package kr.co.tbell.mm.dto.history;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResContractHistoryStatistics {

    private Integer month;
    private Double totalInputManMonth;
    private Integer totalInputPrice;

    private Double totalCalculateManMonth;
    private Integer totalCalculatePrice;
    private String contractNumber;
    private String teamName;

    @QueryProjection
    public ResContractHistoryStatistics(Integer month,
                                        Double totalInputManMonth,
                                        Integer totalInputPrice,
                                        Double totalCalculateManMonth,
                                        Integer totalCalculatePrice,
                                        String contractNumber,
                                        String teamName) {
        this.month = month;
        this.totalInputManMonth = totalInputManMonth;
        this.totalInputPrice = totalInputPrice;
        this.totalCalculateManMonth = totalCalculateManMonth;
        this.totalCalculatePrice = totalCalculatePrice;
        this.contractNumber = contractNumber;
        this.teamName = teamName;
    }
}
