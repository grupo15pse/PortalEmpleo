/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.rest;

import grupo15.portalempleo.entities.Oferta;
import grupo15.portalempleo.entities.Presentar;
import grupo15.portalempleo.entities.PresentarPK;
import java.util.ArrayList;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Admin
 */
@Named
@Stateless
@Path("presentar")
public class PresentarFacadeREST extends AbstractFacade<Presentar> {

    @PersistenceContext(unitName = "grupo15_PortalEmpleo_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private PresentarPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;candidato=candidatoValue;oferta=ofertaValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        grupo15.portalempleo.entities.PresentarPK key = new grupo15.portalempleo.entities.PresentarPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> candidato = map.get("candidato");
        if (candidato != null && !candidato.isEmpty()) {
            key.setCandidato(new java.lang.Integer(candidato.get(0)));
        }
        java.util.List<String> oferta = map.get("oferta");
        if (oferta != null && !oferta.isEmpty()) {
            key.setOferta(new java.lang.Integer(oferta.get(0)));
        }
        return key;
    }

    public PresentarFacadeREST() {
        super(Presentar.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Presentar entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Presentar entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        grupo15.portalempleo.entities.PresentarPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Presentar find(@PathParam("id") PathSegment id) {
        grupo15.portalempleo.entities.PresentarPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Presentar> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Presentar> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /*@GET
    @Path("findOfertasByCandidato/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Oferta> findOfertasByCandidato(@PathParam("id") int id) {
        ArrayList<Oferta> resultado = new ArrayList<>();
        Query presentarQuery = em.createNamedQuery("Presentar.findByCandidato", Presentar.class);
        presentarQuery.setParameter("candidato", id);
        List<Presentar> lista = presentarQuery.getResultList();
   
        for(Presentar pres: lista) {
            System.out.println("PFERTA ID:" + pres.getPresentarPK().getOferta());
            
            Query ofertasQuery = em.createNamedQuery("Oferta.findByOfertaId", Oferta.class);
            ofertasQuery.setParameter("ofertaId", pres.getPresentarPK().getOferta());
            resultado.add((Oferta) ofertasQuery.getSingleResult());
        }

        return resultado;
    }*/

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
