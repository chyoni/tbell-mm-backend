package kr.co.tbell.mm.dto.history;

import jakarta.validation.constraints.NotNull;
import kr.co.tbell.mm.entity.project.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqHistoryManMonth {
    @NotNull(message = "'id' must be required.")
    private Long id;
    @NotNull(message = "'calculateLevel' must be required.")
    private Level calculateLevel;
    @NotNull(message = "'calculateManMonth' must be required.")
    private String calculateManMonth;
    @NotNull(message = "'calculatePrice' must be required.")
    private Integer calculatePrice;
    @NotNull(message = "'durationStart' must be required.")
    private LocalDate durationStart;
    @NotNull(message = "'durationEnd' must be required.")
    private LocalDate durationEnd;
    @NotNull(message = "'inputManMonth' must be required.")
    private String inputManMonth;
    @NotNull(message = "'inputPrice' must be required.")
    private Integer inputPrice;
    @NotNull(message = "'year' must be required.")
    private Integer year;
    @NotNull(message = "'month' must be required.")
    private Integer month;
    @NotNull(message = "'monthSalary' must be required.")
    private Integer monthSalary;
    @NotNull(message = "'plPrice' must be required.")
    private Integer plPrice;
}
