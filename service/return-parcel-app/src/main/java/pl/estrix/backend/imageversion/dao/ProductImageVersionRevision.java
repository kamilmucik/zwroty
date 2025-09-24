package pl.estrix.backend.imageversion.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;
import pl.estrix.backend.converter.LocalDateTimeToStringConverter;
import pl.estrix.backend.converter.LocalDateToStringConverter;
import pl.estrix.backend.release.dao.ReleaseArticle;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * https://medium.com/shoutloudz/spring-boot-upload-and-download-images-using-jpa-b1c9ef174dc0
 */
@Entity
@Table(name = "product_version_revision",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageVersionRevision extends AuditableEntity {

    @ManyToOne
    @JoinColumn(name="product_version_id")
    private ProductImageVersion productImageVersion;

    @Column(name = "img_path", length = 4096)
    private String imgPath;

    @Column(name = "hash_group", length = 32)
    private String hashGroup;

    @Column(name = "order_timestamp")
    private Long orderTimestamp;

    @Column(name = "description", length = 420000)
    private String description;

    @Column(name = "is_main", columnDefinition = "bit(1) default 0")
    private boolean main;

    @Column(name = "last_update")
    @Convert(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime lastUpdate;

}
