package pl.estrix.backend.category.repository;


import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;
import pl.estrix.backend.category.dao.Category;

@Repository
public class CategoryRepositoryCustomImpl extends QueryDslRepositorySupport implements CategoryRepositoryCustom{

    public CategoryRepositoryCustomImpl() {
        super(Category.class);
    }
}
