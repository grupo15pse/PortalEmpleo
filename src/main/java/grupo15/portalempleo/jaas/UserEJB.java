/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.jaas;

import grupo15.portalempleo.entities.Formacion;
import grupo15.portalempleo.entities.Grupo;
import grupo15.portalempleo.entities.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Admin
 */
@Stateless
public class UserEJB {

    @PersistenceContext
    private EntityManager em;

    public Usuario createUser(Usuario user) {
        
        try {
            user.setPass(AuthenticationUtils.encodeSHA256(user.getPass()));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        Grupo group = new Grupo();
        group.setEmail(user.getEmail());
        group.setNombreGrupo("candidato");
        em.persist(user);
        em.persist(group);
        return user;
    }
    
    public void updateUsuario(Usuario usuario) {
        em.merge(usuario);
    }
    public void updateGrupo(Grupo grupo) {
        em.merge(grupo);
    }

    public Usuario findByEmail(String email) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByEmail", Usuario.class);
        query.setParameter("email", email);
        Usuario user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }
    
    public Usuario findById(int userId) {
        TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findByUsuarioId", Usuario.class);
        query.setParameter("usuarioId", userId);
        Usuario user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }
    
    public String getFormacionByUsuario(int userId) {
        TypedQuery<Formacion> query = em.createNamedQuery("Formacion.findByCandidato", Formacion.class);
        
        query.setParameter("candidato", userId);
        
        Formacion formacion = query.getSingleResult();
        return formacion.getFormacionPK().getFormacion();
    }
}
