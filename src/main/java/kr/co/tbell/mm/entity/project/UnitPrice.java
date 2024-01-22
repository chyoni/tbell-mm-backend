package kr.co.tbell.mm.entity.project;

import jakarta.persistence.*;

@Entity
public class UnitPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Level level;

    private Integer worth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
