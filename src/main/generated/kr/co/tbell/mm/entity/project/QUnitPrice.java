package kr.co.tbell.mm.entity.project;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUnitPrice is a Querydsl query type for UnitPrice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUnitPrice extends EntityPathBase<UnitPrice> {

    private static final long serialVersionUID = -1364751903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUnitPrice unitPrice = new QUnitPrice("unitPrice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Level> level = createEnum("level", Level.class);

    public final QProject project;

    public final NumberPath<Integer> worth = createNumber("worth", Integer.class);

    public QUnitPrice(String variable) {
        this(UnitPrice.class, forVariable(variable), INITS);
    }

    public QUnitPrice(Path<? extends UnitPrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUnitPrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUnitPrice(PathMetadata metadata, PathInits inits) {
        this(UnitPrice.class, metadata, inits);
    }

    public QUnitPrice(Class<? extends UnitPrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project"), inits.get("project")) : null;
    }

}

