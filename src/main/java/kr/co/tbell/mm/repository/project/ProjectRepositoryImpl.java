package kr.co.tbell.mm.repository.project;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.tbell.mm.dto.project.ProjectSearchCond;
import kr.co.tbell.mm.entity.project.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static kr.co.tbell.mm.entity.QDepartment.*;
import static kr.co.tbell.mm.entity.project.QProject.project;

@Slf4j
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Project> getProjects(Pageable pageable, ProjectSearchCond projectSearchCond) {

        List<Project> results = queryFactory
                .select(project)
                .from(project)
                .leftJoin(project.department, department)
                .where(teamNameContains(projectSearchCond.getTeamName()),
                        dateBetween(projectSearchCond.getYear()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(startDateOrderBy(projectSearchCond.getOrderBy()))
                .fetch();

        int total = queryFactory
                .select(project)
                .from(project)
                .leftJoin(project.department, department)
                .where(teamNameContains(projectSearchCond.getTeamName()),
                        dateBetween(projectSearchCond.getYear()))
                .fetch()
                .size();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression dateBetween(String year) {
        if (!StringUtils.hasText(year)) return null;

        LocalDate fromStartDate = LocalDate.of(Integer.parseInt(year), 1, 1);
        LocalDate toStartDate = LocalDate.of(Integer.parseInt(year), 12, 31);

        BooleanExpression goe = project.startDate.goe(fromStartDate);
        BooleanExpression loe = project.startDate.loe(toStartDate);

        return Expressions.allOf(goe, loe);
    }

    private OrderSpecifier<?> startDateOrderBy(String orderBy) {
        return StringUtils.hasText(orderBy) ?
                orderBy.equalsIgnoreCase("ASC") ?
                        project.startDate.asc() :
                        project.startDate.desc() :
                project.startDate.asc();
    }

    private BooleanExpression teamNameContains(String teamName) {
        return StringUtils.hasText(teamName) ? project.teamName.contains(teamName) : null;
    }
}
