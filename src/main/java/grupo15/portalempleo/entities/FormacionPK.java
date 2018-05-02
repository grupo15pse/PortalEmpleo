/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Admin
 */
@Embeddable
public class FormacionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Candidato")
    private int candidato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "Formacion")
    private String formacion;

    public FormacionPK() {
    }

    public FormacionPK(int candidato, String formacion) {
        this.candidato = candidato;
        this.formacion = formacion;
    }

    public int getCandidato() {
        return candidato;
    }

    public void setCandidato(int candidato) {
        this.candidato = candidato;
    }

    public String getFormacion() {
        return formacion;
    }

    public void setFormacion(String formacion) {
        this.formacion = formacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) candidato;
        hash += (formacion != null ? formacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormacionPK)) {
            return false;
        }
        FormacionPK other = (FormacionPK) object;
        if (this.candidato != other.candidato) {
            return false;
        }
        if ((this.formacion == null && other.formacion != null) || (this.formacion != null && !this.formacion.equals(other.formacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "grupo15.portalempleo.entities.FormacionPK[ candidato=" + candidato + ", formacion=" + formacion + " ]";
    }
    
}
