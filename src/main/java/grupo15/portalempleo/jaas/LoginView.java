/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.jaas;

import grupo15.portalempleo.entities.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@ManagedBean
@SessionScoped
public class LoginView implements Serializable {

    private String email;
    private String password;

    @Inject
    private UserEJB userEJB;

    private Usuario user;

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            System.out.println(password);
            request.login(email, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login incorrecto!", null));
            return "login";
        }
        this.user = userEJB.findByEmail(request.getUserPrincipal().getName());
        System.out.println(user.getPass());
        /*if (request.isUserInRole("administrador")) {
            return "/admin/adminIndex?faces-redirect=true";
        } else if (request.isUserInRole("empresa")) {
            return "/empresa/empresaIndex?faces-redirect=true";
        }else if (request.isUserInRole("candidato")) {
            return "/candidato/candidatoIndex?faces-redirect=true";
        }else {
            return "login";
        }*/
        
        if (user.getTipo().equals("administrador")) {
            return "/admin/adminIndex?faces-redirect=true";
        } else if (user.getTipo().equals("empresa")) {
            return "/empresa/empresaIndex?faces-redirect=true";
        }else if (user.getTipo().equals("candidato")) {
            return "/candidato/candidatoIndex?faces-redirect=true";
        }else {
            return "login";
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            this.user = null;
            request.logout();
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();
        } catch (ServletException e) {
            
        }
        return "/index?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getAuthenticatedUser() {
        return user;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}
