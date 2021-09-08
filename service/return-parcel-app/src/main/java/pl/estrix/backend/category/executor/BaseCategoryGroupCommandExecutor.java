package pl.estrix.backend.category.executor;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.category.dao.CategoryGroup;
import pl.estrix.backend.category.dto.CategoryDto;
import pl.estrix.backend.category.dto.CategoryGroupDto;
import pl.estrix.backend.category.repository.CategoryGroupRepository;
import pl.estrix.backend.category.repository.CategoryGroupRepositoryCustom;

import java.util.stream.Collectors;

@Data
public class BaseCategoryGroupCommandExecutor extends BaseCommandExecutor<CategoryGroup, CategoryGroupDto> {

    @Autowired
    protected CategoryGroupRepository repository;

    @Autowired
    protected CategoryGroupRepositoryCustom customRepository;

    @Override
    protected Class<CategoryGroupDto> getDtoClass() {
        return CategoryGroupDto.class;
    }

    public CategoryGroup mapDtoToEntity(CategoryGroupDto dto) {
        CategoryGroup entity = new CategoryGroup();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public CategoryGroupDto mapEntityToDto(CategoryGroup category) {
        CategoryGroupDto dto = new CategoryGroupDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.getCategories().addAll(category
                .getCategories()
                .stream()
                .map( m -> {
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setId(m.getId());
                    categoryDto.setName(m.getName());
                    return categoryDto;
                })
                .collect(Collectors.toList()));
        return dto;
    }
}
