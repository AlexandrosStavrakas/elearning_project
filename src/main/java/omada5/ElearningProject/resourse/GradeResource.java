package omada5.ElearningProject.resourse;

import static omada5.ElearningProject.resourse.ElearningUri.GRADES;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
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
@Path(GRADES)
public class GradeResource extends AbstractResource
{
	@Context 
    UriInfo uriInfo;
    
    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<GradeInfo> listAllGrades() 
    {
        EntityManager em = getEntityManager();
        GradeService service = new GradeService(em);
        List<Grade> grades = service.findAllGrades();
        List<GradeInfo> gradeInfo = GradeInfo.wrap(grades);
        em.close();
        return gradeInfo;
    }
    
    /**
     *
     * @param gradeInfo
     * @return
     */
    @PUT
    @Path("/teachers/courses/projects/students/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setScoreProject(GradeInfo gradeInfo) 
    {
        EntityManager em = getEntityManager();
        Grade grade = gradeInfo.getGrade(em);
        GradeService gradeService = new GradeService(em);
        //grade = 
        gradeService.save(grade);
        em.close();
        return Response.ok().build();
    }
    
    /**
     *
     * @param gradeInfo
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createGrade(GradeInfo gradeInfo) 
    {
    	EntityManager em = getEntityManager();
        Grade grade = gradeInfo.getGrade(em);
        Student student = em.find(Student.class, gradeInfo.getStudentId());
        Project project = em.find(Project.class, gradeInfo.getProject());
        byte[] file = gradeInfo.getFile().getBytes(Charset.forName("UTF-8"));
        
        File fin = new File("C:\\Users\\thegr\\Documents\\GGGG.txt");

        BufferedOutputStream bs = null;
        FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(fin);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        bs = new BufferedOutputStream(fs);
        try {
			bs.write(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			bs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        bs = null;
        
        
        GradeService gradeService = new GradeService(em);
        grade = gradeService.createGrade(project, student, fin);
        if(grade != null)
        {
            UriBuilder ub = uriInfo.getAbsolutePathBuilder();
            URI newGradeUri = ub.path(Integer.toString(grade.getGrade_id())).build();
            em.close();
            return Response.created(newGradeUri).build();
        }
        else
        {
            em.close();
            return Response.status(Status.NOT_ACCEPTABLE).build();
        }
    }
}
