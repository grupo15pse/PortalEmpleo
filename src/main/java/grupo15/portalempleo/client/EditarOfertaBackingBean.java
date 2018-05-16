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
@Named(value = "editarOfertaBackingBean")
@SessionScoped
public class EditarOfertaBackingBean implements Serializable {

    private String descripcion,nombre,puestoTrabajo,reqMinimos;
    private int ofertaEditar;
    /**
     * Creates a new instance of EditarOfertaBackingBean
     */
    public EditarOfertaBackingBean() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuestoTrabajo() {
        return puestoTrabajo;
    }

    public void setPuestoTrabajo(String puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }

    public String getReqMinimos() {
        return reqMinimos;
    }

    public void setReqMinimos(String reqMinimos) {
        this.reqMinimos = reqMinimos;
    }

    public int getOfertaEditar() {
        return ofertaEditar;
    }

    public void setOfertaEditar(int ofertaEditar) {
        this.ofertaEditar = ofertaEditar;
    }
    
}
