/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Oferta;
import grupo15.portalempleo.jaas.UserEJB;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Admin
 */
@Named(value = "buscadorClientBean")
@RequestScoped
public class BuscadorClientBean {
    
    @Inject
    private UserEJB userEJB;
    
    @Inject
    private BuscadorBackingBean bbb;

    /**
     * Creates a new instance of BuscadorClientBean
     */
    public BuscadorClientBean() {
    }
    
    public List<Oferta> buscarOfertas () {
        return userEJB.findOfertasByBusqueda(bbb.getBusqueda());
    }
    
}
