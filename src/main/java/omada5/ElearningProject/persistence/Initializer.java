package omada5.ElearningProject.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.File;

import omada5.ElearningProject.domain.*;

/**
 * The initializing class
 * @author thegr
 */
public class Initializer  
{
    /**
     * Remove all data from database.
     * The functionality must be executed within the bounds of a transaction
     */
    public void eraseData() 
    {
        EntityManager em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query;
        
        query = em.createNativeQuery("delete from Student_Course");
        query.executeUpdate();
                    
        query = em.createNativeQuery("delete from Certifications");
        query.executeUpdate();
        
        query = em.createNativeQuery("delete from Grades");
        query.executeUpdate();
        
        query = em.createNativeQuery("delete from Materials");
        query.executeUpdate();
        
        query = em.createNativeQuery("delete from Projects");
        query.executeUpdate();
        
        query = em.createNativeQuery("delete from Courses");
        query.executeUpdate();
        
        query = em.createNativeQuery("delete from Universities");
        query.executeUpdate();
                        
        query = em.createNativeQuery("delete from Persons");
        query.executeUpdate();

        query = em.createNativeQuery("ALTER SEQUENCE hibernate_sequence RESTART WITH 1");
        query.executeUpdate();
        
        tx.commit();   
    }
    
    /**
     * prepares the default data
     * to install in the 
     * h2 database
     */
    public void prepareData() 
    {
        eraseData();                      
        
        University aueb = new University("AUEB", "info@aueb.gr");
        University uoa = new University("UOA", "info@uoa.gr");
        University uom = new University("UOM", "info@uom.gr");
        
        Uni_Admin admin_aueb = new Uni_Admin("Alexandros", "Stavrakas", "stavrakasa@aueb.gr", "1234");
        Uni_Admin admin_uoa = new Uni_Admin("Danai","Dimara","dimarad@uoa.gr","1234");
        Uni_Admin admin_uom = new Uni_Admin("Nektarios","Karatzas","karatzasn@uom.gr","1234");
        
        //Teacher Kotidis = new Teacher("Ioannis","Kotidis","kotidis@aueb.gr","1234");
        Teacher Kotidis = new Teacher();
        Kotidis.setName("Ioannis");
        Kotidis.setSurname("Kotidis");
        Kotidis.setEmail("kotidis@aueb.gr");
        Kotidis.setPassword("1234");
        Teacher Giakoumakis = new Teacher("Manolis","Giakoumakis","giakou@aueb.gr","1234");
        
        Course Java = new Course();
        Course Python = new Course();
        Course Cpp = new Course();
        
        Student Giannakis = new Student();
        Student Kostakis = new Student();
        
        aueb.addCourses(Java);
        Java.setUniversity(aueb);
        
        aueb.addCourses(Python);
        Python.setUniversity(aueb);
        
        Cpp.setUniversity(uoa);
        uoa.addCourses(Cpp);
        
        aueb.setUni_Admin(admin_aueb);
        
        Java.setTitle("java");
        Java.setDescription("the first course for java");
        Java.setField("programming");
        Java.setUniCourseId("inf1");
        Java.setTeacher(Kotidis);
        Kotidis.addCourse(Java);
        
        Python.setTitle("python");
        Python.setDescription("the first course for python");
        Python.setField("programming");
        Python.setUniCourseId("inf2");
        Python.setTeacher(Kotidis);
        Kotidis.addCourse(Python);
        
        Cpp.setTitle("cpp");
        Cpp.setDescription("the first course for cpp");
        Cpp.setField("programming");
        Cpp.setUniCourseId("inf3");
        Cpp.setTeacher(Giakoumakis);
        Giakoumakis.addCourse(Cpp);
        
        Giannakis.setName("Giannakis");
        Giannakis.setSurname("Papardelis");
        Giannakis.setEmail("giannakis@gmail.gr");
        Giannakis.setPassword("password");
                
        Kostakis.setName("Kostakis");
        Kostakis.setSurname("Monarxidos");
        Kostakis.setEmail("kostakis@yahoo.gr");
        Kostakis.setPassword("password");
        
        Material lecture1Java = new Material("lec1", "first lecture of Java");
        Material lecture2Java = new Material("lec2", "second lecture of Java");
        
        Project project1Java1 = new Project("pr1Java", "write the task here");
        Project project2Java1 = new Project("pr2Java", "write the task here");
        Project project3Java1 = new Project("pr3Java", "write the task here");
        
        Java.addProject(project1Java1);
        Java.addProject(project2Java1);
        Java.addProject(project3Java1);
        
        project1Java1.setCourse(Java);
        project2Java1.setCourse(Java);
        project3Java1.setCourse(Java);
        
        Java.addMaterial(lecture1Java);
        Java.addMaterial(lecture2Java);
        
        lecture1Java.setCourse(Java);
        lecture2Java.setCourse(Java);
        
        byte[] bFile;
        FileController f = new FileController();
        File file = new File("C:\\Users\\thegr\\Documents\\test1.txt");
        bFile = f.FileUpolader(file);
        lecture1Java.setFile(bFile);
        
        file = new File("C:\\Users\\thegr\\Documents\\test2.txt");
        bFile = f.FileUpolader(file);
        lecture2Java.setFile(bFile); 
        
        Kostakis.addCourses(Java);
        Kostakis.addCourses(Cpp);
        Kostakis.addCourses(Python);
        Giannakis.addCourses(Java);
        Giannakis.addCourses(Cpp);
        
        
        file = new File("C:\\Users\\thegr\\Documents\\project.txt");
        bFile = f.FileUpolader(file);
        
        
        Grade grade1 = new Grade();
        grade1.setProject(project1Java1);
        grade1.setStudent(Kostakis);
        grade1.setFile(bFile);
        Kostakis.addGrade(grade1);
        grade1.setScore(6);
        
        Grade grade2 = new Grade();
        grade2.setProject(project1Java1);
        grade2.setStudent(Giannakis);
        grade2.setFile(bFile);
        Giannakis.addGrade(grade2);
        grade2.setScore(8);
        
        Grade grade3 = new Grade();
        grade3.setProject(project2Java1);
        grade3.setStudent(Kostakis);
        grade3.setFile(bFile);
        Kostakis.addGrade(grade3);
        grade3.setScore(10);
        
        Grade grade4 = new Grade();
        grade4.setProject(project3Java1);
        grade4.setStudent(Kostakis);
        grade4.setFile(bFile);
        Kostakis.addGrade(grade4);
        grade4.setScore(6);
        
        Grade grade13 = new Grade();
        grade13.setProject(project2Java1);
        grade13.setStudent(Giannakis);
        grade13.setFile(bFile);
        Giannakis.addGrade(grade13);
        grade13.setScore(10);
        
        Grade grade14 = new Grade();
        grade14.setProject(project3Java1);
        grade14.setStudent(Giannakis);
        grade4.setFile(bFile);
        Giannakis.addGrade(grade14);
        grade14.setScore(6);
        
        Certification cer1 = new Certification();
        cer1.setCourse(Java);
        cer1.setStudent(Kostakis);
        Kostakis.addCertification(cer1);
        
        
///////////////////////////////////////////////////////////////////////////////////////

        EntityManager em = JPAUtil.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        em.persist(aueb);
        em.persist(uoa);
        em.persist(uom);
        
        em.persist(admin_aueb);
        em.persist(admin_uoa);
        em.persist(admin_uom);
                
        em.persist(Kotidis);
        em.persist(Giakoumakis);

        em.persist(Java);
        em.persist(Python);
        em.persist(Cpp);

        em.persist(lecture1Java);
        em.persist(lecture2Java);
        
        em.persist(project1Java1);
        em.persist(project2Java1);
        em.persist(project3Java1);
        
        em.persist(Kostakis);
        em.persist(Giannakis);
        
        em.persist(grade1);
        em.persist(grade2);
        em.persist(grade3);
        em.persist(grade4);
        em.persist(grade13);
        em.persist(grade14);
        
        em.persist(cer1);
        
        tx.commit();
        em.close();

    }
}
