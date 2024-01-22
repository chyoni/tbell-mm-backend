package kr.co.tbell.mm.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String employeeNumber;

    private String name;

    private LocalDateTime startDate;
    private LocalDateTime resignationDate;
}
