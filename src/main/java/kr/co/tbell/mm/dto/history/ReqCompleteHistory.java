package kr.co.tbell.mm.dto.history;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReqCompleteHistory {
    @NotNull(message = "'endDate' must be required.")
    private LocalDate endDate;
}
