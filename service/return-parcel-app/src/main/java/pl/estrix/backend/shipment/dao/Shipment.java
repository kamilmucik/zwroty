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
@Table(name = "shipment",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}),
                @UniqueConstraint(columnNames = {"number"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment extends AuditableEntity {

        @Column(name = "number", length = 50, nullable = false)
        private String number;

        @Column(name = "active", nullable = false)
        private Boolean active;

        @Column(name = "_groups", nullable = false, columnDefinition = "int default 1")
        private Integer group;

}
