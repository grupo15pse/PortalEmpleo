/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.EntidadPago;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.json.EntidadPagoReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Admin
 */
@Named(value = "estadoPagoClientBean")
@RequestScoped
public class EstadoPagoClientBean {

    Client client;
    WebTarget target;

    /**
     * Creates a new instance of EstadoPagoClientBean
     */
    public EstadoPagoClientBean() {
    }

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();

    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public boolean comprobarPago(Usuario user) {

        Pattern p = Pattern.compile("grupo[12][1-9][sn][io]@uva.es");
        Matcher m = p.matcher(user.getEmail());
        boolean b = m.matches();

        if (b) {
            EntidadPago entidadPago = null;
            try {
                target = client.target("http://valdavia.infor.uva.es:8080/pagos/webresources/usuarios");
                entidadPago = target.register(EntidadPagoReader.class)
                        .path("{email}")
                        .resolveTemplate("email", user.getEmail())
                        .request(MediaType.APPLICATION_JSON)
                        .get(EntidadPago.class);

                if (entidadPago.getEstadoPago().equals("si")) {
                    return true;
                } else {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Error", "No tienes fondos suficientes para inscribirte a la oferta."));
                    return false;
                }
            } catch (Exception e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Error", "Pruebe con un correo cuyo número de grupo no esté entre 26 y 29, ambos incluidos."));
                return false;
            }

        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "No estás autorizado para inscribirte en ofertas."));
            return false;
        }
    }

}
