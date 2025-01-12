package pl.estrix.backend.print.dao;

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
@Table(name = "print_file",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintFile extends AuditableEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "_path", nullable = false)
    private String path;

    @Column(name = "printer", nullable = false)
    private String printer;

    @Column(name = "active", nullable = false)
    private Boolean active;

}
