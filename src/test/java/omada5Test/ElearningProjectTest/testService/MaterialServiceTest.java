/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omada5Test.ElearningProjectTest.testService;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.CourseService;
import omada5.ElearningProject.service.MaterialService;
import omada5.ElearningProject.service.PersonService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author thegr
 */
public class MaterialServiceTest extends ElearningProjectServiceTest
{

    /**
     *
     * @throws IOException
     */
    @Test
    public void testFindAllMaterials() throws IOException 
    {
        MaterialService service = new MaterialService(em);
        List<Material> material = service.findAllMaterials();
        
        assertNotNull(material);
        assertEquals(2, material.size());
    }
    
    /**
     *
     * @throws IOException
     */
    @Test
    public void testFindMaterialByCourse() throws IOException
    {
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCourseByTeacher("Kotidis");
        MaterialService serviceM = new MaterialService(em);
        List<Material> materials = serviceM.findMaterialByCourse(courses.get(0));
        assertNotNull(materials);
        assertEquals(2, materials.size());
    }
    
    /**
     *
     * @throws IOException
     */
    @Test
    public void testDeleteMaterials() throws IOException 
    {
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCourseByTeacher("Kotidis");
        MaterialService serviceM = new MaterialService(em);
        List<Material> materials = serviceM.findMaterialByCourse(courses.get(0));
        boolean f = serviceM.deleteMaterial(materials.get(0));
        
        assertNotNull(f);
        assertEquals(true, f);
    }
    
    /**
     *
     * @throws IOException
     */
    @Test 
    public void updateExistingMaterial() throws IOException
    {
        CourseService serviceC = new CourseService(em);
        List<Course> courses = serviceC.findCourseByTeacher("Kotidis");
        MaterialService serviceP = new MaterialService(em);
        List<Material> materials = serviceP.findMaterialByCourse(courses.get(0));
        boolean f = serviceP.saveOrUpdateMaterial(materials.get(0).getMat_id(), "this is the new title", null);
        assertNotNull(f);
        assertEquals(f, true);
    }
    
    /**
     *
     */
    @Test
    public void testCreateMaterialandAssignToExcistingCourse()
    {
        PersonService pr = new PersonService(em);
        //Teacher teacher = (Teacher) 
		pr.findPersonByCredentials("giakou@aueb.gr", "1234");
        CourseService cs = new CourseService(em);
        List<Course> courses = cs.findCourseByTeacher("Giakoumakis");
        MaterialService s = new MaterialService(em);
        Material m = s.CreateNewMaterial("lecture8", "geia sas");
        boolean f = s.AssignMaterialToCourse(m, courses.get(0));
        assertNotNull(f);
        assertEquals(true, f);
    }
    
}
