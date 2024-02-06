package kr.co.tbell.mm.entity;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.project.Level;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class EmployeeHistoryMM extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;
    private Integer month;
    private LocalDate durationStart;
    private LocalDate durationEnd;
    private String inputMM;
    private Integer inputPrice;
    private String calculateMM;
    @Enumerated(EnumType.STRING)
    private Level calculateLevel;
    private Integer calculatePrice;
    private Integer plPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeHistory_id")
    private EmployeeHistory employeeHistory;

}
