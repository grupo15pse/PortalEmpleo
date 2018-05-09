/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.jaas.AuthenticationUtils;
import grupo15.portalempleo.managedbean.LoginBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
@Named(value = "empresaBackingBean")
@SessionScoped
public class EmpresaBackingBean implements Serializable {
    
    private int empresaId;
    private String email,nombre,pass;

    /**
     * Creates a new instance of EmpresaBackingBean
     */
    public EmpresaBackingBean() {
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        if(pass != null)
            try {
                return AuthenticationUtils.encodeSHA256(pass);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(EmpresaBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
}
