package kr.co.tbell.mm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startDate;
    private LocalDateTime resignationDate;

    /**
     * Builder Pattern을 사용해서 Employee entity 생성
     * */
    public static Employee createEmployee(String employeeNumber,
                                          String name,
                                          LocalDateTime startDate,
                                          LocalDateTime resignationDate) {
        return Employee.builder()
                .employeeNumber(employeeNumber)
                .name(name)
                .startDate(startDate)
                .resignationDate(resignationDate)
                .build();
    }
}
