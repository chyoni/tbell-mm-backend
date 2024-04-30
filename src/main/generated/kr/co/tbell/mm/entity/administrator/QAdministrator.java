package kr.co.tbell.mm.entity.administrator;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdministrator is a Querydsl query type for Administrator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdministrator extends EntityPathBase<Administrator> {

    private static final long serialVersionUID = 1790658685L;

    public static final QAdministrator administrator = new QAdministrator("administrator");

    public final kr.co.tbell.mm.entity.QBaseEntity _super = new kr.co.tbell.mm.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isCredentialsExpired = createBoolean("isCredentialsExpired");

    public final BooleanPath isEnabled = createBoolean("isEnabled");

    public final BooleanPath isExpired = createBoolean("isExpired");

    public final BooleanPath isLocked = createBoolean("isLocked");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath password = createString("password");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath username = createString("username");

    public QAdministrator(String variable) {
        super(Administrator.class, forVariable(variable));
    }

    public QAdministrator(Path<? extends Administrator> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdministrator(PathMetadata metadata) {
        super(Administrator.class, metadata);
    }

}

