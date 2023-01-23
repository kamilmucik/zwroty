package pl.estrix.backend.imageversion.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;
import pl.estrix.backend.converter.LocalDateToStringConverter;
import pl.estrix.backend.release.dao.ReleaseArticle;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * https://medium.com/shoutloudz/spring-boot-upload-and-download-images-using-jpa-b1c9ef174dc0
 */
@Entity
@Table(name = "product_image_version_revision",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageVersionRevision extends AuditableEntity {

    @ManyToOne
    @JoinColumn(name="product_image_version_id")
    private ProductImageVersion productImageVersion;

    @Column(name = "version_date")
    @Convert(converter = LocalDateToStringConverter.class)
    private LocalDate releaseDate;

    @Column(name = "product_image_version_id", insertable = false,updatable = false)
    private Long parentId;

    @Column(name = "reason", length = 4096)
    private String reason;

    @Column(name = "img_front", length = 420000)
    private String imgFrontBase64;
    @Column(name = "img_back", length = 420000)
    private String imgBackBase64;

    @Column(name = "img_left", length = 420000)
    private String imgLeftBase64;
    @Column(name = "img_right", length = 420000)
    private String imgRightBase64;

    @Column(name = "img_top", length = 420000)
    private String imgTopBase64;
    @Column(name = "img_bottom", length = 420000)
    private String imgBottomBase64;

}
