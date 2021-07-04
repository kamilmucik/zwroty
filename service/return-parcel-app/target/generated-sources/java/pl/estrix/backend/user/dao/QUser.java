package pl.estrix.backend.user.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1510619279L;

    public static final QUser user = new QUser("user");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath firstName = createString("firstName");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath lastName = createString("lastName");

    public final BooleanPath locked = createBoolean("locked");

    public final StringPath password = createString("password");

    public final StringPath role = createString("role");

    public final BooleanPath subscribed = createBoolean("subscribed");

    public final StringPath verificationKey = createString("verificationKey");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata<?> metadata) {
        super(User.class, metadata);
    }

}

