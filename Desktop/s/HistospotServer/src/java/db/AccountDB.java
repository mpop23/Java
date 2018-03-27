/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountDB.findAll", query = "SELECT a FROM AccountDB a")
    , @NamedQuery(name = "AccountDB.findById", query = "SELECT a FROM AccountDB a WHERE a.id = :id")
    , @NamedQuery(name = "AccountDB.findByGoogleId", query = "SELECT a FROM AccountDB a WHERE a.googleId = :googleId")})
public class AccountDB implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "google_id", nullable = false, length = 32, unique = true)
    private String googleId;
    @Column(name = "points", nullable = false)
    private int points;
    @Column(name = "level", nullable = false)
    private int level;
    /*@OneToOne(cascade = CascadeType.ALL, mappedBy = "accountDB")
    private ProfileDB profileDB;*/

    public AccountDB() {
    }

    public AccountDB(Integer id) {
        this.id = id;
    }

    public AccountDB(Integer id, String googleId, int points, int level) {
        this.id = id;
        this.googleId = googleId;
        this.points = points;
        this.level = level;
    }

     public AccountDB(String googleId, int points, int level) {
        this.googleId = googleId;
        this.points = points;
        this.level = level;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /*public ProfileDB getProfileDB() {
        return profileDB;
    }

    public void setProfileDB(ProfileDB profileDB) {
        this.profileDB = profileDB;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountDB)) {
            return false;
        }
        AccountDB other = (AccountDB) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AccountDB[ id=" + id + " ]";
    }

}
