package pl.estrix.backend.category.executor;

import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.category.dao.Category;
import pl.estrix.backend.category.dto.CategoryDto;
import pl.estrix.backend.category.repository.CategoryRepository;
import pl.estrix.backend.category.repository.CategoryRepositoryCustom;

public class BaseCategoryCommandExecutor extends BaseCommandExecutor<Category, CategoryDto> {

    @Autowired
    protected CategoryRepository repository;

    @Autowired
    protected CategoryRepositoryCustom customRepository;

    @Override
    protected Class<CategoryDto> getDtoClass() {
        return CategoryDto.class;
    }

    public Category mapDtoToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public CategoryDto mapDtoToEntity(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
