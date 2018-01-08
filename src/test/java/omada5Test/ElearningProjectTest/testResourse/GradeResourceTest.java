package omada5Test.ElearningProjectTest.testResourse;

import static omada5.ElearningProject.resourse.ElearningUri.*;

import java.io.File;
import java.util.Arrays;
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
public class GradeResourceTest extends ElearningProjectResourceTest 
{
	@Override
    protected Application configure() 
    {
        return new ResourceConfig(GradeResource.class, CourseResourse.class, DebugExceptionMapper.class);
    }
    
    @Test
    public void testListAllGrades() 
    {
        List<GradeInfo> grades = target(GRADES).request().get(new GenericType<List<GradeInfo>>() {});
        //System.out.println(grades.size());
        Assert.assertEquals(6, grades.size());
    }
    
    @Test
    public void testSetGradeToProject()
    {
    	Teacher teacher = (Teacher)login("kotidis@aueb.gr","1234");
    	List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", teacher.getSurname()).request().get(new GenericType<List<CourseInfo>>() {});
    	Assert.assertEquals(2, courses.size());
    	String cid = Integer.toString(courses.get(0).getCourse_id());
        List<ProjectInfo> projects = target(courseProjectUri(cid)).request().get(new GenericType<List<ProjectInfo>>() {});
        Assert.assertNotNull(projects);
        Assert.assertEquals(3, projects.size());
        String pid = Integer.toString(projects.get(0).getProject_id());
        List<GradeInfo> grades = target(courseProjectsGrades(cid, pid)).request().get(new GenericType<List<GradeInfo>>() {});
        Assert.assertEquals(2, grades.size());
        GradeInfo gradeInfo = grades.get(1);
        //System.out.println("project old value " + gradeInfo.getScore());
        Assert.assertEquals(6.0, gradeInfo.getScore(), 1e-15);
        gradeInfo.setScore(5);
        Response response = target(SET_GRADE_TO_PROJECT).request().put(Entity.entity(gradeInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals(5.0, gradeInfo.getScore(), 1e-15);
    }
    
    @Test
    public void testCreateNewGrade() 
    {
        Student student = (Student)login("giannakis@gmail.gr","password");
        List<CourseInfo> courses = target(COURSE_SEARCH).queryParam("title", "java").request().get(new GenericType<List<CourseInfo>>() {});
        int sid = student.getPerson_id();
        String Strfile;
        
        byte[] bFile;
        FileController f = new FileController();
        
        File file = new File("C:\\Users\\thegr\\Documents\\project.txt");
        bFile = f.FileUpolader(file);
        
        Strfile = Arrays.toString(bFile);
        
        String courseId = Integer.toString(courses.get(0).getCourse_id());
        List<ProjectInfo> projects = target(courseProjectUri(courseId)).request().get(new GenericType<List<ProjectInfo>>() {});
        Assert.assertNotNull(projects);
        Assert.assertEquals(3, projects.size());
        int pid = projects.get(0).getProject_id();
        GradeInfo gradeInfo = new GradeInfo();
        gradeInfo.setProjectId(pid);
        gradeInfo.setStudent(sid);
        gradeInfo.setFile(Strfile);
        Response response = target(GRADES).request().post(Entity.entity(gradeInfo, MediaType.APPLICATION_XML));
        Assert.assertEquals(201, response.getStatus());
    }

}
