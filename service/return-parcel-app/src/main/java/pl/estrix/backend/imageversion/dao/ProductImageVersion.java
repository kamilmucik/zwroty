package pl.estrix.backend.imageversion.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;
import pl.estrix.backend.converter.LocalDateToStringConverter;
import pl.estrix.backend.release.dao.ReleaseArticlePallet;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "product_image_version",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageVersion extends AuditableEntity {

    @Column(name = "art_number", length = 50)
    private Long artNumber;

    @Column(name = "ean", length = 4096)
    private String ean;

    @Column(name = "title", length = 4096)
    private String title;

    @Column(name = "last_version_date")
    @Convert(converter = LocalDateToStringConverter.class)
    private LocalDate latVersionDate;

    @OneToMany(mappedBy="productImageVersion")
    private List<ProductImageVersionRevision> revisions;


}
