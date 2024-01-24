package kr.co.tbell.mm.entity.project;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.Department;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String contractNumber;

    @Column(unique = true)
    private String teamName;

    private String contractor;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Enumerated(EnumType.STRING)
    private OperationRate operationRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public void updateProject(String teamName,
                              String contractor,
                              LocalDate startDate,
                              LocalDate endDate,
                              ProjectStatus projectStatus,
                              OperationRate operationRate) {
        if (teamName != null) this.teamName = teamName;
        if (contractor != null) this.contractor = contractor;
        if (startDate != null) this.startDate = startDate;
        if (endDate != null) this.endDate = endDate;
        if (projectStatus != null) this.projectStatus = projectStatus;
        if (operationRate != null) this.operationRate = operationRate;
    }

    public void changeDepartment(Department department) {
        this.department = department;
    }
}
