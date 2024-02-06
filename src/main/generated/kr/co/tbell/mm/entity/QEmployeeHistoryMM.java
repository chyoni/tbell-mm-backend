package kr.co.tbell.mm.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeeHistoryMM is a Querydsl query type for EmployeeHistoryMM
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployeeHistoryMM extends EntityPathBase<EmployeeHistoryMM> {

    private static final long serialVersionUID = 410260087L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployeeHistoryMM employeeHistoryMM = new QEmployeeHistoryMM("employeeHistoryMM");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<kr.co.tbell.mm.entity.project.Level> calculateLevel = createEnum("calculateLevel", kr.co.tbell.mm.entity.project.Level.class);

    public final StringPath calculateMM = createString("calculateMM");

    public final NumberPath<Integer> calculatePrice = createNumber("calculatePrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DatePath<java.time.LocalDate> durationEnd = createDate("durationEnd", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> durationStart = createDate("durationStart", java.time.LocalDate.class);

    public final QEmployeeHistory employeeHistory;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath inputMM = createString("inputMM");

    public final NumberPath<Integer> inputPrice = createNumber("inputPrice", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final NumberPath<Integer> plPrice = createNumber("plPrice", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QEmployeeHistoryMM(String variable) {
        this(EmployeeHistoryMM.class, forVariable(variable), INITS);
    }

    public QEmployeeHistoryMM(Path<? extends EmployeeHistoryMM> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployeeHistoryMM(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployeeHistoryMM(PathMetadata metadata, PathInits inits) {
        this(EmployeeHistoryMM.class, metadata, inits);
    }

    public QEmployeeHistoryMM(Class<? extends EmployeeHistoryMM> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employeeHistory = inits.isInitialized("employeeHistory") ? new QEmployeeHistory(forProperty("employeeHistory"), inits.get("employeeHistory")) : null;
    }

}

