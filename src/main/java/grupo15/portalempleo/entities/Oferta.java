/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo15.portalempleo.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "oferta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT o FROM Oferta o")
    , @NamedQuery(name = "Oferta.findAllNombreA", query = "SELECT o FROM Oferta o ORDER BY o.nombre ASC")
    , @NamedQuery(name = "Oferta.findAllNombreD", query = "SELECT o FROM Oferta o ORDER BY o.nombre DESC")
    , @NamedQuery(name = "Oferta.findAllEmpresaA", query = "SELECT o FROM Oferta o ORDER BY o.nombre ASC")
    , @NamedQuery(name = "Oferta.findAllEmpresaD", query = "SELECT o FROM Oferta o ORDER BY o.empresa DESC")
    , @NamedQuery(name = "Oferta.findAllReqA", query = "SELECT o FROM Oferta o ORDER BY o.reqMinimos ASC")
    , @NamedQuery(name = "Oferta.findAllReqD", query = "SELECT o FROM Oferta o ORDER BY o.reqMinimos DESC")
    , @NamedQuery(name = "Oferta.findAllFechaA", query = "SELECT o FROM Oferta o ORDER BY o.fechaIncorp ASC")
    , @NamedQuery(name = "Oferta.findAllFechaD", query = "SELECT o FROM Oferta o ORDER BY o.fechaIncorp DESC")
    , @NamedQuery(name = "Oferta.findAllPuestoA", query = "SELECT o FROM Oferta o ORDER BY o.puestoTrabajo ASC")
    , @NamedQuery(name = "Oferta.findAllPuestoD", query = "SELECT o FROM Oferta o ORDER BY o.puestoTrabajo DESC")
    , @NamedQuery(name = "Oferta.findByOfertaId", query = "SELECT o FROM Oferta o WHERE o.ofertaId = :ofertaId")
    , @NamedQuery(name = "Oferta.findByNombre", query = "SELECT o FROM Oferta o WHERE o.nombre = :nombre")
    , @NamedQuery(name = "Oferta.findByDescripcion", query = "SELECT o FROM Oferta o WHERE o.descripcion = :descripcion")
    , @NamedQuery(name = "Oferta.findByFechaIncorp", query = "SELECT o FROM Oferta o WHERE o.fechaIncorp = :fechaIncorp")
    , @NamedQuery(name = "Oferta.findByReqMinimos", query = "SELECT o FROM Oferta o WHERE o.reqMinimos = :reqMinimos")
    , @NamedQuery(name = "Oferta.findByPuestoTrabajo", query = "SELECT o FROM Oferta o WHERE o.puestoTrabajo = :puestoTrabajo")
    , @NamedQuery(name = "Oferta.findByEmpresa", query = "SELECT o FROM Oferta o WHERE o.empresa = :empresa")})
public class Oferta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OfertaId")
    private Integer ofertaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FechaIncorp")
    @Temporal(TemporalType.DATE)
    private Date fechaIncorp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ReqMinimos")
    private String reqMinimos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "PuestoTrabajo")
    private String puestoTrabajo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Empresa")
    private int empresa;

    public Oferta() {
    }

    public Oferta(Integer ofertaId) {
        this.ofertaId = ofertaId;
    }

    public Oferta(Integer ofertaId, String nombre, String descripcion, Date fechaIncorp, String reqMinimos, String puestoTrabajo, int empresa) {
        this.ofertaId = ofertaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaIncorp = fechaIncorp;
        this.reqMinimos = reqMinimos;
        this.puestoTrabajo = puestoTrabajo;
        this.empresa = empresa;
    }

    public Integer getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Integer ofertaId) {
        this.ofertaId = ofertaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaIncorp() {
        return fechaIncorp;
    }

    public void setFechaIncorp(Date fechaIncorp) {
        this.fechaIncorp = fechaIncorp;
    }

    public String getReqMinimos() {
        return reqMinimos;
    }

    public void setReqMinimos(String reqMinimos) {
        this.reqMinimos = reqMinimos;
    }

    public String getPuestoTrabajo() {
        return puestoTrabajo;
    }

    public void setPuestoTrabajo(String puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ofertaId != null ? ofertaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.ofertaId == null && other.ofertaId != null) || (this.ofertaId != null && !this.ofertaId.equals(other.ofertaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "grupo15.portalempleo.entities.Oferta[ ofertaId=" + ofertaId + " ]";
    }

}
