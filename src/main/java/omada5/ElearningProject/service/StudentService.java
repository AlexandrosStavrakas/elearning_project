package omada5.ElearningProject.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import omada5.ElearningProject.domain.*;

/**
 * The StudentService Class
 * @author thegr
 */
public class StudentService 
{
    private EntityManager em;

    /**
     * sets the entity manager for the service
     * @param em
     */
    public StudentService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     * updates a Student
     * @param id
     * @param name
     * @param Surname
     * @param mail
     * @param password
     * @return the updated Student
     */
    public Student saveOrUpdateStudent(int id,String name, String Surname, String mail, String password) 
    {
        if (id < 0)
        {
            return null;
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Student> students;
        students = em.createQuery("select s from Student s").getResultList();
        for(Student student: students)
        {
            if(student.getPerson_id()==id)
            {
                if(name!=null)
                    student.setName(name);
                if(Surname!=null)
                    student.setSurname(Surname);
                if(mail!=null)
                    student.setEmail(mail);
                if(password!=null)
                    student.setPassword(password);
                em.merge(student);
                tx.commit();
                return student;
            }
        }
        return null;
    }
    
    /**
     * creates a new Student
     * @param name
     * @param Surname
     * @param mail
     * @param password
     * @return a new Student
     */
    public Student createStudent(String name, String Surname, String mail, String password)
    {	
        if (name == null && Surname == null && mail == null && password == null)
        {
            return null;
        }
        Student newStudent = new Student(name, Surname, mail, password);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(newStudent);
        tx.commit();
        return newStudent;
    }
    
    /**
     *
     * @param email
     * @return
     */
    public boolean deleteStudent(String email) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            Student student = (Student) em.createQuery("select s from Student s where s.email =:email").setParameter("email", email).getSingleResult();
            for(int i = 0; i < student.getCourses().size(); i++)
            {
                //student.getCourses().get(i);
                em.merge(student.getCourses().get(i));
            }
            student.getCourses().clear();
            em.remove(student);
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
     * registration of a Student to a Course
     * @param student
     * @param course
     * @return true if registration succeed
     */
    public boolean RegisterCourse(Student student, Course course)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> results = null;
        results = em.createQuery("select c from Course c inner join c.students s where s.person_id in :studentsId").setParameter("studentsId", student.getPerson_id()).getResultList();
        boolean found = false;
        for(Course c :results)
        {
            if(c.getCourse_id() == course.getCourse_id())
                found = true;
        }
        if(!found)
        {
            student.addCourses(course);
            em.merge(student);
            em.merge(course);
            tx.commit();
            return true;
        }
        else
        {
            tx.rollback();
            return false;
        }
    } 
    
    /**
     * deletion of a Student to a Course
     * @param student
     * @param course
     * @return true if deletion succeed
     */
    public boolean UnregisterCourse(Student student, Course course)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Course> results = null;
        results = em.createQuery("select c from Course c inner join c.students s where s.person_id in :studentsId").setParameter("studentsId", student.getPerson_id()).getResultList();
        boolean found = false;
        for(Course c :results)
        {
            if(c.getCourse_id() == course.getCourse_id())
                found = true;
        }
        if(found)
        {
            student.removeCourse(course);
            tx.commit();
            return true;
        }
        else
        {
            tx.rollback();
            return false;
        }
    } 
    
    /**
     * return a List with all Students
     * @return results
     */
    public List<Student> findAllStudents() 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Student> listStudents = em.createQuery("Select s FROM Student s").getResultList();
        if(listStudents == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return listStudents;
        }
    }
    
    /**
     * returns a Student by Email
     * @param email
     * @return the Student
     */
    public Student findStudentByEmail(String email) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student student;
        student = (Student) em.createQuery("select s from Student s where s.email like :email").setParameter("email", email).getSingleResult();
        if(student == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return student;
        }
    }
    
    /**
     *
     * @param id
     * @return
     */
    public Student findStudentById(int id) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Student student;
        student = (Student) em.createQuery("select s from Student s where s.person_id =:id").setParameter("id", id).getSingleResult();
        if(student == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return student;
        }
    }
}
