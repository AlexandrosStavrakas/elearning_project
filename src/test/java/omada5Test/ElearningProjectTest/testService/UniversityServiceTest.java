package omada5Test.ElearningProjectTest.testService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.UniAdminService;
import omada5.ElearningProject.service.UniversityService;

/**
 *
 * @author thegr
 */
public class UniversityServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void testFindAllUniversities() 
    {
        UniversityService service = new UniversityService(em);
        List<University> uni = service.findAllUniversities();   
        assertNotNull(uni);
        assertEquals(3, uni.size());
    }
    
    /**
     *
     */
    @Test
    public void testFindUniversityByName()
    {
        UniversityService service = new UniversityService(em);
        List<University> uni = service.findUniversityByName("ASOEE");   
        assertNotNull(uni);
        assertEquals(0, uni.size());
    }
    
    /**
     *
     */
    @Test
    public void testCreateUniversity()
    {
        UniversityService service = new UniversityService(em);
        //University uni = 
		service.createUniversity("PAPEI", "info@papei.gr");
        List<University> unis = service.findAllUniversities(); 
        assertNotNull(unis);
        assertEquals(4, unis.size());
    }
    
    /**
     *
     */
    @Test
    public void testDeleteUniversity()
    {
        UniversityService service = new UniversityService(em);
        boolean f = service.deleteUniversityFromEmail("info@aueb.gr");
        assertNotNull(f);
        assertEquals(true, f);
    }
    
    /**
     *
     */
    @Test
    public void updateUniversityTest()
    {
        UniversityService s = new UniversityService(em);
        List<University> unis = s.findUniversityByName("AUEB");
        boolean f = s.saveOrUpdateUniversity(unis.get(0).getUni_id(), "OPA", "info@opa.gr");
        assertNotNull(f);
        assertEquals(true, f);
    }
    
    /**
     *
     */
    @Test
    public void testAssignAdminToUni()
    {
        UniversityService serviceU = new UniversityService(em);
        List<University> unis = serviceU.findUniversityByName("AUEB");
        UniAdminService serviceA = new UniAdminService(em);
        Uni_Admin uni_admin = serviceA.createUniAdmin("Nel", "Kar", "mpl@fa.gr", "nopass");
        boolean f = serviceU.AssignAdminToUni(unis.get(0), uni_admin);
        assertNotNull(f);
        assertEquals(true, f);
    }
}
