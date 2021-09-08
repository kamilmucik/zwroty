package pl.estrix.backend.collector.dao;

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
@Table(name = "collector",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"}),
                @UniqueConstraint(columnNames = {"number"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collector extends AuditableEntity {

    @Column(name = "number", length = 50, nullable = false)
    private String number;

    @Column(name = "session_id", length = 50, nullable = false)
    private String sessionId;

}
