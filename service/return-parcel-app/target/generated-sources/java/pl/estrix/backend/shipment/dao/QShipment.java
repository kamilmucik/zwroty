package pl.estrix.backend.shipment.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShipment is a Querydsl query type for Shipment
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QShipment extends EntityPathBase<Shipment> {

    private static final long serialVersionUID = -656567185L;

    public static final QShipment shipment = new QShipment("shipment");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Integer> group = createNumber("group", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath number = createString("number");

    public QShipment(String variable) {
        super(Shipment.class, forVariable(variable));
    }

    public QShipment(Path<? extends Shipment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShipment(PathMetadata<?> metadata) {
        super(Shipment.class, metadata);
    }

}

