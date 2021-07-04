package pl.estrix.backend.category.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.category.dto.CategoryGroupDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadCategoryGroupCommandExecutor extends BaseCategoryGroupCommandExecutor {

    public List<CategoryGroupDto> getItemList(){
        return repository
                .getAllCategoryGroup()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public CategoryGroupDto findOne(long id) {
        return mapEntityToDto(repository.getOne(id));
    }
}
