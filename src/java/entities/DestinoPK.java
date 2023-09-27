/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author camil
 */
@Embeddable
public class DestinoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_VUELO")
    private String codVuelo;
    @Basic(optional = false)
    @Column(name = "IATA")
    private String iata;

    public DestinoPK() {
    }

    public DestinoPK(String codVuelo, String iata) {
        this.codVuelo = codVuelo;
        this.iata = iata;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codVuelo != null ? codVuelo.hashCode() : 0);
        hash += (iata != null ? iata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DestinoPK)) {
            return false;
        }
        DestinoPK other = (DestinoPK) object;
        if ((this.codVuelo == null && other.codVuelo != null) || (this.codVuelo != null && !this.codVuelo.equals(other.codVuelo))) {
            return false;
        }
        if ((this.iata == null && other.iata != null) || (this.iata != null && !this.iata.equals(other.iata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DestinoPK[ codVuelo=" + codVuelo + ", iata=" + iata + " ]";
    }
    
}
