/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Oferta;
import grupo15.portalempleo.json.EmpresaWriter;
import grupo15.portalempleo.json.OfertaReader;
import grupo15.portalempleo.json.OfertaWriter;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Admin
 */
@Named(value = "ofertaClientBean")
@RequestScoped
public class OfertaClientBean {
    
    Client client;
    WebTarget target;

    @Inject
    private OfertaBackingBean obb;
    /**
     * Creates a new instance of OfertaClientBean
     */
    public OfertaClientBean() {
    }
    
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }
    
    public void addOferta(int empresaId) {
        
        Oferta oferta = new Oferta();
        oferta.setDescripcion(obb.getDescripcion());
        oferta.setEmpresa(empresaId);
        oferta.setFechaIncorp(new Date());
        oferta.setPuestoTrabajo(obb.getPuestoTrabajo());
        oferta.setReqMinimos(obb.getReqMinimos());
        oferta.setNombre(obb.getNombre());
        
        System.out.println("FECHAAA: " + oferta.getFechaIncorp().toString());
        
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/oferta");
        target.register(OfertaWriter.class).request().post(Entity.entity(oferta, MediaType.APPLICATION_JSON));

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Éxito", "La oferta " + oferta.getNombre() + " ha sido enviada."));
    }
    
    public Oferta[] getOfertas() {
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/oferta");
        
        Oferta[] array = target.register(OfertaReader.class)
                .request(MediaType.APPLICATION_JSON)
                .get(Oferta[].class);        
        return array;
    }
    
    public Oferta[] getOfertasPropias(int empresaId) {
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/usuario");
        
        Oferta[] array = target.register(OfertaReader.class)
                .path("findOfertasByEmpresa/{empresaId}")
                .resolveTemplate("empresaId", empresaId)
                .request(MediaType.APPLICATION_JSON)
                .get(Oferta[].class);        
        return array;
    }
}
