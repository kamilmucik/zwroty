package pl.estrix.backend.shipment.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShipmentProduct is a Querydsl query type for ShipmentProduct
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QShipmentProduct extends EntityPathBase<ShipmentProduct> {

    private static final long serialVersionUID = 1843185024L;

    public static final QShipmentProduct shipmentProduct = new QShipmentProduct("shipmentProduct");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final NumberPath<Long> artNumber = createNumber("artNumber", Long.class);

    public final StringPath artReturn = createString("artReturn");

    public final NumberPath<Double> artVolume = createNumber("artVolume", Double.class);

    public final StringPath company = createString("company");

    public final NumberPath<Long> counter = createNumber("counter", Long.class);

    public final StringPath ean = createString("ean");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> lastUpdate = createDateTime("lastUpdate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> scanCorrect = createNumber("scanCorrect", Long.class);

    public final NumberPath<Long> scanError = createNumber("scanError", Long.class);

    public final NumberPath<Long> scanLabel = createNumber("scanLabel", Long.class);

    public final StringPath scanLog = createString("scanLog");

    public final NumberPath<Long> scanUtilization = createNumber("scanUtilization", Long.class);

    public final NumberPath<Long> shipmentId = createNumber("shipmentId", Long.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QShipmentProduct(String variable) {
        super(ShipmentProduct.class, forVariable(variable));
    }

    public QShipmentProduct(Path<? extends ShipmentProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShipmentProduct(PathMetadata<?> metadata) {
        super(ShipmentProduct.class, metadata);
    }

}

