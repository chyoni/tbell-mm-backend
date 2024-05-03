package kr.co.tbell.mm.entity.department;

import jakarta.persistence.*;
import kr.co.tbell.mm.entity.BaseEntity;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public void updateDepartment(String name) {
        if (name != null) this.name = name;
    }
}
