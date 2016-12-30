/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wispmanager.entities.jpa;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import wispmanager.entities.PersonaGiuridica;
import wispmanager.entities.jpa.exceptions.NonexistentEntityException;
import wispmanager.entities.jpa.exceptions.RollbackFailureException;

/**
 *
 * @author torsello
 */
public class PersonaGiuridicaJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "WISPManagerPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersonaGiuridica personaGiuridica) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(personaGiuridica);
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

    public void edit(PersonaGiuridica personaGiuridica) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            personaGiuridica = em.merge(personaGiuridica);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = personaGiuridica.getId();
                if (findPersonaGiuridica(id) == null) {
                    throw new NonexistentEntityException("The personaGiuridica with id " + id + " no longer exists.");
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
            PersonaGiuridica personaGiuridica;
            try {
                personaGiuridica = em.getReference(PersonaGiuridica.class, id);
                personaGiuridica.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaGiuridica with id " + id + " no longer exists.", enfe);
            }
            em.remove(personaGiuridica);
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

    public List<PersonaGiuridica> findPersonaGiuridicaEntities() {
        return findPersonaGiuridicaEntities(true, -1, -1);
    }

    public List<PersonaGiuridica> findPersonaGiuridicaEntities(int maxResults, int firstResult) {
        return findPersonaGiuridicaEntities(false, maxResults, firstResult);
    }

    public List<PersonaGiuridica> findPersonaGiuridicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PersonaGiuridica as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PersonaGiuridica findPersonaGiuridica(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonaGiuridica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaGiuridicaCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from PersonaGiuridica as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

      public List<PersonaGiuridica> findByField(String strField, String strValue){
         EntityManager em =null;

        em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PersonaFisica where object(o)."+strField+" LIKE "+strValue);
            return q.getResultList();
      } finally {
            em.close();
        }

    }

}
