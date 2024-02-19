package kr.co.tbell.mm.dto.history;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResHistoryStatistics {

    private Integer month;

    private Double totalInputManMonth;
    private Integer totalInputPrice;

    private Double totalCalculateManMonth;
    private Integer totalCalculatePrice;

    @QueryProjection
    public ResHistoryStatistics(Integer month,
                                Double totalInputManMonth,
                                Integer totalInputPrice,
                                Double totalCalculateManMonth,
                                Integer totalCalculatePrice) {
        this.month = month;
        this.totalInputManMonth = totalInputManMonth;
        this.totalInputPrice = totalInputPrice;
        this.totalCalculateManMonth = totalCalculateManMonth;
        this.totalCalculatePrice = totalCalculatePrice;
    }
}
