package omada5Test.ElearningProjectTest.testResourse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.*;
import omada5.ElearningProject.persistence.*;
/**
 *
 * @author thegr
 */
public abstract class ElearningProjectResourceTest extends JerseyTest
{
    Initializer dataHelper;

    public ElearningProjectResourceTest() 
    {
        super();
    }

    public ElearningProjectResourceTest(TestContainerFactory testContainerFactory) 
    {
        super(testContainerFactory);
    }

    public ElearningProjectResourceTest(Application jaxrsApplication) 
    {
        super(jaxrsApplication);
    }

    @Override
    public void setUp() throws Exception 
    {
        super.setUp();
        dataHelper = new Initializer();
        dataHelper.prepareData();
    }
    
    public Person login(String email, String password)
    {
        EntityManager em = JPAUtil.getCurrentEntityManager();     
        PersonService s = new PersonService(em);
        Person person = s.findPersonByCredentials(email, password);
        return person;
    }

    public University UniversityByAdmin(Uni_Admin admin)
    {
        EntityManager em = JPAUtil.getCurrentEntityManager(); 
        UniAdminService serviceAdmin = new UniAdminService(em);
        University uni = serviceAdmin.findUniversityByAdmin(admin.getPerson_id());
        return uni;
    }
    
    public List<Course> findCoursesByTitle(String title) 
    {
        EntityManager em = JPAUtil.getCurrentEntityManager();	
        CourseService service = new CourseService(em);
        List<Course> courses = service.findCourseByTitle(title);
        em.close();
        return courses;
    }
    
    public List<Course> findCourseByTeacher(String teacherSurName) 
    {
        EntityManager em = JPAUtil.getCurrentEntityManager(); 
        List<Course> results = null;
        results = em.createQuery("select c from Course c join fetch c.teacher as t where t.surname like :surname").setParameter("surname", teacherSurName).getResultList();
        return results;
    }
    
    public Teacher findTeacherByName(String teacherSurName)
    {
    	EntityManager em = JPAUtil.getCurrentEntityManager(); 
        Teacher teacher = null;
        teacher = (Teacher)em.createQuery("select t from Teacher t where t.surname =:surname").setParameter("surname", teacherSurName).getSingleResult();
        return teacher;
    }
}
