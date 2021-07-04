package pl.estrix.backend.user.executor;

import org.springframework.stereotype.Component;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.user.dao.User;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.model.UserDto;
import pl.estrix.common.dto.UserSearchCriteriaDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadUserCommandExecutor extends BaseUserCommandExecutor{

    public UserDto getByEmail(UserDto userDto){
        return mapEntityToDto(repository.findOneByEmail(userDto.getEmail()));
    }

    public UserDto findById(Long id) {
        return mapEntityToDto(repository.findOne(id));
    }

    public UserDto findByLogin(String login) {
        return mapEntityToDto(repository.findOneByEmail(login) );
    }

    public ListResponseDto<UserDto> find(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria) {
        List<User> result = customRepository.find(searchCriteria, pagingCriteria);
        List<UserDto> queryResultList = result
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return createListResponseDto(pagingCriteria, () -> queryResultList, () -> (int) customRepository.findCount(searchCriteria));
    }
}
