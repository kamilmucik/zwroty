package pl.estrix.backend.collector.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCollector is a Querydsl query type for Collector
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCollector extends EntityPathBase<Collector> {

    private static final long serialVersionUID = 1948081205L;

    public static final QCollector collector = new QCollector("collector");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath number = createString("number");

    public final StringPath sessionId = createString("sessionId");

    public QCollector(String variable) {
        super(Collector.class, forVariable(variable));
    }

    public QCollector(Path<? extends Collector> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollector(PathMetadata<?> metadata) {
        super(Collector.class, metadata);
    }

}

