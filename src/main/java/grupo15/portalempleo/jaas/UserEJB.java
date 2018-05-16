/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.jaas;

import grupo15.portalempleo.entities.Formacion;
import grupo15.portalempleo.entities.Grupo;
import grupo15.portalempleo.entities.Oferta;
import grupo15.portalempleo.entities.Presentar;
import grupo15.portalempleo.entities.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    
    public void updateOferta(Oferta oferta) {
        em.merge(oferta);
    }

    public void deleteUsuario(Usuario user) {
        Query query = em.createQuery("DELETE FROM Usuario u WHERE u.usuarioId= :usuarioId");
        query.setParameter("usuarioId", user.getUsuarioId());
        query.executeUpdate();
        
        query = em.createQuery("DELETE FROM Grupo g WHERE g.email= :email");
        query.setParameter("email", user.getEmail());
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

    public List<Usuario> findUsuariosByOferta(int ofertaId) {
        TypedQuery<Presentar> query = em.createNamedQuery("Presentar.findByOferta", Presentar.class);

        query.setParameter("oferta", ofertaId);

        List<Presentar> aux = query.getResultList();
        List<Usuario> resul = new ArrayList<>();

        for (Presentar presen : aux) {
            TypedQuery<Usuario> query2 = em.createNamedQuery("Usuario.findByUsuarioId", Usuario.class);

            query2.setParameter("usuarioId", presen.getPresentarPK().getCandidato());

            resul.add(query2.getSingleResult());
        }

        return resul;
    }

    public void borrarInscritos(Integer ofertaId) {
        TypedQuery<Presentar> query = em.createNamedQuery("Presentar.findByOferta", Presentar.class);
        query.setParameter("oferta", ofertaId);

        for (Presentar presentar : query.getResultList()) {
            em.remove(presentar);
        }
    }

    public void borrarInscrito(int usuarioId, int ofertaId) {
        TypedQuery<Presentar> query = em.createNamedQuery("Presentar.findAll", Presentar.class);

        for (Presentar pres : query.getResultList()) {
            if (pres.getPresentarPK().getCandidato() == usuarioId && pres.getPresentarPK().getOferta() == ofertaId) {
                em.remove(pres);
            }
        }
    }
    
    public Oferta findOferta(int ofertaId) {
        TypedQuery<Oferta> query = em.createNamedQuery("Oferta.findByOfertaId", Oferta.class);
        query.setParameter("ofertaId", ofertaId);
        
        return query.getSingleResult();
    }
}
