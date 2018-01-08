package omada5.ElearningProject.resourse;

import static omada5.ElearningProject.resourse.ElearningUri.CERTIFICATES;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.*;

/**
 *
 * @author thegr
 */
@Path(CERTIFICATES)
public class CertificateResource extends AbstractResource
{
    @Context 
    UriInfo uriInfo;
    
    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CertificateInfo> listAllCertificates() 
    {
        EntityManager em = getEntityManager();
        CertificationService service = new CertificationService(em);
        List<Certification> certifications = service.findAllCertifications();
        List<CertificateInfo> certificateInfo = CertificateInfo.wrap(certifications);
        em.close();
        return certificateInfo;
    }
    
    /**
     *
     * @param studentId
     * @return
     */
    @GET
    @Path("/students/{studentId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CertificateInfo> getAllStudentsCertifications(@PathParam("studentId") int studentId) 
    {
        EntityManager em = getEntityManager();
        CertificationService service = new CertificationService(em);
        List<Certification> certifications = service.findCertificationsByStudentId(studentId);
        List<CertificateInfo> certificateInfo = CertificateInfo.wrap(certifications);
        em.close();
        return certificateInfo;
    }
    
    /**
     *
     * @param studentId
     * @param courseId
     * @return
     */
    @GET
    @Path("/students/{studentId:[0-9]*}/courses/{courseId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public CertificateInfo getStudentCertificationsByCourse(@PathParam("studentId") int studentId, @PathParam("courseId") int courseId) 
    {
        EntityManager em = getEntityManager();
        CertificationService service = new CertificationService(em);
        Certification certification = service.findCertificationsByStudentIdandCourseId(studentId, courseId);
        CertificateInfo certificateInfo = CertificateInfo.wrap(certification);
        em.close();
        return certificateInfo;
    }
    
    /**
     *
     * @param certificateInfo
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createCertification(CertificateInfo certificateInfo) 
    {
        EntityManager em = getEntityManager();
        Certification certificate = certificateInfo.getCertification(em);
        Student student = em.find(Student.class, certificate.getStudent().getPerson_id());
        Course course = em.find(Course.class, certificate.getCourse().getCourse_id());
        CertificationService certificationService = new CertificationService(em);
        certificate = certificationService.createCertification(course, student);
        if(certificate != null)
        {
            UriBuilder ub = uriInfo.getAbsolutePathBuilder();
            URI newCertificateUri = ub.path(Integer.toString(certificate.getCert_id())).build();
            em.close();
            return Response.created(newCertificateUri).build();
        }
        else
        {
            em.close();
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
    }
}
