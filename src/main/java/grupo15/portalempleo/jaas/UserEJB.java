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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.PathParam;

/**
 *
 * @author Admin
 */
@Named
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
        Query query = em.createQuery("DELETE FROM Usuario u WHERE u.usuarioId=:usuarioId");
        query.setParameter("usuarioId", user.getUsuarioId());
        query.executeUpdate();

        query = em.createQuery("DELETE FROM Grupo g WHERE g.email=:email");
        query.setParameter("email", user.getEmail());
        query.executeUpdate();
        
        query = em.createQuery("DELETE FROM Formacion f WHERE f.formacionPK.candidato=:usuarioId");
        query.setParameter("usuarioId", user.getUsuarioId());
        query.executeUpdate();
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

        Formacion formacion = null;
        
        String resultado = null;
        
        try {
            formacion = query.getSingleResult();
            resultado = formacion.getFormacionPK().getFormacion();
        }catch (Exception e) {
            
        }
        return resultado;
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

        Oferta resul = null;

        try {
            resul = query.getSingleResult();
        } catch (Exception e) {
            System.out.println("ERRROR en BUSQUEDA");
        }

        return resul;
    }

    public List<Oferta> findOfertasByBusqueda(String busqueda) {
        TypedQuery<Oferta> query = em.createNamedQuery("Oferta.findAll", Oferta.class);

        List<Oferta> listaCompleta = query.getResultList();

        ArrayList<Oferta> listaFiltrada = new ArrayList<>();

        for (Oferta ofer : listaCompleta) {
            if (findById(ofer.getEmpresa()) == null) {
                continue;
            }

            boolean valida = false;

            if (ofer.getDescripcion().contains(busqueda)) {
                valida = true;
            }

            if (ofer.getNombre().contains(busqueda)) {
                valida = true;
            }

            if (ofer.getPuestoTrabajo().contains(busqueda)) {
                valida = true;
            }

            if (ofer.getReqMinimos().contains(busqueda)) {
                valida = true;
            }

            Usuario empresa = findById(ofer.getEmpresa());
            String nombreEmpresa = empresa.getNombre();

            if (nombreEmpresa.contains(busqueda)) {
                valida = true;
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formatter.format(ofer.getFechaIncorp());

            if (fecha.contains(busqueda)) {
                valida = true;
            }

            if (valida) {
                listaFiltrada.add(ofer);
            }
        }

        return listaFiltrada;
    }
    
    public List<Oferta> getOfertasOrderByNombre(String direccion) {
        if(direccion.equals("asc")) {
            return em.createNamedQuery("Oferta.findAllNombreA", Oferta.class).getResultList();
        }else {
            return em.createNamedQuery("Oferta.findAllNombreD", Oferta.class).getResultList();
        }
    }
    
    public List<Oferta> getOfertasOrderByEmpresa(String direccion) {
        if(direccion.equals("asc")) {
            return em.createNamedQuery("Oferta.findAllEmpresaA", Oferta.class).getResultList();
        }else {
            return em.createNamedQuery("Oferta.findAllEmpresaD", Oferta.class).getResultList();
        }
    }
    
    public List<Oferta> getOfertasOrderByPuesto(String direccion) {
        if(direccion.equals("asc")) {
            return em.createNamedQuery("Oferta.findAllPuestoA", Oferta.class).getResultList();
        }else {
            return em.createNamedQuery("Oferta.findAllPuestoD", Oferta.class).getResultList();
        }
    }
    
    public List<Oferta> getOfertasOrderByReq(String direccion) {
        if(direccion.equals("asc")) {
            return em.createNamedQuery("Oferta.findAllReqA", Oferta.class).getResultList();
        }else {
            return em.createNamedQuery("Oferta.findAllReqD", Oferta.class).getResultList();
        }
    }
    
    public List<Oferta> getOfertasOrderByFecha(String direccion) {
        if(direccion.equals("asc")) {
            return em.createNamedQuery("Oferta.findAllFechaA", Oferta.class).getResultList();
        }else {
            return em.createNamedQuery("Oferta.findAllFechaD", Oferta.class).getResultList();
        }
    }
    
    public List<Oferta> findOfertasByCandidato(Usuario user)  {
        ArrayList<Oferta> resultado = new ArrayList<>();
        Query presentarQuery = em.createNamedQuery("Presentar.findByCandidato", Presentar.class);
        presentarQuery.setParameter("candidato", user.getUsuarioId());
        List<Presentar> lista = presentarQuery.getResultList();
   
        for(Presentar pres: lista) {
            System.out.println("PFERTA ID:" + pres.getPresentarPK().getOferta());
            
            Query ofertasQuery = em.createNamedQuery("Oferta.findByOfertaId", Oferta.class);
            ofertasQuery.setParameter("ofertaId", pres.getPresentarPK().getOferta());
            resultado.add((Oferta) ofertasQuery.getSingleResult());
        }

        return resultado;
    }
    
    public int getNumeroCandidatos(Oferta oferta) {
        TypedQuery<Presentar> query = em.createNamedQuery("Presentar.findByOferta",Presentar.class);
        
        query.setParameter("oferta", oferta.getOfertaId());
        
        List<Presentar> lista = query.getResultList();
        
        return lista.size();
    }
    
    public String getNombreEmpresa(int empresaId) {
        Usuario empresa = findById(empresaId);
        return empresa.getNombre();
    }
}
