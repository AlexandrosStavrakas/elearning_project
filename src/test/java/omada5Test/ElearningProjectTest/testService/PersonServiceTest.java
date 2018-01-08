package omada5Test.ElearningProjectTest.testService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.PersonService;
/**
 *
 * @author thegr
 */
public class PersonServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void findAllPersonsTest()
    {
        PersonService service = new PersonService(em);
        service.findAllPersons();
    }
    
    /**
     *
     */
    @Test
    public void findPersonTeacherTest()
    {
        PersonService service = new PersonService(em);
        Person f = service.findPersonByCredentials("kotidis@aueb.gr", "1234");
        assertNotNull(f);
        assertEquals("Ioannis", f.getName());
    }
    
    /**
     *
     */
    @Test
    public void findPersonAdminTest()
    {
        PersonService service = new PersonService(em);
        Person f = service.findAdminByCredentials("stavrakasa@aueb.gr", "1234");
        assertNotNull(f);
        assertEquals("Alexandros", f.getName());
    }
}
