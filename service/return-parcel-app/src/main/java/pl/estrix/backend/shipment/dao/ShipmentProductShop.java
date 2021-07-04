package pl.estrix.backend.shipment.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "shipment_product_shop",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentProductShop extends AuditableEntity {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "art_number", length = 50)
    private Long artNumber;

    @Column(name = "shop_number")
    private Long shopNumber;

    @Column(name = "recognition_number")
    private Long recognitionNumber;

    @Column(name = "ship_number")
    private Long shipNumber;

    @Column(name = "recognition_counter")
    private Long recognitionCounter;

    @Column(name = "scan_correct", length = 50, columnDefinition="int(10) default '0'")
    private Long scanCorrect;

    @Column(name = "scan_error", length = 50, columnDefinition="int(10) default '0'")
    private Long scanError;

    @Column(name = "scan_label", length = 50, columnDefinition="int(10) default '0'")
    private Long scanLabel;

    @Column(name = "art_return", length = 50)
    private String artReturn;

//    @Column(name = "_archivated", nullable = false, columnDefinition = "default '0'")
//    private Boolean archivated;

}
