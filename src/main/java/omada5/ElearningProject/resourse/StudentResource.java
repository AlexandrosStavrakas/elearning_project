/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omada5.ElearningProject.resourse;

import static omada5.ElearningProject.resourse.ElearningUri.COURSES;
import static omada5.ElearningProject.resourse.ElearningUri.STUDENTS;

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

@Path(STUDENTS)
public class StudentResource extends AbstractResource
{
    @Context 
    UriInfo uriInfo;

    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentInfo> listAllStudents() 
    {
        EntityManager em = getEntityManager();
        StudentService service = new StudentService(em);
        List<Student> students = service.findAllStudents();
        List<StudentInfo> studentInfo = StudentInfo.wrap(students);
        em.close();
        return studentInfo;
    }
    
    /**
     *
     * @param studentId
     * @return
     */
    @GET
    @Path("/{studentId:[0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public StudentInfo StudentsbyEmail(@PathParam("studentId") int studentId) 
    {
        EntityManager em = getEntityManager();
        StudentService service = new StudentService(em);
        Student student = service.findStudentById(studentId);
        StudentInfo studentInfo = StudentInfo.wrap(student);
        em.close();
        return studentInfo;
    }
    
}
