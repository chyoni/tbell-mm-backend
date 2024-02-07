package kr.co.tbell.mm.dto.project;

import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.OperationRate;
import kr.co.tbell.mm.entity.project.ProjectStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqUpdateProject {
    private String teamName;
    private String contractor;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus projectStatus;
    private OperationRate operationRate;
    private String departmentName;

    @Builder.Default
    private List<Map<Level, Integer>> unitPrices = new ArrayList<>();
}
