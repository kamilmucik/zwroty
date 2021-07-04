package pl.estrix.backend.store.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 770759261L;

    public static final QStore store = new QStore("store");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> group = createNumber("group", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Double> maxVolume = createNumber("maxVolume", Double.class);

    public final NumberPath<Double> minVolume = createNumber("minVolume", Double.class);

    public final StringPath place = createString("place");

    public QStore(String variable) {
        super(Store.class, forVariable(variable));
    }

    public QStore(Path<? extends Store> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStore(PathMetadata<?> metadata) {
        super(Store.class, metadata);
    }

}

