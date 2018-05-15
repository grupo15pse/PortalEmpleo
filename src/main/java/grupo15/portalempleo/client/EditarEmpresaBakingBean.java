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
@Named(value = "editarEmpresaBakingBean")
@SessionScoped
public class EditarEmpresaBakingBean implements Serializable {

    private String nombre;
    private int empresaEditar;
    /**
     * Creates a new instance of EditarEmpresaBakingBean
     */
    public EditarEmpresaBakingBean() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEmpresaEditar() {
        return empresaEditar;
    }

    public void setEmpresaEditar(int empresaEditar) {
        this.empresaEditar = empresaEditar;
    }
    
}
