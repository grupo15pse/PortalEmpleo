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

/**
 *
 * @author Admin
 */
@Embeddable
public class PresentarPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Candidato")
    private int candidato;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Oferta")
    private int oferta;

    public PresentarPK() {
    }

    public PresentarPK(int candidato, int oferta) {
        this.candidato = candidato;
        this.oferta = oferta;
    }

    public int getCandidato() {
        return candidato;
    }

    public void setCandidato(int candidato) {
        this.candidato = candidato;
    }

    public int getOferta() {
        return oferta;
    }

    public void setOferta(int oferta) {
        this.oferta = oferta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) candidato;
        hash += (int) oferta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresentarPK)) {
            return false;
        }
        PresentarPK other = (PresentarPK) object;
        if (this.candidato != other.candidato) {
            return false;
        }
        if (this.oferta != other.oferta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "grupo15.portalempleo.entities.PresentarPK[ candidato=" + candidato + ", oferta=" + oferta + " ]";
    }
    
}
