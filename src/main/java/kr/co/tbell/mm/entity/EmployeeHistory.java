package kr.co.tbell.mm.entity;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class EmployeeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer worth;

    public void completeHistory(LocalDate endDate) {
        if (endDate != null) this.endDate = endDate;
    }
}
