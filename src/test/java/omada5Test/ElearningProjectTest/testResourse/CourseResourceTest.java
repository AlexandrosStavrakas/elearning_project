package omada5Test.ElearningProjectTest.testResourse;

import static omada5.ElearningProject.resourse.ElearningUri.*;

import java.util.List;
import javax.persistence.EntityManager;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.persistence.*;
import omada5.ElearningProject.resourse.*;
import omada5.ElearningProject.service.*;

/**
 *
 * @author thegr
 */
public class CourseResourceTest extends ElearningProjectResourceTest
{
    @Override
    protected Application configure() 
    {
        return new ResourceConfig(CourseResourse.class, TeacherResource.class, StudentResource.class, ProjectResource.class, MaterialResource.class, DebugExceptionMapper.class);
    }
    
    @Test
    public void testListAllCourses() 
    {
        List<CourseInfo> courses = target(COURSES).request().get(new GenericType<List<CourseInfo>>() {});
        Assert.assertEquals(3, courses.size());
    }
    
    @Test
    public void testListCourseById() 
    {
        List<CourseInfo> courses = target(COURSES).request().get(new GenericType<List<CourseInfo>>() {});
        String firstCourseId = Integer.toString(courses.get(0).getCourse_id());
        CourseInfo course = target(courseIdUri(firstCourseId)).request().get(CourseInfo.class);
        Assert.assertNotNull(course);
        Assert.assertEquals("java", course.getTitle());
    }
    
    @Test
    public void testSearchCourseByTitle() 
    {
        //System.out.println(ElearningUri.courseSearchUri("java"));
        List<CourseInfo> courses = target(COURSE_SEARCH).queryParam("title", "java").request().get(new GenericType<List<CourseInfo>>() {});
        Assert.assertEquals(1, courses.size());
    }
    
    @Test
    public void testSearchCourseByTeacher() 
    {
        //System.out.println(ElearningUri.courseSearchTeacherUri("Giakoumakis"));
        List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", "Giakoumakis").request().get(new GenericType<List<CourseInfo>>() {});
        Assert.assertEquals(1, courses.size());
    }
    
