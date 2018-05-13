/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Oferta;
import grupo15.portalempleo.entities.Presentar;
import grupo15.portalempleo.entities.PresentarPK;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.json.OfertaReader;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Admin
 */
@Named(value = "presentarClientBean")
@RequestScoped
public class PresentarClientBean {
    
    Client client;
    WebTarget target;
    
    int sizeOfertasInscrito = 0;

    /**
     * Creates a new instance of PresentarClientBean
     */
    public PresentarClientBean() {
    }
    
    public void inscribirse(Oferta oferta,Usuario usuario) {
        Presentar presentar = new Presentar();
        presentar.setPresentarPK(new PresentarPK(usuario.getUsuarioId(), oferta.getOfertaId()));
        target.request().post(Entity.entity(presentar, MediaType.APPLICATION_JSON));
    }
    
    public Oferta[] getOfertasInscrito(Usuario user) {
        Oferta[] array = target
                .register(OfertaReader.class)
                .path("findOfertasByCandidato/{id}")
                .resolveTemplate("id", user.getUsuarioId())
                .request(MediaType.APPLICATION_JSON)
                .get(Oferta[].class);
        
        sizeOfertasInscrito = array.length;
        
        return array;
    }
    
    public boolean isInscrito(Usuario user,Oferta oferta) {
        Oferta[] array = getOfertasInscrito(user);
        boolean isInscrito = false;
        
        for(Oferta ofert: array) {
            if(oferta.equals(ofert)) {
                System.out.println("Inscrito");
                isInscrito = true;
            }
        }
        
        return isInscrito;
    }
    
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/PortalEmpleo/webresources/presentar");        
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public int getSizeOfertasInscrito() {
        return sizeOfertasInscrito;
    }

    public void setSizeOfertasInscrito(int sizeOfertasInscrito) {
        this.sizeOfertasInscrito = sizeOfertasInscrito;
    }
    
}
