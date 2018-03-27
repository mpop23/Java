/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import db.ProfileDB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alex
 */
@Stateless
public class ProfileDBFacade extends AbstractFacade<ProfileDB> {

    @PersistenceContext(unitName = "HistospotServerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfileDBFacade() {
        super(ProfileDB.class);
    }
    
}
