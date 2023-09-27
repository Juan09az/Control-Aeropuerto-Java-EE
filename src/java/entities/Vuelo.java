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
@Table(name = "VUELO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vuelo.findAll", query = "SELECT v FROM Vuelo v")
    , @NamedQuery(name = "Vuelo.findByCodVuelo", query = "SELECT v FROM Vuelo v WHERE v.codVuelo = :codVuelo")
    , @NamedQuery(name = "Vuelo.findByPuertaEmbarque", query = "SELECT v FROM Vuelo v WHERE v.puertaEmbarque = :puertaEmbarque")
    , @NamedQuery(name = "Vuelo.findByEstado", query = "SELECT v FROM Vuelo v WHERE v.estado = :estado")})
public class Vuelo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_VUELO")
    private String codVuelo;
    @Column(name = "PUERTA_EMBARQUE")
    private String puertaEmbarque;
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "AVION", referencedColumnName = "MATRICULA")
    @ManyToOne
    private Avion avion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelo")
    private List<Destino> destinoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelo")
    private List<Reserva> reservaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vuelo")
    private List<Origen> origenList;

    public Vuelo() {
    }

    public Vuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public String getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(String puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    @XmlTransient
    public List<Destino> getDestinoList() {
        return destinoList;
    }

    public void setDestinoList(List<Destino> destinoList) {
        this.destinoList = destinoList;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
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
        hash += (codVuelo != null ? codVuelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vuelo)) {
            return false;
        }
        Vuelo other = (Vuelo) object;
        if ((this.codVuelo == null && other.codVuelo != null) || (this.codVuelo != null && !this.codVuelo.equals(other.codVuelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vuelo[ codVuelo=" + codVuelo + " ]";
    }
    
}
