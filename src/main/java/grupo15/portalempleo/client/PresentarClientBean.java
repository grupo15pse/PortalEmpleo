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
import grupo15.portalempleo.jaas.LoginView;
import grupo15.portalempleo.jaas.UserEJB;
import grupo15.portalempleo.json.OfertaReader;
import grupo15.portalempleo.rest.FormacionFacadeREST;
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
@Named(value = "presentarClientBean")
@RequestScoped
public class PresentarClientBean {

    Client client;
    WebTarget target;

    @Inject
    private UserEJB userEJB;

    @Inject
    private EstadoPagoClientBean epcb;

    /**
     * Creates a new instance of PresentarClientBean
     */
    public PresentarClientBean() {
    }

    public void inscribirse(Oferta oferta, Usuario usuario) {
        String formacionCandidato = userEJB.getFormacionByUsuario(usuario.getUsuarioId());

        switch (compareTo(formacionCandidato, oferta.getReqMinimos())) {
            case 1: {

                if (epcb.comprobarPago(usuario)) {
                    Presentar presentar = new Presentar();
                    presentar.setPresentarPK(new PresentarPK(usuario.getUsuarioId(), oferta.getOfertaId()));
                    target.request().post(Entity.entity(presentar, MediaType.APPLICATION_JSON));

                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Éxito", "Te has inscrito con éxito en la oferta " + oferta.getNombre()));
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Error", "No tienes fondos suficientes para inscribirte a la oferta " + oferta.getNombre()));
                }
                break;
            }
            case -1: {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", "No dispones de la formación necesaria para inscribirte a la oferta " + oferta.getNombre()));
                break;
            }
            default: {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", "ERROOOOR"));
                break;
            }
        }
    }

    public List<Oferta> getOfertasInscrito(Usuario user) {
        return userEJB.findOfertasByCandidato(user);

    }
    
    public void borrarInscripciones(Usuario user, Oferta oferta) {
        userEJB.borrarInscrito(user.getUsuarioId(),oferta.getOfertaId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Error", "Ya no estás inscrito en la oferta " + oferta.getNombre()));
    } 

    public boolean isInscrito(Usuario user, Oferta oferta) {
        List<Oferta> array = getOfertasInscrito(user);
        boolean isInscrito = false;

        for (Oferta ofert : array) {
            if (oferta.equals(ofert)) {
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

    public int compareTo(String formacionUsuario, String formacionMinima) {

        switch (formacionMinima) {
            case "Ninguna":
                return 1;
            case "ESO":
                if (formacionUsuario.equalsIgnoreCase("Ninguna")) {
                    return -1;
                } else {
                    return 1;
                }
            case "Bachiller":
                if (formacionUsuario.equalsIgnoreCase("Ninguna") || formacionUsuario.equalsIgnoreCase("ESO")) {
                    return -1;
                } else {
                    return 1;
                }
            case "Medio":
                if (formacionUsuario.equalsIgnoreCase("Ninguna") || formacionUsuario.equalsIgnoreCase("ESO") || formacionUsuario.equalsIgnoreCase("Bachiller")) {
                    return -1;
                } else {
                    return 1;
                }
            case "Superior":
                if (formacionUsuario.equalsIgnoreCase("Ninguna") || formacionUsuario.equalsIgnoreCase("ESO") || formacionUsuario.equalsIgnoreCase("Bachiller") || formacionUsuario.equalsIgnoreCase("Medio")) {
                    return -1;
                } else {
                    return 1;
                }
            case "Carrera":
                if (formacionUsuario.equalsIgnoreCase("Ninguna") || formacionUsuario.equalsIgnoreCase("ESO") || formacionUsuario.equalsIgnoreCase("Bachiller") || formacionUsuario.equalsIgnoreCase("Medio") || formacionUsuario.equalsIgnoreCase("Superior")) {
                    return -1;
                } else {
                    return 1;
                }
            default:
                break;
        }

        return 0;
    }
}
