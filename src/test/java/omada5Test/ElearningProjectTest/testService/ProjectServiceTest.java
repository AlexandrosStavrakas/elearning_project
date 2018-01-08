package omada5Test.ElearningProjectTest.testService;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.persistence.Initializer;
import omada5.ElearningProject.service.CourseService;
import omada5.ElearningProject.service.ProjectService;
/**
 *
 * @author thegr
 */
public class ProjectServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void testFindAllProjects() 
    {
        ProjectService service = new ProjectService(em);
        List<Project> project = service.findAllProjects();
        
        assertNotNull(project);
        assertEquals(3, project.size());
    }
    
    /**
     *
     */
    @Test
    public void findCourseByProject()
    {
        ProjectService service = new ProjectService(em);
        Course course = service.findCourseByProjectTitle("pr2Java");
        
        assertNotNull(course);
        assertEquals("java", course.getTitle());
    }
    
    /**
     *
     */
    @Test
    public void testCreateNewProject()
    {
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCourseByTeacher("Kotidis");
        ProjectService serviceP = new ProjectService(em);
        boolean f = serviceP.checkProjectNumber(courses.get(1));        
        Project p = new Project();
        if(f == true)
        {
            p.setTitle("PythonProject");
            p.setDescription("mpla mpla");
            serviceP.createProject(p);
            //f = 
            serviceP.AssignProjectToCourse(p, courses.get(1));
        }
        
        Course c = serviceP.findCourseByProjectTitle("PythonProject");
                
        assertNotNull(c);
        assertEquals(1, c.getProjects().size());
    }
    
    /**
     *
     */
    @Test 
    public void updateExistingProject()
    {
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCourseByTeacher("Kotidis");
        ProjectService serviceP = new ProjectService(em);
        List<Project> projects = serviceP.findProjectByCourse(courses.get(0));
        boolean f = serviceP.saveOrUpdateProject(projects.get(2).getProject_id(), null, "this is the new Description");
        assertNotNull(f);
        assertEquals(f, true);
    }
    
    /**
     *
     * @throws IOException
     */
    @Test
    public void testDeleteProjects() throws IOException 
    {
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCourseByTeacher("Kotidis");
        ProjectService serviceM = new ProjectService(em);
        List<Project> projects = serviceM.findProjectByCourse(courses.get(0));
        boolean f = serviceM.deleteProject(projects.get(0));
        
        assertNotNull(f);
        assertEquals(true, f);
    }
}
