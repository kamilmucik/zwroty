package pl.estrix.frontend.jsf.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@Component("sessionMB")
@Scope("session")
public class SessionMB implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(SessionMB.class);

    private String email;

    private char[] password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String loginAction() {
        FacesMessage message = null;
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.email, String.valueOf(this.password)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            password = null;
            return "/secured/view/preview.html?faces-redirect=true";
        } catch (BadCredentialsException e) {
            log.error("Bad credentials for user {}", email);

            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Złe dane logowania", null);
        } catch (DisabledException e) {
            log.error("User {} is disabled", email);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Konto zablokowane", null);
        } catch (AuthenticationException e) {
            log.error("Exception in authentication for user {}", email);
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Złe dane logowania", null);
        }
        if (message != null) {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage("loginFormMessages", message);
        }

        return "";
    }

    public String logoutAction(){
        SecurityContextHolder.clearContext();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.html?faces-redirect=true";
    }


    private String actionText;

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public String testUserAction(){
        actionText = "User action successfull";
        return "";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public String testAdminAction(){
        actionText = "Admin action successfull";
        return "";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String testSuperuserAction(){
        actionText = "Superuser action successfull";
        return "";
    }
}