package kr.co.tbell.mm.dto.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.OperationRate;
import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.ProjectStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResProject {
    private final String contractNumber;
    private final String teamName;
    private final String contractor;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final ProjectStatus projectStatus;
    private final OperationRate operationRate;
    private final String departmentName;
    private List<Map<Level, Integer>> unitPrices;

    public ResProject(ReqCreateProject reqCreateProject) {
        this.contractNumber = reqCreateProject.getContractNumber();
        this.teamName = reqCreateProject.getTeamName();
        this.contractor = reqCreateProject.getContractor();
        this.startDate = reqCreateProject.getStartDate();
        this.endDate = reqCreateProject.getEndDate();
        this.projectStatus = reqCreateProject.getProjectStatus();
        this.operationRate = reqCreateProject.getOperationRate();
        this.departmentName = reqCreateProject.getDepartmentName();
        this.unitPrices = reqCreateProject.getUnitPrices();
    }

    public ResProject(Project project, List<Map<Level, Integer>> unitPrices) {
        this.contractNumber = project.getContractNumber();
        this.teamName = project.getTeamName();
        this.contractor = project.getContractor();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.projectStatus = project.getProjectStatus();
        this.operationRate = project.getOperationRate();
        this.departmentName = project.getDepartment().getName();
        this.unitPrices = unitPrices;
    }

    public ResProject(Project project) {
        this.contractNumber = project.getContractNumber();
        this.teamName = project.getTeamName();
        this.contractor = project.getContractor();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.projectStatus = project.getProjectStatus();
        this.operationRate = project.getOperationRate();
        this.departmentName = project.getDepartment().getName();
    }
}
