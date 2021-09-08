package pl.estrix.backend.shipment.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.base.QueryDslRepositorySupportBase;
import pl.estrix.backend.shipment.dao.QShipmentProduct;
import pl.estrix.backend.shipment.dao.ShipmentProduct;
import pl.estrix.common.dto.ShipmentProductSearchCriteriaDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ShipmentProductRepositoryCustomImpl extends QueryDslRepositorySupportBase implements ShipmentProductRepositoryCustom{

    private static final QShipmentProduct shipmentProduct = new QShipmentProduct("shipmentProduct");

    public ShipmentProductRepositoryCustomImpl() {
        super(ShipmentProduct.class);
    }

    @Override
    public List<ShipmentProduct> find(ShipmentProductSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
//        query.orderBy(shipmentProduct.id.asc());
        query.orderBy(shipmentProduct.name.asc());
//        query.orderBy(shipmentProduct.lastUpdate.desc());
        addPagingCriteriaToQuery(query, pagingCriteria);
        return query.list(Projections.bean(
                ShipmentProduct.class,
                shipmentProduct.id,
                shipmentProduct.artNumber,
                shipmentProduct.name,
                shipmentProduct.counter,
                shipmentProduct.company,
                shipmentProduct.artReturn,
                shipmentProduct.ean,
                shipmentProduct.weight,
                shipmentProduct.artVolume,
                shipmentProduct.shipmentId,
                shipmentProduct.scanCorrect,
                shipmentProduct.scanError,
                shipmentProduct.scanLabel,
                shipmentProduct.scanUtilization,
                shipmentProduct.scanLog,
                shipmentProduct.lastUpdate
        ));
    }

    @Override
    public long findCount(ShipmentProductSearchCriteriaDto searchCriteria) {
        JPQLQuery query = getQueryForFind(searchCriteria);
        return query.count();
    }

    private JPQLQuery getQueryForFind(ShipmentProductSearchCriteriaDto searchParams) {
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery query = from(shipmentProduct);
        if (searchParams.getShipmentId() > 0){
            builder.and(shipmentProduct.shipmentId.eq(searchParams.getShipmentId()));
        }
        if (StringUtils.isNotEmpty(searchParams.getEan())){
            builder.and(shipmentProduct.ean.like("%"+searchParams.getEan()+"%"));
        }
        if (StringUtils.isNotEmpty(searchParams.getTableSearch())){
            builder.and(
                    shipmentProduct.ean.like("%"+searchParams.getTableSearch()+"%")
            ).or(shipmentProduct.name.like("%"+searchParams.getTableSearch()+"%"));
        }
        if (StringUtils.isNotEmpty(searchParams.getArtReturn())){
            builder.and(shipmentProduct.artReturn.eq(searchParams.getArtReturn()));
        }
        if (searchParams.getArtNumber() != null && searchParams.getArtNumber() > 0){
            builder.and(shipmentProduct.artNumber.eq(searchParams.getArtNumber()));
        }
        if (searchParams.getSyncDate() != null ){

            builder.and(
                    shipmentProduct.lastUpdate.after(LocalDateTime.parse(searchParams.getSyncDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmm"))).or(
                            shipmentProduct.lastUpdate.eq(LocalDateTime.parse(searchParams.getSyncDate(), DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                    )
            );
        }
        query.where(builder);
        return query;
    }
}
