package pl.estrix.backend.shipment.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QShipmentProductShop is a Querydsl query type for ShipmentProductShop
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QShipmentProductShop extends EntityPathBase<ShipmentProductShop> {

    private static final long serialVersionUID = -14330730L;

    public static final QShipmentProductShop shipmentProductShop = new QShipmentProductShop("shipmentProductShop");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final NumberPath<Long> artNumber = createNumber("artNumber", Long.class);

    public final StringPath artReturn = createString("artReturn");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> recognitionCounter = createNumber("recognitionCounter", Long.class);

    public final NumberPath<Long> recognitionNumber = createNumber("recognitionNumber", Long.class);

    public final NumberPath<Long> scanCorrect = createNumber("scanCorrect", Long.class);

    public final NumberPath<Long> scanError = createNumber("scanError", Long.class);

    public final NumberPath<Long> scanLabel = createNumber("scanLabel", Long.class);

    public final NumberPath<Long> shipNumber = createNumber("shipNumber", Long.class);

    public final NumberPath<Long> shopNumber = createNumber("shopNumber", Long.class);

    public QShipmentProductShop(String variable) {
        super(ShipmentProductShop.class, forVariable(variable));
    }

    public QShipmentProductShop(Path<? extends ShipmentProductShop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShipmentProductShop(PathMetadata<?> metadata) {
        super(ShipmentProductShop.class, metadata);
    }

}