    @Test
    public void testCreateNewCourse() 
    {
    	Uni_Admin admin = (Uni_Admin)login("stavrakasa@aueb.gr","1234");
    	List<TeacherInfo> teachers = target(TEACHERS).request().get(new GenericType<List<TeacherInfo>>() {});
        Assert.assertEquals(2, teachers.size());
        University uni = UniversityByAdmin(admin);
        CourseInfo courseInfo = new CourseInfo("Pearl", "Pearl Languange", "CS", "INF9000", teachers.get(0).getPerson_id(), uni.getUni_id());
        Response response = target(COURSES).request().post(Entity.entity(courseInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
        List<Course> foundCourses = findCoursesByTitle("Pearl");
        Assert.assertEquals(1, foundCourses.size());
    }
    
    @Test
    public void testDeleteExistingCourse() 
    {
    	Uni_Admin admin = (Uni_Admin)login("stavrakasa@aueb.gr","1234");
        List<Course> courses = findCoursesByTitle("java");
        Assert.assertEquals(1, courses.size());
        String cid = Integer.toString(courses.get(0).getCourse_id());
        String aid = Integer.toString(admin.getPerson_id());
        Response response = target(courseIdAdminId(cid, aid)).request().delete();
        Assert.assertEquals(200, response.getStatus());
        List<Course> foundCourses = findCoursesByTitle("java");
        Assert.assertEquals(0, foundCourses.size());
    }
  
    @Test
    public void testDeleteNonExistingCourse() 
    {
    	Uni_Admin admin = (Uni_Admin)login("stavrakasa@aueb.gr","1234");
        Response response = target(courseIdAdminId(Integer.toString(Integer.MAX_VALUE),Integer.toString(admin.getPerson_id()))).request().delete();
        Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
    
    @Test
    public void testUpdateTeacherToCourse() 
    {
        List<Course> courses = findCourseByTeacher("Giakoumakis");
        Assert.assertEquals(1, courses.size());
        CourseInfo courseInfo;
        courseInfo= CourseInfo.wrap(courses.get(0));
        Teacher t = findTeacherByName("Kotidis");
        courseInfo.setTeacherId(t.getPerson_id());
        // Submit the updated representation
        Response response = target(courseIdUri(Integer.toString(courseInfo.getCourse_id()))).request()
                        .put(Entity.entity(courseInfo, MediaType.APPLICATION_JSON));
        // assertion on request status and database state
        Assert.assertEquals(200, response.getStatus());
        List<Course> foundCourses =  findCourseByTeacher("Kotidis");
        Assert.assertEquals(3, foundCourses.size());
        List<Course> foundCourses1 =  findCourseByTeacher("Giakoumakis");
        Assert.assertEquals(0, foundCourses1.size());
    }
    
    @Test
    public void testRegisterStudentToCourse()
    {
    	Student student = (Student)login("giannakis@gmail.gr","password");
    	String sid = Integer.toString(student.getPerson_id());
    	StudentInfo studentInfo = target(studentIdUri(sid)).request().get(StudentInfo.class);
    	Assert.assertEquals("giannakis@gmail.gr", studentInfo.getEmail());
        List<CourseInfo> courses = target(COURSE_SEARCH).queryParam("title", "python").request().get(new GenericType<List<CourseInfo>>() {});
        int courseId = courses.get(0).getCourse_id();
        String cid = Integer.toString(courseId);
        Response response = target(register(cid, sid)).request().put(Entity.entity("", MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testUnRegisterStudentToCourse()
    {
    	Student student = (Student)login("giannakis@gmail.gr","password");
    	String sid = Integer.toString(student.getPerson_id());
    	StudentInfo studentInfo = target(studentIdUri(sid)).request().get(StudentInfo.class);
    	Assert.assertEquals("giannakis@gmail.gr", studentInfo.getEmail());
        List<CourseInfo> courses = target(COURSE_SEARCH).queryParam("title", "java").request().get(new GenericType<List<CourseInfo>>() {});
        int courseId = courses.get(0).getCourse_id();
        String cid = Integer.toString(courseId);
        Response response = target(unregister(cid, sid)).request().put(Entity.entity("", MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testCreateProjectToCourse()
    {
    	Teacher teacher = (Teacher)login("kotidis@aueb.gr","1234");
    	List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", teacher.getSurname()).request().get(new GenericType<List<CourseInfo>>() {});
    	ProjectInfo projectInfo = new ProjectInfo();
    	projectInfo.setDescription("mpla mpla mpla");
    	projectInfo.setTitle("project new project");
    	//CourseIdProjectIdUri
    	Response response = target(PROJECTS).request().post(Entity.entity(projectInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
    	String cid = Integer.toString(courses.get(1).getCourse_id());
        
    	Response response1 = target(CourseIdProjectIdUri(cid)).request().post(Entity.entity(projectInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response1.getStatus());
    }
    
    
    @Test
    public void testDeleteProjectFromCourse()
    {
    	Teacher teacher = (Teacher)login("kotidis@aueb.gr","1234");
    	List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", teacher.getSurname()).request().get(new GenericType<List<CourseInfo>>() {});
    	String cid = Integer.toString(courses.get(0).getCourse_id());
    	List<ProjectInfo> projects = target(courseProjectUri(cid)).request().get(new GenericType<List<ProjectInfo>>() {});
        Assert.assertNotNull(projects);
        Assert.assertEquals(3, projects.size());
        String pid = Integer.toString(projects.get(0).getProject_id());
        //ProjectInfo projectInfo = projects.get(0);
        Response response = target(CourseIdProjectIdUriDelete(cid, pid)).request().delete();
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testUpdateProjectToCourse()
    {
    	Teacher teacher = (Teacher)login("kotidis@aueb.gr","1234");
    	List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", teacher.getSurname()).request().get(new GenericType<List<CourseInfo>>() {});
    	String cid = Integer.toString(courses.get(0).getCourse_id());
    	List<ProjectInfo> projects = target(courseProjectUri(cid)).request().get(new GenericType<List<ProjectInfo>>() {});
        Assert.assertNotNull(projects);
        Assert.assertEquals(3, projects.size());
        String pid = Integer.toString(projects.get(0).getProject_id());
        ProjectInfo projectInfo = projects.get(0);
        projectInfo.setTitle("new title");
        projectInfo.setDescription("new description");
        Response response = target(CourseIdProjectIdUriUpdate(cid, pid)).request().put(Entity.entity(projectInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());
    }
    
    
    @Test
    public void testCreateMaterialToCourse()
    {
    	Teacher teacher = (Teacher)login("kotidis@aueb.gr","1234");
    	List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", teacher.getSurname()).request().get(new GenericType<List<CourseInfo>>() {});
    	MaterialInfo materialInfo = new MaterialInfo();
    	materialInfo.setDescription("mpla mpla mpla");
    	materialInfo.setTitle("material new project");
    	//CourseIdProjectIdUri
    	Response response = target(MATERIALS).request().post(Entity.entity(materialInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response.getStatus());
    	String cid = Integer.toString(courses.get(1).getCourse_id());
        
    	Response response1 = target(CourseIdMaterialIdUri(cid)).request().post(Entity.entity(materialInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response1.getStatus());
    }
    
    @Test
    public void testDeleteMateriaFromCourse()
    {
    	Teacher teacher = (Teacher)login("kotidis@aueb.gr","1234");
    	List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", teacher.getSurname()).request().get(new GenericType<List<CourseInfo>>() {});
    	String cid = Integer.toString(courses.get(0).getCourse_id());
    	List<MaterialInfo> materials = target(courseMaterialUri(cid)).request().get(new GenericType<List<MaterialInfo>>() {});
        Assert.assertNotNull(materials);
        Assert.assertEquals(2, materials.size());
        String mid = Integer.toString(materials.get(0).getMaterial_id());
        //ProjectInfo projectInfo = projects.get(0);
        Response response = target(CourseIdMaterialIdUriDelete(cid, mid)).request().delete();
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testUpdateMaterialToCourse()
    {
    	Teacher teacher = (Teacher)login("kotidis@aueb.gr","1234");
    	List<CourseInfo> courses = target(COURSE_SEARCH_TEACHER).queryParam("teacher", teacher.getSurname()).request().get(new GenericType<List<CourseInfo>>() {});
    	String cid = Integer.toString(courses.get(0).getCourse_id());
    	List<MaterialInfo> materials = target(courseMaterialUri(cid)).request().get(new GenericType<List<MaterialInfo>>() {});
        Assert.assertNotNull(materials);
        Assert.assertEquals(2, materials.size());
        String mid = Integer.toString(materials.get(0).getMaterial_id());
        MaterialInfo materialInfo = materials.get(0);
        materialInfo.setTitle("new title");
        materialInfo.setDescription("new description");
        Response response = target(CourseIdMaterialIdUriUpdate(cid, mid)).request().put(Entity.entity(materialInfo, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus());
    }
   
    
    /*
    
    
    @Test
    public void testListProjectsByCourseId() 
    {
        // get all books
        List<CourseInfo> courses = target(COURSES).request().get(new GenericType<List<CourseInfo>>() {});
        String firstCourseId = Integer.toString(courses.get(0).getCourse_id());
        //CourseInfo course = target(courseIdUri(firstCourseId)).request().get(CourseInfo.class);
        System.out.println(courseProjectUri(firstCourseId).toString()+ " poutanas paidia");
        List<ProjectInfo> projects = target(courseProjectUri(firstCourseId)).request().get(new GenericType<List<ProjectInfo>>() {});
        System.out.println("gamomano "+ projects.get(0).getTitle());
        Assert.assertNotNull(projects);
        Assert.assertEquals(3, projects.size());
    }




    @Test
    public void testUpdateCourse() 
    {
        // Find a book and update its title
        List<Course> courses = findCoursesByTitle("java");
        Assert.assertEquals(1, courses.size());
        CourseInfo courseInfo;
        courseInfo= CourseInfo.wrap(courses.get(0));
        courseInfo.setTitle("JAVA for beginners");
        // Submit the updated representation
        Response response = target(courseIdUri(Integer.toString(courseInfo.getCourse_id()))).request()
                        .put(Entity.entity(courseInfo, MediaType.APPLICATION_JSON));
        // assertion on request status and database state
        Assert.assertEquals(200, response.getStatus());
        List<Course> foundCourses = findCoursesByTitle("JAVA for beginners");
        Assert.assertEquals(1, foundCourses.size());
    }

   

    ////////////////////////////////////////////////////////////////////////////////
    

    @Test
    public void AdminCreatesCourseAndAssignsToTeacher()
    {
        Uni_Admin admin = (Uni_Admin)login("stavrakasa@aueb.gr","1234");
        University uni = UniversityByAdmin(admin);
        List<Teacher> teachers = listTeachers();
        
        CourseInfo courseInfo = new CourseInfo("RUBY", "RUBY Languange", "CS", "INF8000",uni.getUni_id());
        Response response = target(COURSES).request().post(Entity.entity(courseInfo, MediaType.APPLICATION_JSON));

        // Check status and database state
        Assert.assertEquals(201, response.getStatus());
        List<Course> foundCourses = findCoursesByTitle("RUBY");
        Assert.assertEquals(1, foundCourses.size());
    }
    
    
    
    @Test
    public void findCoursesByStudent()
    {
    	Student student = (Student)login("giannakis@gmail.gr","password");
    	List<CourseInfo> courses = target(COURSE_SEARCH_STUDENT).queryParam("student", student.getPerson_id()).request().get(new GenericType<List<CourseInfo>>() {});
        Assert.assertEquals(2, courses.size());
    	//List<Course> courses = findCourseByStudent(student.getEmail());
    	//Assert.assertEquals(3, courses.size());
    }

    */
}
