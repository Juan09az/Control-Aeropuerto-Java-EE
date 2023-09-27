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
@Table(name = "CIUDAD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c")
    , @NamedQuery(name = "Ciudad.findByCodCiu", query = "SELECT c FROM Ciudad c WHERE c.codCiu = :codCiu")
    , @NamedQuery(name = "Ciudad.findByNombre", query = "SELECT c FROM Ciudad c WHERE c.nombre = :nombre")})
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_CIU")
    private String codCiu;
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "ciudad")
    private List<Aeropuerto> aeropuertoList;
    @JoinColumn(name = "COD_PAIS", referencedColumnName = "COD_PAIS")
    @ManyToOne
    private Pais codPais;

    public Ciudad() {
    }

    public Ciudad(String codCiu) {
        this.codCiu = codCiu;
    }

    public String getCodCiu() {
        return codCiu;
    }

    public void setCodCiu(String codCiu) {
        this.codCiu = codCiu;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Aeropuerto> getAeropuertoList() {
        return aeropuertoList;
    }

    public void setAeropuertoList(List<Aeropuerto> aeropuertoList) {
        this.aeropuertoList = aeropuertoList;
    }

    public Pais getCodPais() {
        return codPais;
    }

    public void setCodPais(Pais codPais) {
        this.codPais = codPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCiu != null ? codCiu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.codCiu == null && other.codCiu != null) || (this.codCiu != null && !this.codCiu.equals(other.codCiu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ciudad[ codCiu=" + codCiu + " ]";
    }
    
}
