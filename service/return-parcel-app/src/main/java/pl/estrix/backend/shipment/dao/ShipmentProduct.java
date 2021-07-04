package pl.estrix.backend.shipment.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;
import pl.estrix.backend.converter.LocalDateTimeToStringConverter;
import pl.estrix.backend.converter.LocalDateToStringConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipment_product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentProduct extends AuditableEntity {

        @Column(name = "art_number", length = 50)
        private Long artNumber;

        @Column(name = "name", length = 50)
        private String name;

        @Column(name = "counter", length = 50)
        private Long counter;

        @Column(name = "company", length = 50)
        private String company;

        @Column(name = "art_return", length = 50)
        private String artReturn;

        /**
         * Lengh is 252 char, it will contains 18 different EAN(13char + space as delimiter) code
         */
        @Column(name = "ean", length = 4096)
        private String ean;

        @Column(name = "weight", length = 50)
        private Double weight;

        @Column(name = "art_volume", length = 50)
        private Double artVolume;

        @Column(name = "shipment_id", length = 50)
        private Long shipmentId;

        @Column(name = "scan_correct", length = 50, columnDefinition="int(10) default '0'")
        private Long scanCorrect;

        @Column(name = "scan_error", length = 50, columnDefinition="int(10) default '0'")
        private Long scanError;

        @Column(name = "scan_label", length = 50, columnDefinition="int(10) default '0'")
        private Long scanLabel;

        @Column(name = "last_update")
        @Convert(converter = LocalDateTimeToStringConverter.class)
        private LocalDateTime lastUpdate;

        @Column(name = "scan_log", length = 4096)
        private String scanLog;

}
