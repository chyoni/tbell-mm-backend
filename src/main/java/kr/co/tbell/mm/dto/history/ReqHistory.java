package kr.co.tbell.mm.dto.history;

import jakarta.validation.constraints.NotNull;
import kr.co.tbell.mm.entity.project.Level;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqHistory {
    @NotNull(message = "'employeeNumber' must be required.")
    private String employeeNumber;
    @NotNull(message = "'contractNumber' must be required.")
    private String contractNumber;
    @NotNull(message = "'startDate' must be required.")
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull(message = "'level' must be required.")
    private Level level;
}
