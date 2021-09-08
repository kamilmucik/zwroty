package pl.estrix.backend.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import pl.estrix.backend.category.dao.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>, QueryDslPredicateExecutor<Category> {

}
