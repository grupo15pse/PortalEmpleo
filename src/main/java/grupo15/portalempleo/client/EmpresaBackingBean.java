/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.managedbean.LoginBean;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
            return SHA1(this.pass);
        
        return null;
        
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String SHA1(String pass) {
        try {
            byte[] hash = null;
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] array = md.digest(pass.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
}
