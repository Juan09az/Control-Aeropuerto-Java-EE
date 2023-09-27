/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author camil
 */
@Entity
@Table(name = "PASAJERO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasajero.findAll", query = "SELECT p FROM Pasajero p")
    , @NamedQuery(name = "Pasajero.findByIdPasajero", query = "SELECT p FROM Pasajero p WHERE p.idPasajero = :idPasajero")
    , @NamedQuery(name = "Pasajero.findByMillasReco", query = "SELECT p FROM Pasajero p WHERE p.millasReco = :millasReco")})
public class Pasajero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PASAJERO")
    private String idPasajero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "MILLAS_RECO")
    private Double millasReco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasajero")
    private List<Reserva> reservaList;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne
    private Persona idPersona;
    @JoinColumn(name = "USUARIO_PAS", referencedColumnName = "USUARIO")
    @ManyToOne
    private Usuario usuarioPas;

    public Pasajero() {
    }

    public Pasajero(String idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(String idPasajero) {
        this.idPasajero = idPasajero;
    }

    public Double getMillasReco() {
        return millasReco;
    }

    public void setMillasReco(Double millasReco) {
        this.millasReco = millasReco;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Usuario getUsuarioPas() {
        return usuarioPas;
    }

    public void setUsuarioPas(Usuario usuarioPas) {
        this.usuarioPas = usuarioPas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPasajero != null ? idPasajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasajero)) {
            return false;
        }
        Pasajero other = (Pasajero) object;
        if ((this.idPasajero == null && other.idPasajero != null) || (this.idPasajero != null && !this.idPasajero.equals(other.idPasajero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pasajero[ idPasajero=" + idPasajero + " ]";
    }
    
}
