package omada5Test.ElearningProjectTest.testService;

import java.util.List;
import org.junit.Test;
import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author thegr
 */
public class CertificationServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void testFindAllCertifications() 
    {
        CertificationService service = new CertificationService(em);
        List<Certification> certifications = service.findAllCertifications();
        assertNotNull(certifications);
        assertEquals(1, certifications.size());
    }
    
    /**
     *
     */
    @Test
    public void testCreateCertification() 
    {
        PersonService serviceP = new PersonService(em);
        Person person = serviceP.findPersonByCredentials("kostakis@yahoo.gr", "password");
        CourseService serviceC = new CourseService(em);
        List<Course> courses1 = serviceC.findCourseByTitle("java");
        Course c = courses1.get(0);
        CertificationService service = new CertificationService(em);
        Certification certification = service.createCertification(c, (Student) person);
        assertNotNull(certification);
        assertEquals("Kostakis", certification.getStudent().getName());
        assertEquals("java", certification.getCourse().getTitle());
    }
}
