package omada5Test.ElearningProjectTest.testService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.PersonService;
import omada5.ElearningProject.service.UniAdminService;

/**
 *
 * @author thegr
 */
public class UniAdminServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void testFindAllUniAdmins() 
    {
        UniAdminService service = new UniAdminService(em);
        List<Uni_Admin> uniadmins = service.findAllUniAdmins();   
        assertNotNull(uniadmins);
        assertEquals(3, uniadmins.size());
    }
    
    /**
     *
     */
    @Test
    public void testFindUniAdminsBySurname() 
    {
        UniAdminService service = new UniAdminService(em);
        List<Uni_Admin> uniadmins = service.findUniAdminsByLastName("%Stavrakas%");

        assertNotNull(uniadmins);
        assertEquals(1, uniadmins.size());
    }

    /**
     *
     */
    @Test
    public void testFindAdminById() 
    {
        UniAdminService service = new UniAdminService(em);
        List<Uni_Admin> allAdmins = service.findAllUniAdmins();
        Uni_Admin uniadmin = service.findUni_AdminById(allAdmins.get(0).getPerson_id());
        assertNotNull("Expected non null course", uniadmin);
    }
    
    /**
     *
     */
    @Test
    public void testInsertNewAdmin() 
    {
        UniAdminService service = new UniAdminService(em);
        service.createUniAdmin("Kostas","Sdrolias","sdroliask@aueb.gr","password");
        List<Uni_Admin> uniadmins = service.findAllUniAdmins();
        assertNotNull(uniadmins);
        assertEquals(4, uniadmins.size());
    }
    
    /**
     *
     */
    @Test
    public void updateAdminTest()
    {
        PersonService s = new PersonService(em);
        Uni_Admin admin = (Uni_Admin) s.findPersonByCredentials("stavrakasa@aueb.gr", "1234");
        UniAdminService service = new UniAdminService(em);
        //Uni_Admin t = 
		service.saveOrUpdateUni_Admin(admin.getPerson_id(), "Johan",null,null,null);
        List<Uni_Admin> admins = service.findUniAdminsByLastName("Stavrakas");
        assertNotNull(admins);
        assertEquals("Johan", admins.get(0).getName());
    }
    
    /**
     *
     */
    @Test
    public void deleteUniAdminTest()
    {
        UniAdminService service = new UniAdminService(em);
        boolean t = service.deleteUniAdmin("stavrakasa@aueb.gr");
        assertNotNull(t);
        assertEquals(t, true);
    }

}
