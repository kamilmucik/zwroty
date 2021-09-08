package pl.estrix.frontend.web.user;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import pl.estrix.backend.user.service.UserService;
import pl.estrix.common.dto.model.UserDto;
import pl.estrix.frontend.jsf.FacesViewScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Component("userEditView")
@Scope(FacesViewScope.NAME)
@Getter
@Setter
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserEditBean implements Serializable {

    @Autowired
    private UserService userService;

    private UserDto selectedUser;

    private Long id;

    private Integer tablePageInx;

    @PostConstruct
    public void init() {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            try {
                id = Long.parseLong(request.getParameter("id"));
                tablePageInx = Integer.parseInt(request.getParameter("table_page"));
            }catch (NumberFormatException e) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
                String username = securityContext.getAuthentication().getName();

                selectedUser = userService.getItem(username);
                id =selectedUser.getId();
                tablePageInx = 0;
            }

            if (id == null || id == 0) {
                selectedUser = new UserDto();
            }else{
                selectedUser = userService.getItem(id);
            }
    }

    public void saveDetail() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            userService.saveOrUpdate(selectedUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapis rekordu", ""));
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zapis rekordu", ""));
        }
    }

    public void changePassword() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            userService.changePassword(selectedUser);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Zapis rekordu", ""));
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Zapis rekordu", ""));
        }
    }

    public String saveAndReturn() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "saveAndReturn.id: " + id, "saveAndReturn"));
        return "saveAndOut";
    }

    public String returnToList(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "saveAndReturn.id: " + id, "saveAndReturn"));
        return "saveAndOut";
    }
}
