/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "formacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formacion.findAll", query = "SELECT f FROM Formacion f")
    , @NamedQuery(name = "Formacion.findByCandidato", query = "SELECT f FROM Formacion f WHERE f.formacionPK.candidato = :candidato")
    , @NamedQuery(name = "Formacion.findByFormacion", query = "SELECT f FROM Formacion f WHERE f.formacionPK.formacion = :formacion")})
public class Formacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FormacionPK formacionPK;

    public Formacion() {
    }

    public Formacion(FormacionPK formacionPK) {
        this.formacionPK = formacionPK;
    }

    public Formacion(int candidato, String formacion) {
        this.formacionPK = new FormacionPK(candidato, formacion);
    }

    public FormacionPK getFormacionPK() {
        return formacionPK;
    }

    public void setFormacionPK(FormacionPK formacionPK) {
        this.formacionPK = formacionPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formacionPK != null ? formacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formacion)) {
            return false;
        }
        Formacion other = (Formacion) object;
        if ((this.formacionPK == null && other.formacionPK != null) || (this.formacionPK != null && !this.formacionPK.equals(other.formacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "grupo15.portalempleo.entities.Formacion[ formacionPK=" + formacionPK + " ]";
    }
    
}
