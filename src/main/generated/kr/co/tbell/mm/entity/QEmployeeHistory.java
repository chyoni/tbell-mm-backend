package kr.co.tbell.mm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeeHistory is a Querydsl query type for EmployeeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployeeHistory extends EntityPathBase<EmployeeHistory> {

    private static final long serialVersionUID = -826387817L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployeeHistory employeeHistory = new QEmployeeHistory("employeeHistory");

    public final QEmployee employee;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<kr.co.tbell.mm.entity.project.Level> level = createEnum("level", kr.co.tbell.mm.entity.project.Level.class);

    public final kr.co.tbell.mm.entity.project.QProject project;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final NumberPath<Integer> worth = createNumber("worth", Integer.class);

    public QEmployeeHistory(String variable) {
        this(EmployeeHistory.class, forVariable(variable), INITS);
    }

    public QEmployeeHistory(Path<? extends EmployeeHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployeeHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployeeHistory(PathMetadata metadata, PathInits inits) {
        this(EmployeeHistory.class, metadata, inits);
    }

    public QEmployeeHistory(Class<? extends EmployeeHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new QEmployee(forProperty("employee")) : null;
        this.project = inits.isInitialized("project") ? new kr.co.tbell.mm.entity.project.QProject(forProperty("project"), inits.get("project")) : null;
    }

}

