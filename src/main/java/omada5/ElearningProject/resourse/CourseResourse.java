package omada5.ElearningProject.resourse;

import static omada5.ElearningProject.resourse.ElearningUri.COURSES;

import java.io.FileNotFoundException;
import java.io.IOException;
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

@Path(COURSES)
public class CourseResourse extends AbstractResource
{
    @Context 
    UriInfo uriInfo;

    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseInfo> listAllCourses() 
    {
        EntityManager em = getEntityManager();
        CourseService service = new CourseService(em);
        List<Course> courses = service.findAllCourses();
        List<CourseInfo> courseInfo = CourseInfo.wrap(courses);
        em.close();
        return courseInfo;
    }
    
    /**
     *
     * @param courseId
     * @return
     */
    @GET
    @Path("{courseId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public CourseInfo getCourseDetails(@PathParam("courseId") int courseId) 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        Course course = courseService.findCourseById(courseId);
        CourseInfo courseInfo = CourseInfo.wrap(course);
        em.close();
        return courseInfo;
    }
    
    /**
     *
     * @param title
     * @return
     */
    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseInfo> searchCourseByTitle(@QueryParam("title") String title) 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        List<Course> courses = courseService.findCourseByTitle(title);
        List<CourseInfo> coursesInfo = CourseInfo.wrap(courses);
        em.close();
        return coursesInfo;
    }
    
    /**
     *
     * @param studentId
     * @return
     */
    @GET
    @Path("/students/{studentId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseInfo> listAllCoursesByStudent(@PathParam("studentId") int studentId)
    {
        EntityManager em = getEntityManager();
        CourseService service = new CourseService(em);
        List<Course> courses = service.findCoursesByStudent(studentId);
        List<CourseInfo> courseInfo = CourseInfo.wrap(courses);
        em.close();
        return courseInfo;
    }
    
    /**
     *
     * @param teacherSurname
     * @return
     */
    @GET
    @Path("searchTeacher")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseInfo> searchCourseByTeacher(@QueryParam("teacher") String teacherSurname) 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        List<Course> courses = courseService.findCourseByTeacher(teacherSurname);
        List<CourseInfo> coursesInfo = CourseInfo.wrap(courses);
        em.close();
        return coursesInfo;
    }
//////////////////////////////////////admin///////////////////////////

    /**
     *
     * @param courseInfo
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCourse(CourseInfo courseInfo) 
    {
        EntityManager em = getEntityManager();
        Course course = courseInfo.getCourse(em);
        CourseService courseService = new CourseService(em);
        course = courseService.save(course);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI newCourseUri = ub.path(Integer.toString(course.getCourse_id())).build();
        em.close();
        return Response.created(newCourseUri).build();
    }

    /**
     *
     * @param courseId
     * @param adminId
     * @return
     */
    @DELETE
    @Path("/{courseId:[0-9]*}/admins/{adminId:[0-9]*}/")
    public Response deleteCourse(@PathParam("courseId") int courseId , @PathParam("adminId") int adminId ) 
    {
    	EntityManager em = getEntityManager();
        CourseService service = new CourseService(em);
        boolean result = service.deleteCourse(courseId);
        if (!result) 
        {
            em.close();
            return Response.status(Status.NOT_FOUND).build();
        }
        em.close();
        return Response.ok().build();
    }

