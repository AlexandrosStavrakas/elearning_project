package omada5.ElearningProject.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.persistence.JPAUtil;

/**
 * The UniAdminService class
 * @author thegr
 */
public class UniAdminService 
{
    final private EntityManager em;

    /**
     * sets the entity manager for the service
     * @param em
     */
    public UniAdminService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     * gets the current entity manager from the JPAUtil class
     */
    public UniAdminService() 
    {
            em = JPAUtil.getCurrentEntityManager();
    }

    /**
     * returns a List with all Administrators with a specific Surname
     * @param surname
     * @return results
     */
    public List<Uni_Admin> findUniAdminsByLastName(String surname) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Uni_Admin> results = null;
        results = em.createQuery("select ua from Uni_Admin ua where ua.surname like :surname ").setParameter("surname", surname).getResultList();
        tx.commit();
        return results;
    }

    /**
     * return an administrator from a specific id
     * @param id
     * @return the administrator
     */
    public Uni_Admin findUni_AdminById(int id) 
    {
        return em.find(Uni_Admin.class, id);
    }

    /**
     * updates an administrator
     * @param id
     * @param name
     * @param Surname
     * @param mail
     * @param password
     * @return the updated administrator
     */
    public Uni_Admin saveOrUpdateUni_Admin(int id,String name, String Surname, String mail, String password) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Uni_Admin> uniAdmins;
        uniAdmins = em.createQuery("select t from Uni_Admin t").getResultList();
        for(Uni_Admin uniAdmin: uniAdmins)
        {
            if(uniAdmin.getPerson_id()==id)
            {
                if(name!=null)
                    uniAdmin.setName(name);
                if(Surname!=null)
                    uniAdmin.setSurname(Surname);
                if(mail!=null)
                    uniAdmin.setEmail(mail);
                if(password!=null)
                    uniAdmin.setPassword(password);
                em.merge(uniAdmin);
                tx.commit();
                return uniAdmin;
            }
        }
        tx.rollback();
        return null;
    }
    
    /**
     * creates a new administrator
     * @param name
     * @param Surname
     * @param mail
     * @param password
     * @return the created administrator
     */
    public Uni_Admin createUniAdmin(String name, String Surname, String mail, String password)
    {	
        if (name == null && Surname == null && mail == null && password == null)
        {
            return null;
        }
        Uni_Admin newUni_Admin = new Uni_Admin(name, Surname, mail, password);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(newUni_Admin);
        tx.commit();
        return newUni_Admin;
    }
    
    /**
     *
     * @param email
     * @return
     */
    public boolean deleteUniAdmin(String email) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            Uni_Admin admin = (Uni_Admin) em.createQuery("select a from Uni_Admin a where a.email =:email").setParameter("email", email).getSingleResult();
            University uni = (University)em.createQuery("select u from University u where u.uni_Admin.email =:email").setParameter("email", email).getSingleResult();
            uni.setUni_Admin(null);
            em.merge(uni);
            em.remove(admin);
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
     * returns a List with all the university administrators
     * @return results
     */
    public List<Uni_Admin> findAllUniAdmins() 
    {
        List<Uni_Admin> results = null;
        results = em.createQuery("select ua from Uni_Admin ua").getResultList();
        return results;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public University findUniversityByAdmin(int id)
    {
    	University university = (University)em.createQuery("select u from University u where u.uni_Admin.person_id =:id").setParameter("id", id).getSingleResult();
    	return university;
    }
}
