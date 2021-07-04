package pl.estrix.backend.category.dao;


import lombok.Data;
import pl.estrix.backend.base.AuditableEntity;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Data
public class Category extends AuditableEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_group_id", referencedColumnName = "id", nullable = false)
    private CategoryGroup categoryGroup;

}
