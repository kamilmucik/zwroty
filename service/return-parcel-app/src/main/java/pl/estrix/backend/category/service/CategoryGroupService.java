package pl.estrix.backend.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.category.dto.CategoryGroupDto;
import pl.estrix.backend.category.executor.*;

import java.util.List;

@Service
public class CategoryGroupService {

    @Autowired
    private CreateCategoryGroupCommandExecutor create;

    @Autowired
    private DeleteCategoryGroupCommandExecutor delete;

    @Autowired
    private ReadCategoryGroupCommandExecutor read;

    @Autowired
    private UpdateCategoryGroupCommandExecutor update;

    @Autowired
    private CreateCategoryCommandExecutor createCategory;

    @Autowired
    private DeleteCategoryCommandExecutor deleteCategory;

    @Autowired
    private ReadCategoryCommandExecutor readCategory;

    @Autowired
    private UpdateCategoryCommandExecutor updateCategory;

    public List<CategoryGroupDto> getCategoryGroup() {
        return read.getItemList();
    }

   public CategoryGroupDto save(CategoryGroupDto categoryGroupDto) {
     return categoryGroupDto;
    }

    public void delete(long id) {
    }

    public CategoryGroupDto get(long id) {
        return read.findOne(id);
    }
}
