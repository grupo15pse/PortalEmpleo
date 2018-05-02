/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.managedbean;

import grupo15.portalempleo.entities.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private int usuarioId;
    private String email, pass;

    @PersistenceContext
    EntityManager em;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public String procesarInicioSesion() {

        Query q = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        q.setParameter("email", email);
        List<Usuario> lista = q.getResultList();

        pass = SHA1(pass);

        if (lista.isEmpty()) {
            return "loginError";
        } else {
            if (equals(pass, lista.get(0).getPass())) {
                System.out.println("Aceptado");
                switch (lista.get(0).getTipo()) {
                    case "Empresa":
                        System.out.println("Empresa");
                        return "empresa/empresaIndex";
                    case "Administrador":
                        System.out.println("Admin");
                        return "admin/adminIndex";
                    case "Candidato":
                        System.out.println("Candidato");
                        return "candidato/candidatoIndex";
                    default:
                        return "loginError";
                }
            } else {
                System.out.println("Denegado");
                return "loginError";
            }
        }
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
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public boolean equals(String uno,String dos) {
        boolean iguales = true;
        
        for(int i = 0;i < uno.length();i++) {
            char character = uno.charAt(i);
            
            if(character != dos.charAt(i)) {
                iguales = false;
            }
        }
        
        return iguales;
        
    }

}
