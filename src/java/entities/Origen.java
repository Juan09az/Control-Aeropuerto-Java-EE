/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camil
 */
@Entity
@Table(name = "ORIGEN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Origen.findAll", query = "SELECT o FROM Origen o")
    , @NamedQuery(name = "Origen.findByCodVuelo", query = "SELECT o FROM Origen o WHERE o.origenPK.codVuelo = :codVuelo")
    , @NamedQuery(name = "Origen.findByIata", query = "SELECT o FROM Origen o WHERE o.origenPK.iata = :iata")
    , @NamedQuery(name = "Origen.findByHora", query = "SELECT o FROM Origen o WHERE o.hora = :hora")
    , @NamedQuery(name = "Origen.findByFecha", query = "SELECT o FROM Origen o WHERE o.fecha = :fecha")})
public class Origen implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrigenPK origenPK;
    @Column(name = "HORA")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "IATA", referencedColumnName = "IATA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Aeropuerto aeropuerto;
    @JoinColumn(name = "COD_VUELO", referencedColumnName = "COD_VUELO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vuelo vuelo;

    public Origen() {
    }

    public Origen(OrigenPK origenPK) {
        this.origenPK = origenPK;
    }

    public Origen(String codVuelo, String iata) {
        this.origenPK = new OrigenPK(codVuelo, iata);
    }

    public OrigenPK getOrigenPK() {
        return origenPK;
    }

    public void setOrigenPK(OrigenPK origenPK) {
        this.origenPK = origenPK;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (origenPK != null ? origenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Origen)) {
            return false;
        }
        Origen other = (Origen) object;
        if ((this.origenPK == null && other.origenPK != null) || (this.origenPK != null && !this.origenPK.equals(other.origenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Origen[ origenPK=" + origenPK + " ]";
    }
    
}
