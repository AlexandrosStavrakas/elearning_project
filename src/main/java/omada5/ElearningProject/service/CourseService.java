package omada5.ElearningProject.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;


import omada5.ElearningProject.domain.*;
/**
 * The CourseService Class
 * @author thegr
 */
public class CourseService 
{
    private EntityManager em;

    /**
     * sets the entity manager for the service
     * @param em
     */
    public CourseService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     * updates a Course
     * @param course
     * @return true if update succeed
     */
    public boolean saveOrUpdateCourse(Course course) 
    {
        if (course != null) 
        {
            em.merge(course);
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param course
     * @return
     */
    public Course save(Course course) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (course.getCourse_id() > 0) 
        {
            course = em.merge(course);
        } 
        else 
        {
            em.persist(course);
        }
        tx.commit();
        return course;
    }
    
    /**
     * create a new Course
     * @param title
     * @param description
     * @param field
     * @param uniId
     * @return the newCourse
     */
    public Course createCourse(String title, String description, String field, String uniId) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (title != null && description != null && field != null && uniId != null) 
        {
            List<Course> results;
            results = em.createQuery("select c from Course c where c.uniCourseId =:uniCourseId").setParameter("uniCourseId", uniId).getResultList();
            if(results.size()>0)
            {
                tx.rollback();
                return null;
            }
            else
            {
                Course newCourse = new Course(title,description,field,uniId);
                em.persist(newCourse);
                tx.commit();
                return newCourse;
            }
        }
        tx.rollback();
        return null;
    }
    
    /**
     * creates a new Course for a Teacher
     * @param title
     * @param description
     * @param field
     * @param uniId
     * @param teacher
     * @return the newCourse
     */
    public Course createCourse(String title, String description, String field, String uniId, Teacher teacher) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (title != null && description != null && field != null && uniId != null) 
        {
            List<Course> results;
            results = em.createQuery("select c from Course c where c.uniCourseId =:uniCourseId").setParameter("uniCourseId", uniId).getResultList();
            if(results.size()>0)
            {
                tx.rollback();
                return null;
            }
            else
            {
                Course newCourse = new Course(title,description,field,uniId);
                newCourse.setTeacher(teacher);
                teacher.addCourse(newCourse);
                em.persist(newCourse);
                tx.commit();
                return newCourse;
            }
        }
        tx.rollback();
        return null;
    }

    /**
     * returns a Course by id
     * @param id
     * @return the Course
     */
    public Course findCourseById(int id) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Course course = null;
        try 
        {
            course = em.find(Course.class, id);
            tx.commit();
        } 
        catch (NoResultException ex) 
        {
            tx.rollback();
        }
        return course;
    }

    /**
     * returns a List with all Courses of a Teacher
     * @param teacherSurName
     * @return results
     */
    public List<Course> findCourseByTeacher(String teacherSurName) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> results = null;
        results = em.createQuery("select c from Course c join fetch c.teacher as t where t.surname like :surname").setParameter("surname", teacherSurName).getResultList();
        tx.commit();
        return results;
    }
    
    /**
     * returns a List with all Courses of a Student
     * @param sid
     * @return results
     */
    public List<Course> findCoursesByStudent(int sid) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> results = null;
        results = em.createQuery("select c from Course c join fetch c.students as s where s.person_id =:id").setParameter("id", sid).getResultList();
        tx.commit();
        return results;
    }

    /**
     * returns a List with all Courses of a Title
     * @param title
     * @return results
     */ 
    public List<Course> findCourseByTitle(String title) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> results = null;
        results = em.createQuery("select c from Course c where c.title like :title").setParameter("title", "%" + title + "%").getResultList();
        tx.commit();
        return results;
    }

    /**
     * returns a List with all Courses
     * @return listCourses
     */
    public List<Course> findAllCourses() 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> listCourses = em.createQuery("Select c FROM Course c").getResultList();
        if(listCourses == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return listCourses;
        }
    }
    
    /**
     * deletes a Course by UniId
     * @param uniCourseId
     * @return true if deletion succeed
     */
    public boolean deleteCourseFromUniId(String uniCourseId) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            List<Course> results = null;
            results = em.createQuery("select c from Course c where c.uniCourseId like :uniCourseId").setParameter("uniCourseId", uniCourseId).getResultList();
            Course course = results.get(0);
            if(course != null)
            {    
                em.remove(course);
                tx.commit();
            }
        }
        catch (EntityNotFoundException e) 
        {
            tx.rollback();
            return false;
        }
        return true;
    }
    
    /**
     *
     * @param courseId
     * @return
     */
    public boolean deleteCourse(int courseId) {
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			Course course = em.getReference(Course.class, courseId);
			em.remove(course);
		} catch (EntityNotFoundException e) {
			tx.rollback();
			return false;
		}

		tx.commit();

		return true;

	}
    
    /**
     * assigns a Course to a Teacher
     * @param teacher
     * @param course
     * @return true if assignment succeed
     */
    public boolean AssignCourseToTeacher(Teacher teacher, Course course) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(course != null && teacher != null)
        {
            course.setTeacher(teacher);
            teacher.addCourse(course);
            em.merge(course);
            em.merge(teacher);
        }
        tx.commit();
        return true;
    }
}
