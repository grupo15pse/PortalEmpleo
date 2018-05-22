/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.jaas;

import grupo15.portalempleo.client.EditarPerfilBakingBean;
import grupo15.portalempleo.entities.Formacion;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.rest.FormacionFacadeREST;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    
    @Inject
    private FormacionFacadeREST formacionFacadeREST;
    
    @Inject
    private EditarPerfilBakingBean epbb;

    private Usuario user;

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(email, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login incorrecto!", null));
            return "login";
        }
        this.user = userEJB.findByEmail(request.getUserPrincipal().getName());
        
        if (user.getTipo().equalsIgnoreCase("administrador")) {
            return "/admin/adminIndex?faces-redirect=true";
        } else if (user.getTipo().equalsIgnoreCase("empresa")) {
            return "/empresa/empresaIndex?faces-redirect=true";
        }else if (user.getTipo().equalsIgnoreCase("candidato")) {
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
    
    public String updateCandidato() {
        
        if(epbb.getFecha() != null) {
            user.setFechaNacimiento(epbb.getFecha());
        }
        
        if(epbb.getNombre() != null) {
            user.setNombre(epbb.getNombre());
        }
        
        if(epbb.getTarjeta()!= null) {
            user.setTarjeta(epbb.getTarjeta());
        }
        
        if(epbb.getTel() != null) {
            user.setTelefono(epbb.getTel());
        }

        userEJB.updateUsuario(user);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Éxito",  "El candidato " + user.getNombre() + " ha sido actualizado."));
        
        return "perfil";
    }
    
    public String deleteUsuario() {
        userEJB.deleteUsuario(user);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Éxito",  "El usuario " + user.getNombre() + " ha sido borrado."));
        
        return logout();
    }
    
    public String getFormacion() {
        Formacion formacion = formacionFacadeREST.findFormacionByCandidato(user.getUsuarioId()).get(0);
        return formacion.getFormacionPK().getFormacion();
    }
    
    public String fechaHace16Anos() {
        Date fechaHace16 = null;
        Calendar hoy = Calendar.getInstance();
        hoy.add(Calendar.YEAR, -16);
        fechaHace16 = hoy.getTime();
        
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(fechaHace16);
    }
    
    public String fechaHace65Anos() {
        Date fechaHace65 = null;
        Calendar hoy = Calendar.getInstance();
        hoy.add(Calendar.YEAR, -65);
        fechaHace65 = hoy.getTime();
        
        
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(fechaHace65);
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


    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

}
