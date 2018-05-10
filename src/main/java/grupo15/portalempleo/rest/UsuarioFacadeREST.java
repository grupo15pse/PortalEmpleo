/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.rest;

import grupo15.portalempleo.entities.Oferta;
import grupo15.portalempleo.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Admin
 */
@Named
@Stateless
@Path("usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "grupo15_PortalEmpleo_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
        System.out.println("CREATE " + entity.getTelefono());
        System.out.println("CREATE " + entity.getTarjeta());
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findEmpresas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> findEmpresas() {
        Query q = em.createNativeQuery("SELECT * FROM Usuario WHERE tipo='empresa'", Usuario.class);
        
        return q.getResultList();
    }
    
    @GET
    @Path("findOfertasByEmpresa/{empresaId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Oferta> findOfertasByEmpresa(@PathParam ("empresaId") int empresa) {
        Query q = em.createNamedQuery("Oferta.findByEmpresa");
        
        q.setParameter("empresa", empresa);
        
        return q.getResultList();
    }
    
    @GET
    @Path("findOfertasSize")
    @Produces(MediaType.TEXT_PLAIN)
    public int findOfertasSize(@PathParam ("empresaId") int empresa) {
        Query q = em.createNamedQuery("Oferta.findByEmpresa");
        
        q.setParameter("empresa", empresa);
        
        return q.getResultList().size();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
