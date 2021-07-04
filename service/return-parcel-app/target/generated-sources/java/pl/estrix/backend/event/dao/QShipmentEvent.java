package pl.estrix.backend.event.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShipmentEvent is a Querydsl query type for ShipmentEvent
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QShipmentEvent extends EntityPathBase<ShipmentEvent> {

    private static final long serialVersionUID = -1329757163L;

    public static final QShipmentEvent shipmentEvent = new QShipmentEvent("shipmentEvent");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final NumberPath<Long> collectorId = createNumber("collectorId", Long.class);

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> lastUpdate = createDateTime("lastUpdate", java.time.LocalDateTime.class);

    public final StringPath shipmentNumber = createString("shipmentNumber");

    public final StringPath username = createString("username");

    public QShipmentEvent(String variable) {
        super(ShipmentEvent.class, forVariable(variable));
    }

    public QShipmentEvent(Path<? extends ShipmentEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShipmentEvent(PathMetadata<?> metadata) {
        super(ShipmentEvent.class, metadata);
    }

}

