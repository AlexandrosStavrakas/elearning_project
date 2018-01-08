package omada5.ElearningProject.resourse;

import static omada5.ElearningProject.resourse.ElearningUri.PROJECTS;

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
import omada5.ElearningProject.resourse.*;;
/**
 *
 * @author thegr
 */

@Path(PROJECTS)
public class ProjectResource extends AbstractResource
{
    @Context 
    UriInfo uriInfo;

    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectInfo> listAllProjects() 
    {
        EntityManager em = getEntityManager();
        ProjectService service = new ProjectService(em);
        List<Project> projects= service.findAllProjects();
        List<ProjectInfo> projectInfo = ProjectInfo.wrap(projects);
        em.close();
        return projectInfo;
    }
    
    /**
     *
     * @param projectId
     * @return
     */
    @GET
    @Path("{projectId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProjectInfo getProjectDetails(@PathParam("projectId") int projectId) 
    {
        EntityManager em = getEntityManager();
        ProjectService projectService = new ProjectService(em);
        Project project = projectService.findProjectById(projectId);
        ProjectInfo projectInfo = ProjectInfo.wrap(project);
        em.close();
        return projectInfo;
    }
    
    /**
     *
     * @param projectInfo
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProject(ProjectInfo projectInfo) 
    {
        EntityManager em = getEntityManager();
        Project project = projectInfo.getProject(em);
        ProjectService projectService = new ProjectService(em);
        project = projectService.save(project);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI newProjectUri = ub.path(Integer.toString(project.getProject_id())).build();
        em.close();
        return Response.created(newProjectUri).build();
    }
}