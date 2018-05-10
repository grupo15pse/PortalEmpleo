/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import grupo15.portalempleo.entities.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Admin
 */
@Named(value = "ofertaBackingBean")
@SessionScoped
public class OfertaBakingBean implements Serializable{
    private String descripcion,nombre,puestoTrabajo,reqMinimos;
    private Date fechaIncorp;
    private Usuario empresa;

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

    public Date getFechaIncorp() {
        return fechaIncorp;
    }

    public void setFechaIncorp(Date fechaIncorp) {
        this.fechaIncorp = fechaIncorp;
    }

    public Usuario getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Usuario empresa) {
        this.empresa = empresa;
    }
    
    
}
