package kr.co.tbell.mm.dto.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import kr.co.tbell.mm.dto.employee.ResEmployee;
import kr.co.tbell.mm.dto.project.ResProject;
import kr.co.tbell.mm.entity.employee.Employee;
import kr.co.tbell.mm.entity.employeehistory.EmployeeHistory;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResHistory {
    private Long id;
    private ResEmployee employee;
    private ResProject project;
    private LocalDate startDate;
    private LocalDate endDate;
    private Level level;
    private Integer worth;
    private List<ResHistoryManMonth> mms = new ArrayList<>();

    public ResHistory(Project project,
                      List<Map<Level, Integer>> unitPrices,
                      Employee employee,
                      EmployeeHistory employeeHistory) {
        this.employee = new ResEmployee(employee);
        this.project = new ResProject(project, unitPrices);
        this.id = employeeHistory.getId();
        this.startDate = employeeHistory.getStartDate();
        this.endDate = employeeHistory.getEndDate();
        this.level = employeeHistory.getLevel();
        this.worth = employeeHistory.getWorth();
    }

    @QueryProjection
    public ResHistory(Project project,
                      Employee employee,
                      EmployeeHistory employeeHistory) {
        this.employee = new ResEmployee(employee);
        this.project = new ResProject(project);
        this.id = employeeHistory.getId();
        this.startDate = employeeHistory.getStartDate();
        this.endDate = employeeHistory.getEndDate();
        this.level = employeeHistory.getLevel();
        this.worth = employeeHistory.getWorth();
    }

    public ResHistory(EmployeeHistory history, Employee employee) {
        this.id = history.getId();
        this.startDate = history.getStartDate();
        this.endDate = history.getEndDate();
        this.employee = new ResEmployee(employee);
    }

    public void setMms(List<ResHistoryManMonth> mms) {
        this.mms = mms;
    }
}
