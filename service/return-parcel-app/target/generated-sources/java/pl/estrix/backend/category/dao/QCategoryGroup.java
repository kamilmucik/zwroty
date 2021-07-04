package pl.estrix.backend.category.dao;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QCategoryGroup is a Querydsl query type for CategoryGroup
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCategoryGroup extends EntityPathBase<CategoryGroup> {

    private static final long serialVersionUID = -1800155952L;

    public static final QCategoryGroup categoryGroup = new QCategoryGroup("categoryGroup");

    public final pl.estrix.backend.base.QAuditableEntity _super = new pl.estrix.backend.base.QAuditableEntity(this);

    public final ListPath<Category, QCategory> categories = this.<Category, QCategory>createList("categories", Category.class, QCategory.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public QCategoryGroup(String variable) {
        super(CategoryGroup.class, forVariable(variable));
    }

    public QCategoryGroup(Path<? extends CategoryGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryGroup(PathMetadata<?> metadata) {
        super(CategoryGroup.class, metadata);
    }

}

