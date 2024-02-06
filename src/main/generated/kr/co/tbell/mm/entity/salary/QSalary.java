package kr.co.tbell.mm.entity.salary;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSalary is a Querydsl query type for Salary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSalary extends EntityPathBase<Salary> {

    private static final long serialVersionUID = -106915969L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSalary salary1 = new QSalary("salary1");

    public final kr.co.tbell.mm.entity.QBaseEntity _super = new kr.co.tbell.mm.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final kr.co.tbell.mm.entity.QEmployee employee;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<Month> month = createEnum("month", Month.class);

    public final NumberPath<Integer> salary = createNumber("salary", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QSalary(String variable) {
        this(Salary.class, forVariable(variable), INITS);
    }

    public QSalary(Path<? extends Salary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSalary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSalary(PathMetadata metadata, PathInits inits) {
        this(Salary.class, metadata, inits);
    }

    public QSalary(Class<? extends Salary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new kr.co.tbell.mm.entity.QEmployee(forProperty("employee")) : null;
    }

}

