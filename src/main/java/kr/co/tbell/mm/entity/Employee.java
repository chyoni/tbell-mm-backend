package kr.co.tbell.mm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Employee extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate resignationDate;

    /**
     * Builder Pattern을 사용해서 Employee entity 생성
     * */
    public static Employee createEmployee(String employeeNumber,
                                          String name,
                                          LocalDate startDate,
                                          LocalDate resignationDate) {
        return Employee.builder()
                .employeeNumber(employeeNumber)
                .name(name)
                .startDate(startDate)
                .resignationDate(resignationDate)
                .build();
    }

    public void updateEmployee(String employeeNumber,
                               String name,
                               LocalDate startDate,
                               LocalDate resignationDate) {

        if (employeeNumber != null)
            this.employeeNumber = employeeNumber;

        if (name != null)
            this.name = name;

        if (startDate != null)
            this.startDate = startDate;

        if (resignationDate != null)
            this.resignationDate = resignationDate;
    }
}
