package pl.estrix.backend.release.dao;

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
import java.util.List;

@Entity
@Table(name = "release_article",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReleaseArticle extends AuditableEntity {

        @Column(name = "release_date")
        @Convert(converter = LocalDateToStringConverter.class)
        private LocalDate releaseDate;

        @OneToMany(mappedBy="releaseArticle")
        private List<ReleaseArticlePallet> pallets;

}
