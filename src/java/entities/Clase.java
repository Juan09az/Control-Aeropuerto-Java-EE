/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "CLASE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clase.findAll", query = "SELECT c FROM Clase c")
    , @NamedQuery(name = "Clase.findByCodClase", query = "SELECT c FROM Clase c WHERE c.codClase = :codClase")
    , @NamedQuery(name = "Clase.findByTarifa", query = "SELECT c FROM Clase c WHERE c.tarifa = :tarifa")
    , @NamedQuery(name = "Clase.findByNombre", query = "SELECT c FROM Clase c WHERE c.nombre = :nombre")})
public class Clase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_CLASE")
    private String codClase;
    @Basic(optional = false)
    @Column(name = "TARIFA")
    private double tarifa;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "codClase")
    private List<Reserva> reservaList;

    public Clase() {
    }

    public Clase(String codClase) {
        this.codClase = codClase;
    }

    public Clase(String codClase, double tarifa) {
        this.codClase = codClase;
        this.tarifa = tarifa;
    }

    public String getCodClase() {
        return codClase;
    }

    public void setCodClase(String codClase) {
        this.codClase = codClase;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codClase != null ? codClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clase)) {
            return false;
        }
        Clase other = (Clase) object;
        if ((this.codClase == null && other.codClase != null) || (this.codClase != null && !this.codClase.equals(other.codClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Clase[ codClase=" + codClase + " ]";
    }
    
}
