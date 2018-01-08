package omada5.ElearningProject.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import omada5.ElearningProject.domain.*;
/**
 * The TeacherService Class
 * @author thegr
 */
public class TeacherService 
{
    private final EntityManager em;

    /**
     *sets the entity manager for the service
     * @param em
     */
    public TeacherService(EntityManager em) 
    {
        this.em = em;
    }

    /**
     * returns a List with Teachers by LastNam
     * @param surname
     * @return results
     */
    public List<Teacher> findTeachersByLastName(String surname) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Teacher> results;
        results = em.createQuery("select t from Teacher t where t.surname like :surname ").setParameter("surname", surname).getResultList();
        tx.commit();
        return results;
    }
    
    /**
     * updates a Teacher
     * @param id
     * @param name
     * @param Surname
     * @param mail
     * @param password
     * @return the updated Teacher
     */
    public Teacher saveOrUpdateTeacher(int id,String name, String Surname, String mail, String password) 
    {	
        if (id < 0)
        {
            return null;
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Teacher> teachers = null;
        teachers = em.createQuery("select t from Teacher t").getResultList();
        for(Teacher teacher: teachers)
        {
            if(teacher.getPerson_id()==id)
            {
                if(name!=null)
                    teacher.setName(name);
                if(Surname!=null)
                    teacher.setSurname(Surname);
                if(mail!=null)
                    teacher.setEmail(mail);
                if(password!=null)
                    teacher.setPassword(password);
                em.merge(teacher);
                tx.commit();
                return teacher;
            }
        }
        return null;
    }

    /**
     * creates a new Teacher
     * @param t
     * @return the new Teacher
     */
    public boolean createTeacher(Teacher t) 
    {
        if (t != null) 
        {
            em.persist(t);
            return true;
        }
        return false;
    }
    
    /**
     * creates a new Teacher		
     * @param name
     * @param Surname
     * @param mail
     * @param password
     * @return the new Teacher
     */
    public Teacher createTeacher(String name, String Surname, String mail, String password)
    {	
        if (name == null && Surname == null && mail == null && password == null)
        {
            return null;
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Teacher> teachers;
        teachers = em.createQuery("select t from Teacher t").getResultList();
        for(Teacher teacher: teachers)
        {
            if(mail.equalsIgnoreCase(teacher.getEmail()))
            {
                tx.rollback();
                return null;
            }
        }
        Teacher newTeacher = new Teacher(name, Surname, mail, password);
        em.persist(newTeacher);
        tx.commit();
        return newTeacher;
    }
    
    /**
     *
     * @param email
     * @return
     */
    public boolean deleteTeacher(String email) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            Teacher teacher = (Teacher) em.createQuery("select t from Teacher t where t.email =:email").setParameter("email", email).getSingleResult();
            for(int i = 0; i < teacher.getCourses().size(); i++)
            {
                //teacher.getCourses().get(i);
                em.merge(teacher.getCourses().get(i));
            }
            teacher.getCourses().clear();
            em.remove(teacher);
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
     * returns a List with all Teachers
     * @return results
     */
    public List<Teacher> findAllTeachers() 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Teacher> results = null;
        results = em.createQuery("select t from Teacher t", Teacher.class).getResultList();
        tx.commit();
        return results;
    }
}
