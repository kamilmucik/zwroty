package pl.estrix.backend.base;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAuditableEntity is a Querydsl query type for AuditableEntity
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QAuditableEntity extends EntityPathBase<AuditableEntity> {

    private static final long serialVersionUID = 1536867862L;

    public static final QAuditableEntity auditableEntity = new QAuditableEntity("auditableEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAuditableEntity(String variable) {
        super(AuditableEntity.class, forVariable(variable));
    }

    public QAuditableEntity(Path<? extends AuditableEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditableEntity(PathMetadata<?> metadata) {
        super(AuditableEntity.class, metadata);
    }

}

