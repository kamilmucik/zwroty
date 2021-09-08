package pl.estrix.backend.user.executor;

import org.springframework.stereotype.Component;
import pl.estrix.common.dto.model.UserDto;

@Component
public class UpdateUserCommandExecutor extends BaseUserCommandExecutor{

    public UserDto update(UserDto dto) {
        return this.mapEntityToDto(
                repository.save(this.mapDtoToEntity(dto)
                ));
    }
}
