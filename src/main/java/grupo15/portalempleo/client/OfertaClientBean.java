/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Oferta;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.jaas.UserEJB;
import grupo15.portalempleo.json.EmpresaWriter;
import grupo15.portalempleo.json.OfertaReader;
import grupo15.portalempleo.json.OfertaWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    @Inject
    private EditarOfertaBackingBean eobb;
    
    @Inject
    private UserEJB userEJB;
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
    
    public String addOferta(int empresaId) {
        
        Oferta oferta = new Oferta();
        oferta.setDescripcion(obb.getDescripcion());
        oferta.setEmpresa(empresaId);
        oferta.setFechaIncorp(new Date());
        oferta.setPuestoTrabajo(obb.getPuestoTrabajo());
        oferta.setReqMinimos(obb.getReqMinimos());
        oferta.setNombre(obb.getNombre());
        
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/oferta");
        target.register(OfertaWriter.class).request().post(Entity.entity(oferta, MediaType.APPLICATION_JSON));

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Éxito", "La oferta " + oferta.getNombre() + " ha sido enviada."));
        
        return "empresaIndex";
    }
    
    public List<Oferta> getOfertas() {
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/oferta");
        
        Oferta[] array = target.register(OfertaReader.class)
                .request(MediaType.APPLICATION_JSON)
                .get(Oferta[].class);   
        
        ArrayList<Oferta> arrayList = new ArrayList<>();
        
        for(Oferta ofer: array) {
            if(userEJB.findById(ofer.getEmpresa()) != null) {
                arrayList.add(ofer);
            }
        }
        
        return arrayList;
    }
    
    public List<Oferta> getOfertasOrderByNombre(String direccion) {
        return userEJB.getOfertasOrderByNombre(direccion);
    }
    
    public List<Oferta> getOfertasOrderByEmpresa(String direccion) {
        return userEJB.getOfertasOrderByEmpresa(direccion);
    }
    
    public List<Oferta> getOfertasOrderByPuesto(String direccion) {
        return userEJB.getOfertasOrderByPuesto(direccion);
    }
    
    public List<Oferta> getOfertasOrderByReq(String direccion) {
        return userEJB.getOfertasOrderByReq(direccion);
    }
    
    public List<Oferta> getOfertasOrderByFecha(String direccion) {
        return userEJB.getOfertasOrderByFecha(direccion);
    }
    
    public String deleteOferta(Oferta oferta) {
        String nombreOferta = oferta.getNombre();

        target = client.target("http://localhost:8080/PortalEmpleo/webresources/oferta");
        target.path("{id}")
                .resolveTemplate("id", oferta.getOfertaId())
                .request()
                .delete();
        
        userEJB.borrarInscritos(oferta.getOfertaId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Éxito", "La oferta " + nombreOferta + " ha sido eliminada."));
        
        return "ofertasPropias";
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
    
    public List<Usuario> getUsuariosByOferta() {
        return userEJB.findUsuariosByOferta(obb.getVerCandidatos());
    }
    
    public String updateOferta() {
        Oferta oferta = userEJB.findOferta(eobb.getOfertaEditar());
        
        if(eobb.getDescripcion() != null) {
            oferta.setDescripcion(eobb.getDescripcion());
        }
        
        if(eobb.getNombre() != null) {
            oferta.setNombre(eobb.getNombre());
        }
        
        if(eobb.getPuestoTrabajo() != null) {
            oferta.setPuestoTrabajo(eobb.getPuestoTrabajo());
        }
        
        if(eobb.getReqMinimos() != null) {
            oferta.setReqMinimos(eobb.getReqMinimos());
        }

        userEJB.updateOferta(oferta);
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Éxito",  "La oferta " + oferta.getNombre() + " ha sido actualizada."));
        
        return "ofertasPropias";
    }
    
    public int getNumeroCandidatos(Oferta oferta) {
        return userEJB.getNumeroCandidatos(oferta);
    }
}
