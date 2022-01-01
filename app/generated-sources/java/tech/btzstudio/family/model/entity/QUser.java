package tech.btzstudio.family.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1336423473L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final ListPath<Role, QRole> roles = this.<Role, QRole>createList("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final QFile thumbnail;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.thumbnail = inits.isInitialized("thumbnail") ? new QFile(forProperty("thumbnail")) : null;
    }

}

