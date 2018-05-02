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
@Table(name = "presentar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presentar.findAll", query = "SELECT p FROM Presentar p")
    , @NamedQuery(name = "Presentar.findByCandidato", query = "SELECT p FROM Presentar p WHERE p.presentarPK.candidato = :candidato")
    , @NamedQuery(name = "Presentar.findByOferta", query = "SELECT p FROM Presentar p WHERE p.presentarPK.oferta = :oferta")})
public class Presentar implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresentarPK presentarPK;

    public Presentar() {
    }

    public Presentar(PresentarPK presentarPK) {
        this.presentarPK = presentarPK;
    }

    public Presentar(int candidato, int oferta) {
        this.presentarPK = new PresentarPK(candidato, oferta);
    }

    public PresentarPK getPresentarPK() {
        return presentarPK;
    }

    public void setPresentarPK(PresentarPK presentarPK) {
        this.presentarPK = presentarPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (presentarPK != null ? presentarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Presentar)) {
            return false;
        }
        Presentar other = (Presentar) object;
        if ((this.presentarPK == null && other.presentarPK != null) || (this.presentarPK != null && !this.presentarPK.equals(other.presentarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "grupo15.portalempleo.entities.Presentar[ presentarPK=" + presentarPK + " ]";
    }
    
}
