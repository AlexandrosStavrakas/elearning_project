package omada5Test.ElearningProjectTest.testService;

import java.util.List;
import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.CourseService;
import omada5.ElearningProject.service.PersonService;
import omada5.ElearningProject.service.StudentService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author thegr
 */
public class StudentServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void testInsertNewStudent()
    {
        StudentService service = new StudentService(em);
        //Student student = 
		service.createStudent("Teo","Apostolopoulos","apostolo@aueb.gr","pass");
        List<Student> students = service.findAllStudents();
        assertNotNull(students);
        assertEquals(3, students.size());
    }

    /**
     *
     */
    @Test
    public void testRegisterCourse() 
    {
        CourseService service1 = new CourseService(em);
        List<Course> courses = service1.findCourseByTitle("cpp");
        StudentService service2 = new StudentService(em);
        Student student = service2.findStudentByEmail("giannakis@gmail.gr");
        boolean f = service2.RegisterCourse(student, courses.get(0));
        assertNotNull(f);
        assertEquals(false, f);
    }
    
    /**
     *
     */
    @Test
    public void testUnregisterCourse() 
    {
        CourseService service1 = new CourseService(em);
        List<Course> courses = service1.findCourseByTitle("java");
        StudentService service2 = new StudentService(em);
        Student student = service2.findStudentByEmail("giannakis@gmail.gr");
        boolean f = service2.UnregisterCourse(student, courses.get(0));
        assertNotNull(f);
        assertEquals(true, f);
    }
    
    /**
     *
     */
    @Test
    public void findStudentByEmailTest()
    {
        StudentService service = new StudentService(em);
        Student student = service.findStudentByEmail("kostakis@yahoo.gr");
        assertNotNull(student);
        assertEquals(student.getName(),"Kostakis");
    }
    
    /**
     *
     */
    @Test
    public void updateStudentTest()
    {
        PersonService person = new PersonService(em);
        Student student = (Student) person.findPersonByCredentials("kostakis@yahoo.gr", "password");
        StudentService service = new StudentService(em);
        //Student s = 
		service.saveOrUpdateStudent(student.getPerson_id(), "Johan",null,null,null);
        Student students = service.findStudentByEmail("kostakis@yahoo.gr");
        assertNotNull(students);
        assertEquals("Johan", students.getName());
    }
    
    /**
     *
     */
    @Test
    public void testDeleteStudent()
    {
        StudentService service = new StudentService(em);
        //boolean f = 
        service.deleteStudent("kostakis@yahoo.gr");
        List<Student> s = service.findAllStudents();
        assertNotNull(s);
        assertEquals(0, s.size());
    }
}
