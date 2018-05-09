/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.registro;

import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.managedbean.LoginBean;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Admin
 */
@Named
@FlowScoped("registro")
public class Registro implements Serializable {
    
    private String email, pass, nombre, tel;
    private Date fechaNac;
    private BigInteger numTarjeta;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction et;
    
    public String registrarUsuario() throws RollbackException, HeuristicMixedException, HeuristicRollbackException {
        Usuario user = new Usuario();
            user.setTipo("Candidato");
            user.setEmail(email);
            user.setNombre(nombre);
            user.setPass(getPass());
            user.setFechaNacimiento(fechaNac);
            user.setTelefono(tel);
            user.setTarjeta(numTarjeta);
        
        try {
            
            
            et.begin();;
            em.joinTransaction();
            em.persist(user);
            et.commit();
            
            
        } catch (NotSupportedException | SystemException ex) {
            System.out.println("Se jodiooooo");
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "/candidato/candidatoIndex.xhtml";
    }
    
    public String fechaHoy() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPass() {
        if (pass != null) {
            return SHA1(this.pass);
        }
        
        return null;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTel() {
        return tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
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
}
