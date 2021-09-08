package pl.estrix.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.estrix.backend.base.PagingCriteria;
import pl.estrix.backend.user.executor.CreateUserCommandExecutor;
import pl.estrix.backend.user.executor.DeleteUserCommandExecutor;
import pl.estrix.backend.user.executor.ReadUserCommandExecutor;
import pl.estrix.backend.user.executor.UpdateUserCommandExecutor;
import pl.estrix.common.base.ListResponseDto;
import pl.estrix.common.dto.UserSearchCriteriaDto;
import pl.estrix.common.dto.model.UserDto;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private ReadUserCommandExecutor readExecutor;
    @Autowired
    private CreateUserCommandExecutor createExecutor;
    @Autowired
    private UpdateUserCommandExecutor updateExecutor;
    @Autowired
    private DeleteUserCommandExecutor deleteExecutor;

    @Autowired
    private PasswordEncoder standardPasswordEncoder;


    @Transactional
    public ListResponseDto<UserDto> getItems(UserSearchCriteriaDto searchCriteria, PagingCriteria pagingCriteria){
        return readExecutor.find(searchCriteria,pagingCriteria);
    }

    @Transactional
    public UserDto saveOrUpdate(UserDto userDto) throws NoSuchAlgorithmException{
        UserDto temp = readExecutor.findById(userDto.getId());

        if (userDto.getId() != null){
            userDto.setPassword(temp.getPassword());
            temp = updateExecutor.update(userDto);
        } else {
            temp = createExecutor.create(userDto);
        }
        return temp;
    }

    @Transactional
    public UserDto getItem(Long id){
        return readExecutor.findById(id);
    }

    @Transactional
    public UserDto getItem(String login){
        return readExecutor.findByLogin(login);
    }

    public List<UserDto> getItems(){
        return readExecutor
                .getRepository()
                .findAll()
                .stream()
                .map(readExecutor::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete (UserDto storeDto){
        deleteExecutor.delete(storeDto);
    }

    public UserDto changePassword(UserDto userDto) {
        UserDto temp = readExecutor.findById(userDto.getId());
        temp.setPassword(standardPasswordEncoder.encode(userDto.getPassword()));

        if (temp.getId() != null){
            temp = updateExecutor.update(temp);
        }
        return temp;
    }
}
