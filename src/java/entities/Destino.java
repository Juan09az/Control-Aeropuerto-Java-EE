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
@Table(name = "DESTINO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Destino.findAll", query = "SELECT d FROM Destino d")
    , @NamedQuery(name = "Destino.findByCodVuelo", query = "SELECT d FROM Destino d WHERE d.destinoPK.codVuelo = :codVuelo")
    , @NamedQuery(name = "Destino.findByIata", query = "SELECT d FROM Destino d WHERE d.destinoPK.iata = :iata")
    , @NamedQuery(name = "Destino.findByHora", query = "SELECT d FROM Destino d WHERE d.hora = :hora")
    , @NamedQuery(name = "Destino.findByFecha", query = "SELECT d FROM Destino d WHERE d.fecha = :fecha")})
public class Destino implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DestinoPK destinoPK;
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

    public Destino() {
    }

    public Destino(DestinoPK destinoPK) {
        this.destinoPK = destinoPK;
    }

    public Destino(String codVuelo, String iata) {
        this.destinoPK = new DestinoPK(codVuelo, iata);
    }

    public DestinoPK getDestinoPK() {
        return destinoPK;
    }

    public void setDestinoPK(DestinoPK destinoPK) {
        this.destinoPK = destinoPK;
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
        hash += (destinoPK != null ? destinoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Destino)) {
            return false;
        }
        Destino other = (Destino) object;
        if ((this.destinoPK == null && other.destinoPK != null) || (this.destinoPK != null && !this.destinoPK.equals(other.destinoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Destino[ destinoPK=" + destinoPK + " ]";
    }
    
}
