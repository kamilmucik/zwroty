package pl.estrix.backend.category.dao;


import lombok.Data;
import lombok.Getter;
import pl.estrix.backend.base.AuditableEntity;
import pl.estrix.backend.category.dao.Category;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "category_group")
@Data
public class CategoryGroup extends AuditableEntity {

    private String name;

    @OneToMany(mappedBy = "categoryGroup", cascade = CascadeType.ALL)
    private List<Category> categories = Collections.EMPTY_LIST;
}
