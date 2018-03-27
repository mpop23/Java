/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import db.AccountDB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alex
 */
@Stateless
public class AccountDBFacade extends AbstractFacade<AccountDB> {

    @PersistenceContext(unitName = "HistospotServerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void updateEm() {
        this.em.flush();
    }

    public AccountDBFacade() {
        super(AccountDB.class);
    }

    public AccountDB findByGoogleId(String googleId) {
        try {
            TypedQuery<AccountDB> q = this.em.createNamedQuery("AccountDB.findByGoogleId", AccountDB.class);
            q.setParameter("googleId", googleId);
            return q.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
    }
}
