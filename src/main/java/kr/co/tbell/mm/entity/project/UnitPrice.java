package kr.co.tbell.mm.entity.project;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.BaseEntity;
import lombok.*;

@Entity
@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnitPrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Level level;

    private Integer worth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
