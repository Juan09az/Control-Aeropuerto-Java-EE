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
@Table(name = "AEROPUERTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aeropuerto.findAll", query = "SELECT a FROM Aeropuerto a")
    , @NamedQuery(name = "Aeropuerto.findByIata", query = "SELECT a FROM Aeropuerto a WHERE a.iata = :iata")
    , @NamedQuery(name = "Aeropuerto.findByNombre", query = "SELECT a FROM Aeropuerto a WHERE a.nombre = :nombre")})
public class Aeropuerto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IATA")
    private String iata;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aeropuerto")
    private List<Destino> destinoList;
    @JoinColumn(name = "CIUDAD", referencedColumnName = "COD_CIU")
    @ManyToOne
    private Ciudad ciudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aeropuerto")
    private List<Origen> origenList;

    public Aeropuerto() {
    }

    public Aeropuerto(String iata) {
        this.iata = iata;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Destino> getDestinoList() {
        return destinoList;
    }

    public void setDestinoList(List<Destino> destinoList) {
        this.destinoList = destinoList;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @XmlTransient
    public List<Origen> getOrigenList() {
        return origenList;
    }

    public void setOrigenList(List<Origen> origenList) {
        this.origenList = origenList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iata != null ? iata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aeropuerto)) {
            return false;
        }
        Aeropuerto other = (Aeropuerto) object;
        if ((this.iata == null && other.iata != null) || (this.iata != null && !this.iata.equals(other.iata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Aeropuerto[ iata=" + iata + " ]";
    }
    
}
