package pl.estrix.backend.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.category.dao.CategoryGroup;

import java.util.List;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long>, QueryDslPredicateExecutor<CategoryGroup> {

    @Query("from CategoryGroup t")
    List<CategoryGroup> getAllCategoryGroup();
}
