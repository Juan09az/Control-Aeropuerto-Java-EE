/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camil
 */
@Entity
@Table(name = "RESERVA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r")
    , @NamedQuery(name = "Reserva.findByCodVuelo", query = "SELECT r FROM Reserva r WHERE r.reservaPK.codVuelo = :codVuelo")
    , @NamedQuery(name = "Reserva.findByIdPasajero", query = "SELECT r FROM Reserva r WHERE r.reservaPK.idPasajero = :idPasajero")
    , @NamedQuery(name = "Reserva.findByClave", query = "SELECT r FROM Reserva r WHERE r.reservaPK.clave = :clave")
    , @NamedQuery(name = "Reserva.findByAsiento", query = "SELECT r FROM Reserva r WHERE r.asiento = :asiento")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReservaPK reservaPK;
    @Column(name = "ASIENTO")
    private String asiento;
    @JoinColumn(name = "COD_CLASE", referencedColumnName = "COD_CLASE")
    @ManyToOne
    private Clase codClase;
    @JoinColumn(name = "PAGO", referencedColumnName = "ID_PAGO")
    @ManyToOne
    private Pago pago;
    @JoinColumn(name = "ID_PASAJERO", referencedColumnName = "ID_PASAJERO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pasajero pasajero;
    @JoinColumn(name = "COD_VUELO", referencedColumnName = "COD_VUELO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Vuelo vuelo;

    public Reserva() {
    }

    public Reserva(ReservaPK reservaPK) {
        this.reservaPK = reservaPK;
    }

    public Reserva(String codVuelo, String idPasajero, String clave) {
        this.reservaPK = new ReservaPK(codVuelo, idPasajero, clave);
    }

    public ReservaPK getReservaPK() {
        return reservaPK;
    }

    public void setReservaPK(ReservaPK reservaPK) {
        this.reservaPK = reservaPK;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public Clase getCodClase() {
        return codClase;
    }

    public void setCodClase(Clase codClase) {
        this.codClase = codClase;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
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
        hash += (reservaPK != null ? reservaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.reservaPK == null && other.reservaPK != null) || (this.reservaPK != null && !this.reservaPK.equals(other.reservaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Reserva[ reservaPK=" + reservaPK + " ]";
    }
    
}
