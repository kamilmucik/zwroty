package pl.estrix.backend.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.estrix.backend.user.dao.Role;
import pl.estrix.common.dto.model.UserDto;
import pl.estrix.backend.user.executor.ReadUserCommandExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserLoginService implements UserDetailsService{

    private static final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private ReadUserCommandExecutor readUser;

    /**
     * Finds user by username which is in this case email
     *
     * @param email user email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = readUser.getByEmail(UserDto.builder().email(email).build());

        if (user == null)
            throw new UsernameNotFoundException("User " + email + " not found");

        List<Role> roles = new ArrayList<>();
        roles.add(Role.valueOf(user.getRole()));
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        boolean enabled = user.isEnabled();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = !user.isLocked();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}