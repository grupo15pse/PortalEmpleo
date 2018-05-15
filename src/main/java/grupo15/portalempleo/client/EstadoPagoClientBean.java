/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.EntidadPago;
import grupo15.portalempleo.entities.Usuario;
import grupo15.portalempleo.json.EntidadPagoReader;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

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
        System.out.println(user.getTarjeta().longValue());
        
        if (user.getTarjeta().longValue() % 2 == 0) {
            target = client.target("http://valdavia.infor.uva.es:8080/pagos/webresources/usuarios/grupo15si@uva.es");
        } else {
            target = client.target("http://valdavia.infor.uva.es:8080/pagos/webresources/usuarios/grupo15no@uva.es");
        }

        EntidadPago entidadPago = target.register(EntidadPagoReader.class).request().get(EntidadPago.class);
        return entidadPago.getEstadoPago().equals("si");
    }

}
