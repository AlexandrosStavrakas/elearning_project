package omada5.ElearningProject.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import omada5.ElearningProject.domain.*;
/**
 * The Person Class
 * @author thegr
 */
public class PersonService 
{
    private EntityManager em;

    /**
     * sets the entity manager for the service
     * @param em
     */
    public PersonService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     * returns a List with all Persons
     * @return results
     */
    public List<Person> findAllPersons() 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Person> results = null;
        results = em.createQuery("select p from Person p").getResultList();
        tx.commit();
        return results;
    }
    
    /**
     * returns a Person by Credentials
     * @param email
     * @param password
     * @return the Person with the specific email and password
     */
    public Person findPersonByCredentials(String email, String password) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Person result;
        result = (Person) em.createQuery("select p from Person p where p.email =:email and p.password=:password").setParameter("email", email).setParameter("password", password).getSingleResult();
        if(result == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            if(result.getClass().getSimpleName().equals("Teacher"))
                result  = (Teacher) result;
            if(result.getClass().getSimpleName().equals("Student"))
                result  = (Student) result;
            if(result.getClass().getSimpleName().equals("Uni_Admin"))
                result  = (Uni_Admin) result;
        }
        tx.commit();
        return result;     
    }
    
    /**
     * returns a Student by Credentials
     * @param email
     * @param password
     * @return the Student if detection succeed or null if not
     */
    public Student findStudentByCredentials(String email, String password) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student s;
        s = (Student) em.createQuery("select s from Student s where s.email like :email ").setParameter("email", email).getSingleResult();
        tx.commit();
        if(s == null)
            return null;
        else if(s.getPassword().equals(password))
            return s;
        else 
            return null;
    }
    
    /**
     * returns a Teacher by Credentials
     * @param email
     * @param password
     * @return the Teacher if detection succeed or null if not
     */
    public Teacher findTeacherByCredentials(String email, String password) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Teacher t;
        t = (Teacher) em.createQuery("select t from Teacher t where t.email like :email ").setParameter("email", email).getSingleResult();
        tx.commit();
        if(t == null)
            return null;
        else if(t.getPassword().equals(password))
            return t;
        else 
            return null;
    }
    
    /**
     * returns a Uni_Admin by credentials
     * @param email
     * @param password
     * @return the Uni_Admin if detection succeed or null if not
     */
    public Uni_Admin findAdminByCredentials(String email, String password) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Uni_Admin admin;
        admin = (Uni_Admin) em.createQuery("select t from Uni_Admin t where t.email like :email ").setParameter("email", email).getSingleResult();
        tx.commit();
        if(admin == null)
            return null;
        else if(admin.getPassword().equals(password))
            return admin;
        else 
            return null;
    }
}
