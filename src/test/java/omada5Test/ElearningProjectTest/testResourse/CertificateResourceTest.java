package omada5Test.ElearningProjectTest.testResourse;

import static omada5.ElearningProject.resourse.ElearningUri.*;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.resourse.*;

/**
 *
 * @author thegr
 */
public class CertificateResourceTest extends ElearningProjectResourceTest
{
    @Override
    protected Application configure() 
    {
        return new ResourceConfig(CertificateResource.class, CourseResourse.class, DebugExceptionMapper.class);
    }
    
    @Test
    public void testListAllCertifications() 
    {
        List<CertificateInfo> certifications = target(CERTIFICATES).request().get(new GenericType<List<CertificateInfo>>() {});
        Assert.assertEquals(1, certifications.size());
    }
    
    @Test
    public void testListCertificationsByLoggedInStudent()
    {
        Student student = (Student)login("kostakis@yahoo.gr","password");
        List<CertificateInfo> certifications = target(certificateByStudentIdUri(Integer.toString(student.getPerson_id()))).request().get(new GenericType<List<CertificateInfo>>() {});
        Assert.assertNotNull(certifications);
        Assert.assertEquals(1, certifications.size());
    }
    
    @Test
    public void testCertificationByLoggedInStudentforCourse()
    {
        Student student = (Student)login("kostakis@yahoo.gr","password");
        List<CourseInfo> courses = target(courseSearchStudentUri(Integer.toString(student.getPerson_id()))).request().get(new GenericType<List<CourseInfo>>() {});
        String cid = Integer.toString(courses.get(0).getCourse_id());
        String sid = Integer.toString(student.getPerson_id());
        CertificateInfo certification = (CertificateInfo) target(certificateByStudentIdAndCourseIdUri(sid, cid)).request().get(new GenericType<List<CertificateInfo>>() {});
        Assert.assertNotNull(certification);
        Assert.assertEquals(student.getPerson_id(), certification.getStudentId());
    }
    
    @Test
    public void testCreateNewCertification() 
    {
        Student student = (Student)login("giannakis@gmail.gr","password");
        List<CourseInfo> courses = target(COURSE_SEARCH).queryParam("title", "java").request().get(new GenericType<List<CourseInfo>>() {});
        int sid = student.getPerson_id();
        int cid = courses.get(0).getCourse_id();
        CertificateInfo certificateInfo = new CertificateInfo();
        certificateInfo.setCourseId(cid);
        certificateInfo.setStudentId(sid);
        Response response = target(CERTIFICATES).request().post(Entity.entity(certificateInfo, MediaType.APPLICATION_XML));
        Assert.assertEquals(201, response.getStatus());
    }
}
