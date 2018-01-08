package omada5Test.ElearningProjectTest.testService;

import java.util.List;
import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.PersonService;
import omada5.ElearningProject.service.TeacherService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
/**
 *
 * @author thegr
 */
public class TeacherServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void testInsertExistingTeacher() 
    {
        TeacherService service = new TeacherService(em);
        //Teacher teacher = 
		service.createTeacher("Ioannis","Kotidis","kotidis@aueb.gr","pass");
        List<Teacher> teachers = service.findAllTeachers();
        assertNotNull(teachers);
        assertEquals(2, teachers.size());
    }
    
    /**
     *
     */
    @Test
    public void testInsertNewTeacher()
    {
        TeacherService service = new TeacherService(em);
        //Teacher teacher = 
		service.createTeacher("Teo","Apostolopoulos","apostolo@aueb.gr","pass");
        List<Teacher> teachers = service.findAllTeachers();
        assertNotNull(teachers);
        assertEquals(3, teachers.size());
    }
    
    /**
     *
     */
    @Test
    public void testInsertNewTeacherAnotherConstructor()
    {
        TeacherService service = new TeacherService(em);
        Teacher teacher = new Teacher("Teo","Apostolopoulos","apostolo@aueb.gr","pass"); 
        service.createTeacher(teacher);
        List<Teacher> teachers = service.findAllTeachers();
        assertNotNull(teachers);
        assertEquals(3, teachers.size());
    }
    
    /**
     *
     */
    @Test
    public void findTeacherByNameTest()
    {
        TeacherService service = new TeacherService(em);
        List<Teacher> results = service.findTeachersByLastName("Kotidis");
        assertNotNull(results);
        assertEquals(results.get(0).getSurname(),"Kotidis");
    }
    
    /**
     *
     */
    @Test
    public void updateTeacherTest()
    {
        PersonService s = new PersonService(em);
        Teacher teacher = (Teacher) s.findPersonByCredentials("kotidis@aueb.gr", "1234");       
        TeacherService service = new TeacherService(em);
        //Teacher t = 
		service.saveOrUpdateTeacher(teacher.getPerson_id(), "Johan",null,null,null);
        List<Teacher> teachers = service.findTeachersByLastName("Kotidis");
        assertNotNull(teachers);
        assertEquals("Johan", teachers.get(0).getName());
    }
    
    /**
     *
     */
    @Test
    public void deleteTeacherTest()
    {
        TeacherService service = new TeacherService(em);
        //boolean t = 
		service.deleteTeacher("kotidis@aueb.gr");
        List<Teacher> teachers = service.findAllTeachers();
        assertNotNull(teachers);
        assertEquals(1, teachers.size());
    }
}
