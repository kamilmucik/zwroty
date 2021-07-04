package pl.estrix.backend.shipment.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.shipment.dao.QShipment;
import pl.estrix.backend.shipment.dao.QShipmentProductShop;
import pl.estrix.backend.shipment.dao.Shipment;
import pl.estrix.backend.shipment.dao.ShipmentProductShop;
import pl.estrix.common.dto.ShipmentProductShopSearchCriteriaDto;
import pl.estrix.common.dto.ShipmentSearchCriteriaDto;

import java.util.List;

@Repository
public class ShipmentProductShopRepositoryCustomImpl extends QueryDslRepositorySupportBase implements ShipmentProductShopRepositoryCustom {

    private static final QShipmentProductShop shipmentProductShop = new QShipmentProductShop("shipmentProductShop");

    public ShipmentProductShopRepositoryCustomImpl() {
        super(Shipment.class);
    }

    @Override
    public List<ShipmentProductShop> find(ShipmentProductShopSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        query.orderBy(shipmentProductShop.id.asc());
//        query.limit(5);
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                ShipmentProductShop.class,
                shipmentProductShop.id,
                shipmentProductShop.productId,
                shipmentProductShop.artNumber,
                shipmentProductShop.artReturn,
                shipmentProductShop.recognitionCounter,
                shipmentProductShop.recognitionNumber,
                shipmentProductShop.shipNumber,
                shipmentProductShop.shopNumber,
                shipmentProductShop.scanCorrect,
                shipmentProductShop.scanLabel,
                shipmentProductShop.scanError
//                shipmentProductShop.scanError,
//                shipmentProductShop.archivated
        ));
    }

    @Override
    public long findCount(ShipmentProductShopSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ShipmentProductShopSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(shipmentProductShop);

//        shipmentProductShop.archivated.eq(Boolean.FALSE);

        if (searchParams.getProductId() != null) {
            query.where(shipmentProductShop.productId.eq(searchParams.getProductId()));
        }
        if (searchParams.getArtNumber() != null) {
            query.where(shipmentProductShop.artNumber.eq(searchParams.getArtNumber()));
        }
        if (searchParams.getShopNumber() != null) {
            query.where(shipmentProductShop.shopNumber.eq(searchParams.getShopNumber()));
        }

        if (StringUtils.isNotEmpty(searchParams.getArtReturn())){
            query.where(
                    shipmentProductShop.artReturn.like("%"+searchParams.getArtReturn()+"%")
            );
        }

        query.where(builder);
//        query.limit(2);
        return query;
    }
}
