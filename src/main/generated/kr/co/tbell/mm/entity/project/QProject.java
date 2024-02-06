package kr.co.tbell.mm.entity.project;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProject is a Querydsl query type for Project
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProject extends EntityPathBase<Project> {

    private static final long serialVersionUID = -1749329451L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProject project = new QProject("project");

    public final kr.co.tbell.mm.entity.QBaseEntity _super = new kr.co.tbell.mm.entity.QBaseEntity(this);

    public final StringPath contractNumber = createString("contractNumber");

    public final StringPath contractor = createString("contractor");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final kr.co.tbell.mm.entity.QDepartment department;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<OperationRate> operationRate = createEnum("operationRate", OperationRate.class);

    public final EnumPath<ProjectStatus> projectStatus = createEnum("projectStatus", ProjectStatus.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath teamName = createString("teamName");

    public QProject(String variable) {
        this(Project.class, forVariable(variable), INITS);
    }

    public QProject(Path<? extends Project> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProject(PathMetadata metadata, PathInits inits) {
        this(Project.class, metadata, inits);
    }

    public QProject(Class<? extends Project> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new kr.co.tbell.mm.entity.QDepartment(forProperty("department")) : null;
    }

}

