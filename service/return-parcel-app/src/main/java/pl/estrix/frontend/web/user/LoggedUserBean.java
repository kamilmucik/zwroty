package pl.estrix.frontend.web.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import pl.estrix.backend.user.service.UserService;
import pl.estrix.common.dto.model.UserDto;
import pl.estrix.frontend.jsf.FacesViewScope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Component("loggedUser")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
public class LoggedUserBean implements Serializable {

    private UserDto user;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
//        System.out.println("LoggedUserBean");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        String username = securityContext.getAuthentication().getName();

        user = userService.getItem(username);
    }

}
