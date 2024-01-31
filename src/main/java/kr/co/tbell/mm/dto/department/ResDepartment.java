package kr.co.tbell.mm.dto.department;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.tbell.mm.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResDepartment {
    private String name;

    @QueryProjection
    public ResDepartment(Department department) {
        this.name = department.getName();
    }
}
