package omada5.ElearningProject.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.persistence.JPAUtil;

/**
 * The UniversityService Class
 * @author thegr
 */
public class UniversityService 
{
    final private EntityManager em;
    
    /**
     * sets the entity manager for the service
     * @param em
     */
    public UniversityService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     * gets the current entity manager from the JPAUtil class
     */
    public UniversityService() 
    {
            em = JPAUtil.getCurrentEntityManager();
    }

    /**
     * creates a new University from name and mail
     * @param name
     * @param mail
     * @return newUniversity
     */
    public University createUniversity(String name, String mail)
    {	
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (name == null || mail == null)
        {
            tx.rollback();
            return null;
        }
        University newUniversity = new University(name, mail);
        em.persist(newUniversity);
        tx.commit();
        return newUniversity;
    }
    
    /**
     * returns a List from University with the specific name
     * @param name
     * @return results
     */
    public List<University> findUniversityByName(String name) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<University> results;
        results = em.createQuery("select u from University u where u.name like :name").setParameter("name", "%" + name + "%").getResultList();
        tx.commit();
        return results;
    }

    /**
     * returns a List with all created Universities
     * @return results
     */
    public List<University> findAllUniversities() 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<University> results = em.createQuery("Select u FROM University u").getResultList();
        if(results == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return results;
        }
    }
    
    /**
     * deletes a University
     * @param email
     * @return true if the deletion done
     */
    public boolean deleteUniversityFromEmail(String email) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            University uni = (University) em.createQuery("select u from University u where u.email =:email").setParameter("email", email).getSingleResult();
            uni.setUni_Admin(null);
            for(int i = 0; i < uni.getCourses().size(); i++)
            {
                uni.getCourses().get(i).setUniversity(null);
                em.merge(uni.getCourses().get(i));
            }
            uni.getCourses().clear();
            em.remove(uni);
        }
        catch (EntityNotFoundException e) 
        {
            tx.rollback();
            return false;
        }
        tx.commit();
        return true;
    }
    
    /**
     * updates a university
     * @param id
     * @param name
     * @param mail
     * @return true if it is updated of false if it is not
     */
    public boolean saveOrUpdateUniversity(int id,String name, String mail) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<University> unis;
        unis = em.createQuery("select u from University u").getResultList();
        for(University uni: unis)
        {
            if(uni.getUni_id()==id)
            {
                if(name!=null)
                    uni.setName(name);
                if(mail!=null)
                    uni.setEmail(mail);
                em.merge(uni);
                tx.commit();
                return true;
            }
        }
        tx.rollback();
        return false;
    }
    
    /**
     * Assigns a person as an administrator for the University 
     * @param uni
     * @param uni_admin
     * @return true if the assignment is fine or false if it is not
     */
    public boolean AssignAdminToUni(University uni, Uni_Admin uni_admin)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            uni.setUni_Admin(uni_admin);
            em.merge(uni);
        }
        catch (EntityNotFoundException e) 
        {
            tx.rollback();
            return false;
        }
        tx.commit();
        return true;
    }
}