///////////////////////////////////////////teacher

    /**
     *
     * @param courseInfo
     * @return
     */
    @PUT
    @Path("{courseId:[0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourse(CourseInfo courseInfo) 
    {
        EntityManager em = getEntityManager();
        Course course = courseInfo.getCourse(em);
        CourseService courseService = new CourseService(em);
        //course = 
        courseService.save(course);
        em.close();
        return Response.ok().build();
    }

    /**
     *
     * @param courseId
     * @return
     */
    @GET
    @Path("/{courseId:[0-9]*}/projects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectInfo> getAllProjectSByCourse(@PathParam("courseId") int courseId) 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        Course course = courseService.findCourseById(courseId);
        ProjectService projectService = new ProjectService(em);
        List<Project> projects = projectService.findProjectByCourse(course);
        List<ProjectInfo> projectInfo = ProjectInfo.wrap(projects);
        em.close();
        return projectInfo;
    }
    
    /**
     *
     * @param courseId
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @GET
    @Path("/{courseId:[0-9]*}/materials")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MaterialInfo> getAllMaterialsSByCourse(@PathParam("courseId") int courseId) throws FileNotFoundException, IOException 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        Course course = courseService.findCourseById(courseId);
        MaterialService materialService = new MaterialService(em);
        List<Material> materials = materialService.findMaterialByCourse(course);
        List<MaterialInfo> materialInfo = MaterialInfo.wrap(materials);
        em.close();
        return materialInfo;
    }
    
    /**
     *
     * @param courseId
     * @param projectId
     * @return
     */
    @GET
    @Path("/{courseId:[0-9]*}/projects/{projectId:[0-9]*}/students")
    @Produces(MediaType.APPLICATION_JSON)
    public List<GradeInfo> getProjectDetailsByCourse(@PathParam("courseId") int courseId , @PathParam("projectId") int projectId) 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        Course course = courseService.findCourseById(courseId);
        ProjectService projectService = new ProjectService(em);
        List<Project> projects = projectService.findProjectByCourse(course);
        GradeService gradeService = new GradeService(em);
        int found = -1;
        for(int i = 0; i< projects.size(); i++)
        {
        	if (projects.get(i).getProject_id() == projectId)
        		found =i;
        }
        List<Grade> grades = gradeService.findGradeByProject(projects.get(found));
        List<GradeInfo> gradesInfo = GradeInfo.wrap(grades);
        em.close();                
        return gradesInfo;
    }
    
    /**
     *
     * @param courseId
     * @param projectInfo
     * @return
     */
    @POST
    @Path("/{courseId:[0-9]*}/projects/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProject(@PathParam("courseId") int courseId, ProjectInfo projectInfo) 
    {
        EntityManager em = getEntityManager();
        Course course;
        CourseService courseService = new CourseService(em);
        course = courseService.findCourseById(courseId);
        Project project = projectInfo.getProject(em);
        ProjectService projectService = new ProjectService(em);
        projectService.createProject(project);
        projectService.AssignProjectToCourse(project, course);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI newProjectUri = ub.path(Integer.toString(project.getProject_id())).build();
        //System.out.println(newProjectUri);
        em.close();
        return Response.created(newProjectUri).build();
    }
    
    /**
     *
     * @param courseId
     * @param projectId
     * @param projectInfo
     * @return
     */
    @PUT
    @Path("/{courseId:[0-9]*}/projects/{projectId:[0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(@PathParam("courseId") int courseId, @PathParam("projectId") int projectId, ProjectInfo projectInfo) 
    {
        EntityManager em = getEntityManager();
        Project project = projectInfo.getProject(em);
        ProjectService projectService = new ProjectService(em);
        //project = 
		projectService.save(project);
        em.close();
        return Response.ok().build();
    }

    /**
     *
     * @param courseId
     * @param projectId
     * @return
     */
    @DELETE
    @Path("/{courseId:[0-9]*}/projects/{projectId:[0-9]*}/delete")
    public Response deleteProject(@PathParam("courseId") int courseId , @PathParam("projectId") int projectId ) 
    {
    	EntityManager em = getEntityManager();
        ProjectService service = new ProjectService(em);
        Project project = service.findProjectById(projectId);
        boolean result = service.deleteProject(project);
        if (!result) 
        {
            em.close();
            return Response.status(Status.NOT_FOUND).build();
        }
        em.close();
        return Response.ok().build();
    }

    /**
     *
     * @param courseId
     * @param materialInfo
     * @return
     */
    @POST
    @Path("/{courseId:[0-9]*}/materials/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMaterial(@PathParam("courseId") int courseId, MaterialInfo materialInfo) 
    {
        EntityManager em = getEntityManager();
        Course course;
        CourseService courseService = new CourseService(em);
        course = courseService.findCourseById(courseId);
        Material material = materialInfo.getMaterial(em);
        MaterialService materialService = new MaterialService(em);
        materialService.createMaterial(material);
        materialService.AssignMaterialToCourse(material, course);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI newMaterialUri = ub.path(Integer.toString(material.getMat_id())).build();
        //System.out.println(newMaterialUri);
        em.close();
        return Response.created(newMaterialUri).build();
    }
    
    /**
     *
     * @param courseId
     * @param materialId
     * @return
     */
    @DELETE
    @Path("/{courseId:[0-9]*}/materials/{materialId:[0-9]*}/delete")
    public Response deleteMaterial(@PathParam("courseId") int courseId , @PathParam("materialId") int materialId ) 
    {
    	EntityManager em = getEntityManager();
        MaterialService service = new MaterialService(em);
        Material material = service.findMaterialById(materialId);
        boolean result = service.deleteMaterial(material);
        if (!result) 
        {
            em.close();
            return Response.status(Status.NOT_FOUND).build();
        }
        em.close();
        return Response.ok().build();
    }
    
    /**
     *
     * @param courseId
     * @param materialId
     * @param materialInfo
     * @return
     */
    @PUT
    @Path("/{courseId:[0-9]*}/materials/{materialId:[0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProject(@PathParam("courseId") int courseId, @PathParam("materialId") int materialId, MaterialInfo materialInfo) 
    {
        EntityManager em = getEntityManager();
        Material material = materialInfo.getMaterial(em);
        MaterialService materialService = new MaterialService(em);
        //material = 
		materialService.save(material);
        em.close();
        return Response.ok().build();
    }
    /////////////////////////////student
    
    /**
     *
     * @param courseId
     * @param studentId
     * @return
     */
    @PUT
    @Path("/{courseId:[0-9]*}/students/{studentId:[0-9]*}/register/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCourse(@PathParam("courseId") int courseId , @PathParam("studentId") int studentId) 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        Course course = courseService.findCourseById(courseId);
        StudentService studentService = new StudentService(em);
        Student student = studentService.findStudentById(studentId);
        boolean register = studentService.RegisterCourse(student, course);
        em.close();
        if(register)
        	return Response.ok().build();
        else
        	return Response.status(Status.NOT_ACCEPTABLE).build();
    }
    
    /**
     *
     * @param courseId
     * @param studentId
     * @return
     */
    @PUT
    @Path("/{courseId:[0-9]*}/students/{studentId:[0-9]*}/unregister/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response unregisterCourse(@PathParam("courseId") int courseId , @PathParam("studentId") int studentId) 
    {
        EntityManager em = getEntityManager();
        CourseService courseService = new CourseService(em);
        Course course = courseService.findCourseById(courseId);
        StudentService studentService = new StudentService(em);
        Student student = studentService.findStudentById(studentId);
        boolean register = studentService.UnregisterCourse(student, course);
        em.close();
        if(register)
        	return Response.ok().build();
        else
        	return Response.status(Status.NOT_ACCEPTABLE).build();
    }


   
    /*    
    @GET
    @Path("searchStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CourseInfo> listCoursesByStudent(@QueryParam("student") int sid)
    {
        EntityManager em = getEntityManager();
        CourseService service = new CourseService(em);
        List<Course> courses = service.findCoursesByStudent(sid);
        List<CourseInfo> courseInfo = CourseInfo.wrap(courses);
        em.close();
        return courseInfo;
    }


    
    */
}
