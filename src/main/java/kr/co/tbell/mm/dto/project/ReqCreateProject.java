package kr.co.tbell.mm.dto.project;

import jakarta.validation.constraints.NotNull;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.OperationRate;
import kr.co.tbell.mm.entity.project.ProjectStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqCreateProject {
    @NotNull(message = "'contractNumber' must be required.")
    private String contractNumber;
    @NotNull(message = "'team' must be required.")
    private String teamName;
    @NotNull(message = "'contractor' must be required.")
    private String contractor;
    @NotNull(message = "'startDate' must be required.")
    private LocalDate startDate;
    @NotNull(message = "'endDate' must be required.")
    private LocalDate endDate;
    private ProjectStatus projectStatus;
    private OperationRate operationRate;
    @NotNull(message = "'departmentName' must be required.")
    private String departmentName;

    @Builder.Default
    @NotNull(message = "'unitPrices' must be required.")
    private List<Map<Level, Integer>> unitPrices = new ArrayList<>();
}
