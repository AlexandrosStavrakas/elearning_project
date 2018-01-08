package omada5.ElearningProject.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author thegr
 */
public class JPAUtil 
{
    private static EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> currentEntityManager = new ThreadLocal<EntityManager>();
    
    /**
     *
     * @return
     */
    public static EntityManagerFactory getEntityManagerFactory() 
    {
        if (emf == null) 
        {
            emf = Persistence.createEntityManagerFactory("elearning");
        }
        return emf;
    }
    
    /**
     *
     * @return
     */
    public static EntityManager getCurrentEntityManager() 
    {      
        EntityManager em = currentEntityManager.get();         
        if (em  == null || !em.isOpen()) 
        {
            em = getEntityManagerFactory().createEntityManager();
            currentEntityManager.set(em);
        }
        return em;
    }
    
    /**
     *
     * @return
     */
    public static EntityManager createEntityManager() 
    {
    	return getEntityManagerFactory().createEntityManager();
    }

    /**
     *
     * @param runnable
     */
    public static void transactional(Runnable runnable)
    {	
    	EntityManager em = getCurrentEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	tx.begin();	
    	runnable.run();
    	tx.rollback();
    	em.close();
    }
}