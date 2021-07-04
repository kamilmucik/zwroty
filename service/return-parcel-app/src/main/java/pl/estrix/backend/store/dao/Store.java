package pl.estrix.backend.store.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "store",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
                //,@UniqueConstraint(columnNames = {"volume"}, name = "uidx_store_volume")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store extends AuditableEntity {

    @Column(name = "min_volume",  nullable = false)
    private Double minVolume;

    @Column(name = "max_volume",  nullable = false)
    private Double maxVolume;

    @Size(min = 1, max = 50)
    @Column(name = "place", length = 50, nullable = false)
    private String place;

    @Column(name = "groups", nullable = false, columnDefinition = "int default 1")
    private Integer group;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

}
