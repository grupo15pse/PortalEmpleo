/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.client;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Admin
 */
@Named(value = "editarPerfilBakingBean")
@SessionScoped
public class EditarPerfilBakingBean implements Serializable {
    
    private String nombre,correo;
    private Date fecha;
    private BigInteger tarjeta;
    private String tel;

    /**
     * Creates a new instance of EditarPerfilBakingBean
     */
    public EditarPerfilBakingBean() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigInteger getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(BigInteger tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
}
