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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "profile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProfileDB.findAll", query = "SELECT p FROM ProfileDB p")
    , @NamedQuery(name = "ProfileDB.findByAccount", query = "SELECT p FROM ProfileDB p WHERE p.account = :account")
    , @NamedQuery(name = "ProfileDB.findByEmail", query = "SELECT p FROM ProfileDB p WHERE p.email = :email")
    , @NamedQuery(name = "ProfileDB.findByFirstName", query = "SELECT p FROM ProfileDB p WHERE p.firstName = :firstName")
    , @NamedQuery(name = "ProfileDB.findByLastName", query = "SELECT p FROM ProfileDB p WHERE p.lastName = :lastName")})
public class ProfileDB implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "account", nullable = false)
    private Integer account;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", nullable = false, length = 256)
    private String email;
    @Column(name = "first_name", nullable = false, length = 256)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 256)
    private String lastName;
    @Lob
    @Column(name = "picture", nullable = true)
    private byte[] picture;
    /*@JoinColumn(name = "account", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private AccountDB accountDB;*/

    public ProfileDB() {
    }

    public ProfileDB(Integer account) {
        this.account = account;
    }

    public ProfileDB(Integer account, String email, String firstName, String lastName) {
        this.account = account;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    /*public AccountDB getAccountDB() {
        return accountDB;
    }

    public void setAccountDB(AccountDB accountDB) {
        this.accountDB = accountDB;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (account != null ? account.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfileDB)) {
            return false;
        }
        ProfileDB other = (ProfileDB) object;
        if ((this.account == null && other.account != null) || (this.account != null && !this.account.equals(other.account))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ProfileDB[ account=" + account + " ]";
    }
    
}
