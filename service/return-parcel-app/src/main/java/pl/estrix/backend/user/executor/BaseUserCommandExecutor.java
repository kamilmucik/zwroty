package pl.estrix.backend.user.executor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.estrix.backend.base.BaseCommandExecutor;
import pl.estrix.backend.user.dao.Role;
import pl.estrix.backend.user.dao.User;
import pl.estrix.common.dto.model.UserDto;
import pl.estrix.backend.user.repository.UserRepository;
import pl.estrix.backend.user.repository.UserRepositoryCustom;

@Data
public class BaseUserCommandExecutor extends BaseCommandExecutor<User, UserDto> {

    @Autowired
    protected UserRepository repository;

    @Autowired
    protected UserRepositoryCustom customRepository;

    @Override
    protected Class<UserDto> getDtoClass() {
        return UserDto.class;
    }

    public User mapDtoToEntity(UserDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setEnabled(dto.isEnabled());
        entity.setLocked(dto.isLocked());
        entity.setFirstName(dto.getUserName());
        entity.setLastName(dto.getUserLastname());
        entity.setRole(dto.getRole() );
        return entity;
    }

    public UserDto mapEntityToDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setEnabled(entity.isEnabled());
        dto.setLocked(entity.isLocked());
        dto.setUserName(entity.getFirstName());
        dto.setUserLastname(entity.getLastName());
        dto.setRole(entity.getRole());
        return dto;
    }
}
