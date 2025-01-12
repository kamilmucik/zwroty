package pl.estrix.backend.print.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.estrix.backend.base.AuditableEntity;
import pl.estrix.backend.converter.LocalDateTimeToStringConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "printer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Printer extends AuditableEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "_path", nullable = false)
    private String path;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "last_update")
    @Convert(converter = LocalDateTimeToStringConverter.class)
    private LocalDateTime lastUpdate;
}
