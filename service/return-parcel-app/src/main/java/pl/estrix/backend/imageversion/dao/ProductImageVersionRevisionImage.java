//package pl.estrix.backend.imageversion.dao;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import pl.estrix.backend.base.AuditableEntity;
//import pl.estrix.backend.converter.LocalDateToStringConverter;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "product_image_version_revision_image",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"id"})
//        })
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class ProductImageVersionRevisionImage extends AuditableEntity {
//
////    @ManyToOne
////    @JoinColumn(name="product_image_version_revision_id")
////    private ProductImageVersionRevision productImageVersionRevision;
//
//    @Column(name = "image_base64", length = 4096)
//    private String imageBase64;
//}
