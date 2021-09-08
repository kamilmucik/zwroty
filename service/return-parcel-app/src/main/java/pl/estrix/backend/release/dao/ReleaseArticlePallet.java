package pl.estrix.backend.release.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;

import javax.persistence.*;

@Entity
@Table(name = "release_article_pallet",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReleaseArticlePallet extends AuditableEntity {

        @ManyToOne
        @JoinColumn(name="release_article_id")
        private ReleaseArticle releaseArticle;

        @Column(name = "release_code", length = 4096)
        private String releaseCode;

        @Column(name = "art_number", length = 4096)
        private String artNumber;

        @Column(name = "return_number", length = 10)
        private String returnNumber;

        @Column(name = "product_ean", length = 4096)
        private String productEan;

        @Column(name = "pallet_flag", length = 10)
        private String palletFlag;;

        @Column(name = "counter", length = 50)
        private Long counter;

        @Column(name = "pallet_counter", length = 50)
        private Long palletCounter;

}
