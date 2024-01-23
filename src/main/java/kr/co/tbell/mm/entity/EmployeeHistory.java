package kr.co.tbell.mm.entity;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;

import java.time.LocalDate;

@Entity
public class EmployeeHistory {

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
}
