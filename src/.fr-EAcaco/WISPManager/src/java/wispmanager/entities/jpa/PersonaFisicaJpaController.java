/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import wispmanager.entities.PersonaFisica;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;

/**
 *
 * @author torsello
 */
public class PersonaFisicaJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

   
    public void create(PersonaFisica personaFisica) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(personaFisica);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonaFisica personaFisica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            personaFisica = em.merge(personaFisica);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = personaFisica.getId();
                if (findPersonaFisica(id) == null) {
                    throw new NonexistentEntityException("The personaFisica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PersonaFisica personaFisica;
            try {
                personaFisica = em.getReference(PersonaFisica.class, id);
                personaFisica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaFisica with id " + id + " no longer exists.", enfe);
            }
            em.remove(personaFisica);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersonaFisica> findPersonaFisicaEntities() {
            return findPersonaFisicaEntities(true, -1, -1);
        
    }

    public List<PersonaFisica> findPersonaFisicaEntities(int maxResults, int firstResult) {
            return findPersonaFisicaEntities(false, maxResults, firstResult);
        
    }

    public List<PersonaFisica> findPersonaFisicaEntities(boolean all, int maxResults, int firstResult){
         EntityManager em =null;       
            
        em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PersonaFisica as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }            
            return q.getResultList();
      } finally {
            em.close();
        }

    }


    public List<PersonaFisica> findByField(String strField, String strValue){
         EntityManager em =null;

        em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PersonaFisica where object(o)."+strField+" LIKE "+strValue);
            return q.getResultList();
      } finally {
            em.close();
        }

    }


    public PersonaFisica findPersonaFisica(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonaFisica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaFisicaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from PersonaFisica as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }


}
