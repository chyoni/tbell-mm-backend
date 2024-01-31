package kr.co.tbell.mm.repository.department;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.department.DepartmentSearchCond;
import kr.co.tbell.mm.dto.department.QResDepartment;
import kr.co.tbell.mm.dto.department.ResDepartment;
import kr.co.tbell.mm.entity.QDepartment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static kr.co.tbell.mm.entity.QDepartment.department;

@Slf4j
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ResDepartment> getDepartments(Pageable pageable, DepartmentSearchCond departmentSearchCond) {

        List<ResDepartment> results = queryFactory
                .select(new QResDepartment(department))
                .from(department)
                .where(departmentNameEq(departmentSearchCond.getDepartmentName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory
                .select(new QResDepartment(department))
                .from(department)
                .where(departmentNameEq(departmentSearchCond.getDepartmentName()))
                .fetch()
                .size();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression departmentNameEq(String departmentName) {
        return StringUtils.hasText(departmentName) ? department.name.contains(departmentName) : null;
    }
}
