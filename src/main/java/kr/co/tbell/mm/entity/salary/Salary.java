package kr.co.tbell.mm.entity.salary;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.BaseEntity;
import kr.co.tbell.mm.entity.employee.Employee;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Salary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private Month month;

    private Integer salary;

    public void changeSalary(Integer year, Month month, Integer salary) {
        if (year != null) this.year = year;
        if (month != null) this.month = month;
        if (salary != null) this.salary = salary;
    }
}
