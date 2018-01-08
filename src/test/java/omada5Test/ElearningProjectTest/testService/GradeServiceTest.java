package omada5Test.ElearningProjectTest.testService;

import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.CourseService;
import omada5.ElearningProject.service.GradeService;
import omada5.ElearningProject.service.PersonService;
import omada5.ElearningProject.service.ProjectService;
/**
 *
 * @author thegr
 */
public class GradeServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     */
    @Test
    public void testFindAllProjects() 
    {
        GradeService service = new GradeService(em);
        List<Project> projects = service.findAllProjects();
        
        assertNotNull(projects);
        assertEquals(3, projects.size());
    }
    
    /**
     *
     */
    @Test
    public void uploadProject()
    {
        PersonService serviceP = new PersonService(em);
        Student s = serviceP.findStudentByCredentials("kostakis@yahoo.gr", "password");
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCoursesByStudent(s.getPerson_id());
        ProjectService servicePr = new ProjectService(em);
        List<Project> projects = servicePr.findProjectByCourse(courses.get(0));
        GradeService serviceG = new GradeService(em);
        File file = new File("C:\\Users\\thegr\\Documents\\projectTest.txt");
        Grade grade = serviceG.createGrade(projects.get(2), s, file);
        assertNotNull(grade);
        assertEquals("Kostakis", grade.getStudent().getName());
        //serviceG.uploadProjectFile(grade, file);
    }
    
    /**
     *
     * @throws IOException
     */
    @Test
    public void setGradeToProject() throws IOException
    {
        PersonService serviceP = new PersonService(em);
        Teacher t = serviceP.findTeacherByCredentials("kotidis@aueb.gr", "1234");
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCourseByTeacher(t.getSurname());
        ProjectService servicePr = new ProjectService(em);
        List<Project> projects = servicePr.findProjectByCourse(courses.get(0));
        GradeService serviceG = new GradeService(em);
        List<Grade> grades = serviceG.findGradeByProject(projects.get(1));
        serviceG.getFileForGrade(grades);
        serviceG.setScoretoGrade(grades.get(0), 8);
        assertNotNull(grades);
        //assertEquals("8", grades.get(0));
    }
}
