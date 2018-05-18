/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
@Named(value = "buscadorBackingBean")
@SessionScoped
public class BuscadorBackingBean implements Serializable {
    
    private String busqueda;

    /**
     * Creates a new instance of BuscadorBackingBean
     */
    public BuscadorBackingBean() {
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    
}
