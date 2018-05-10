/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.jaas;

import grupo15.portalempleo.client.UsuarioClientBean;
import grupo15.portalempleo.entities.Grupo;
import grupo15.portalempleo.entities.Usuario;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Admin
 */
@Named
@FlowScoped("registro")
public class Registro implements Serializable {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String tel;
    private Date fechaNac;
    private BigInteger numTarjeta;
    
    @Inject
    private UsuarioClientBean clientBean;

    public String register() {
        Usuario user = new Usuario();
        user.setNombre(name);
        user.setEmail(email);
        user.setTipo("candidato");
        user.setFechaNacimiento(fechaNac);
        user.setTarjeta(numTarjeta);
        user.setTelefono(tel);
        try {
            user.setPass(AuthenticationUtils.encodeSHA256(password));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Grupo grupo = new Grupo();
        grupo.setEmail(email);
        grupo.setNombreGrupo("candidato");

        clientBean.addCandidato(user,grupo);
        
        /*FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            System.out.println(password);
            request.login(email, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login incorrecto!", null));
            return "login";
        }*/
        //this.user = userEJB.findByEmail(request.getUserPrincipal().getName());

        return "goHome";
    }

    public String fechaHoy() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public BigInteger getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(BigInteger numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
