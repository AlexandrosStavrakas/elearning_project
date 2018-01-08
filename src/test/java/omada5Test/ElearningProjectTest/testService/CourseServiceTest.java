package omada5Test.ElearningProjectTest.testService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.CourseService;
import omada5.ElearningProject.service.MaterialService;
import omada5.ElearningProject.service.PersonService;
import omada5.ElearningProject.service.ProjectService;
import omada5.ElearningProject.service.TeacherService;
/**
 *
 * @author thegr
 */
public class CourseServiceTest extends ElearningProjectServiceTest
{ 

    /**
     *
     */
    @Test
    public void testFindAllCourses() 
    {
        CourseService service = new CourseService(em);
        List<Course> courses = service.findAllCourses();
        
        assertNotNull(courses);
        assertEquals(3, courses.size());
    }

    /**
     *
     */
    @Test
    public void testFindCoursesByTeacher() 
    {
        CourseService service = new CourseService(em);
        List<Course> courses = service.findCourseByTeacher("Giakoumakis");

        assertNotNull(courses);
        assertEquals(1, courses.size());
    }

    /**
     *
     */
    @Test
    public void testFindCourseById() 
    {
        CourseService service = new CourseService(em);
        List<Course> allCourses = service.findAllCourses();
        Course course = service.findCourseById(allCourses.get(0).getCourse_id());
        assertNotNull("Expected non null course", course);
    }
    
    /**
     *
     */
    @Test
    public void testFindCourseByTitle() 
    {
        CourseService service = new CourseService(em);
        List<Course> courses = service.findCourseByTitle("%java%");
        assertNotNull(courses);
        assertEquals(1, courses.size());
        Teacher t = courses.get(0).getTeacher();
        assertNotNull(t);
        assertEquals(t.getSurname(), "Kotidis");
    }
    
    /**
     *
     */
    @Test
    public void testsaveOrUpdateCourse()
    {
        CourseService service = new CourseService(em);
        Course c = new Course("R","first lesson of R language","programming","inf9");
        boolean f = service.saveOrUpdateCourse(c);
        assertNotNull(f);
        assertEquals(f,true);
    }
    
    /**
     *
     */
    @Test
    public void testcreateCourse()
    {
        CourseService service = new CourseService(em);
        //Course c = new Course("R","first lesson of R language","programming","inf9");
        //Course c = 
        service.createCourse("R","first lesson of R language","programming","inf9");
        List<Course> courses = service.findAllCourses();
        assertNotNull(courses);
        assertEquals(4, courses.size());
    }
    
    /**
     *
     */
    @Test
    public void testcreateFullCourse()
    {
        PersonService log = new PersonService(em);
        Person p = log.findPersonByCredentials("stavrakasa@aueb.gr", "1234");
        p.getClass().getSimpleName().equals("Uni_Admin");
        TeacherService s = new TeacherService(em);
        List<Teacher> teachers = s.findTeachersByLastName("Giakoumakis");
        CourseService service = new CourseService(em);
        //Course c = 
        service.createCourse("R","first lesson of R language","programming","inf9", teachers.get(0));
        List<Teacher> t = s.findTeachersByLastName("Giakoumakis");
        assertNotNull(t.get(0));
        assertEquals(2, t.get(0).getCourses().size());
        
    }
    /*
    @Test
    public void testdeleteCourse()
    {
        CourseService service = new CourseService(em);
    }
    
    @Test
    public void testdeleteCourseFromId()
    {
        CourseService service = new CourseService(em);
    }*/
    
    /**
     *
     */
    @Test
    public void testdeleteCourseFromUniId()
    {
        CourseService service = new CourseService(em);
        service.deleteCourseFromUniId("inf2");
    }
    
    /**
     *
     */
    @Test
    public void testAssignCourseToTeacher()
    {
        CourseService service = new CourseService(em);
        //Course c = new Course("R","first lesson of R language","programming","inf9");
        Course c = service.createCourse("R","first lesson of R language","programming","inf9");
        TeacherService service2 = new TeacherService(em);
        List<Teacher> teachers = service2.findAllTeachers();
        service.AssignCourseToTeacher(teachers.get(0), c);
        //List<Course> courses = service.findAllCourses();
        assertNotNull(c);
        assertEquals(3, c.getTeacher().getCourses().size());
    }
    
    /*@Test
    public void testUnAssignCourseFromTeacher()
    {
        CourseService service = new CourseService(em);
        List <Course> courses = service.findCourseByTitle("java");
        boolean f = service.UnAssignCourseFromTeacher(courses.get(0));
        List<Course> courses1 = service.findCourseByTeacher("Kotidis");
        assertNotNull(courses1);
        assertEquals(0, courses1.get(0).getTeacher().getCourses().size());
        
        TeacherService service1 = new TeacherService(em);
        List<Teacher> teachers1 = service1.findTeachersByLastName("Kotidis");
        assertNotNull(teachers1);
        assertEquals(1, teachers1.get(0).getCourses().size());
    }*/

    /**
     *
     */

    
    @Test
    public void testUploadMaterial()
    {
        PersonService pr = new PersonService(em);
        //Teacher teacher = 
		pr.findPersonByCredentials("giakou@aueb.gr", "1234");
        CourseService cs = new CourseService(em);
        List<Course> courses = cs.findCourseByTeacher("Giakoumakis");
        MaterialService s = new MaterialService(em);
        boolean f = s.CreateNewMaterial("lecture8", "geia sas", courses.get(0));
        assertNotNull(f);
        assertEquals(true, f);
    }
    
    /**
     *
     */
    @Test
    public void testUploadProject()
    {
        PersonService pr = new PersonService(em);
        //Teacher teacher = (Teacher) 
		pr.findPersonByCredentials("giakou@aueb.gr", "1234");
        CourseService cs = new CourseService(em);
        List<Course> courses = cs.findCourseByTeacher("Giakoumakis");
        ProjectService p = new ProjectService(em);
        boolean f = p.CreateNewProject("project8", "geia sas", courses.get(0));
        assertNotNull(f);
        assertEquals(true, f);
    }
    
}
