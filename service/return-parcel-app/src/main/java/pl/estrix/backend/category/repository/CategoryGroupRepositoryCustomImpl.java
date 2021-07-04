package pl.estrix.backend.category.repository;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.category.dao.Category;
import pl.estrix.backend.category.dao.CategoryGroup;
import pl.estrix.backend.category.dao.QCategory;
import pl.estrix.backend.category.dao.QCategoryGroup;

import java.util.List;

@Repository
public class CategoryGroupRepositoryCustomImpl extends QueryDslRepositorySupport implements CategoryGroupRepositoryCustom {

    private static final QCategoryGroup categoryGroup = new QCategoryGroup("categoryGroup");
    private static final QCategory category = new QCategory("category");

    public CategoryGroupRepositoryCustomImpl() {
        super(CategoryGroup.class);
    }

    @Override
    public List<CategoryGroup> getItemList() {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        JPQLQuery query = from(categoryGroup);

        query.join(categoryGroup.categories,category);

        booleanBuilder.and(categoryGroup.id.gt(0L));

        query.where(booleanBuilder);

        return query.list(Projections.bean(
                CategoryGroup.class,
                categoryGroup.id,
                categoryGroup.name,
                Projections.bean(Category.class, category.id, category.name).as("categories")
                ));
    }
}
