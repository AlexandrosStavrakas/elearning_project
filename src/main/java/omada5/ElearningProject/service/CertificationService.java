package omada5.ElearningProject.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import omada5.ElearningProject.domain.*;
/**
 * The CertificationService Class
 * @author thegr
 */
public class CertificationService 
{
    private EntityManager em;

    /**
     * sets the entity manager for the service
     * @param em
     */
    public CertificationService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     * returns a List of all Certifications
     * @return ListCertifications
     */ 
    public List<Certification> findAllCertifications()
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Certification> listCertifications = null;
        listCertifications = em.createQuery("select c from Certification c").getResultList();

        if(listCertifications == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return listCertifications;
        }

    }
    
    /**
     * create a new Certification
     * @param course 
     * @param student
     * @return the new certificate
     */
    public Certification createCertification(Course course, Student student)
    {
        
        ProjectService p = new ProjectService(em);
        List<Project> projects = p.findProjectByCourse(course);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(projects.size()<3)
        {
            tx.rollback();
            return null;
        }
        for(int i=0; i<3; i++)
        {
            Grade grade;
            grade = (Grade) em.createQuery("select g from Grade g where g.student.person_id =:person_id and g.project.project_id =:project_id").setParameter("person_id", student.getPerson_id()).setParameter("project_id", projects.get(i).getProject_id()).getSingleResult();
            if(grade.getScore()<5)
            {
                tx.rollback();
                return null;
            }
        }
        Certification certificate = new Certification();
        certificate.setStudent(student);
        certificate.setCourse(course);
        student.addCertification(certificate);
        em.persist(certificate);
        em.merge(student);
        tx.commit();
        return certificate;
    }
    
    /**
     *
     * @param studentId
     * @return
     */
    public List<Certification> findCertificationsByStudentId(int studentId)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Certification> listCertifications = null;
        listCertifications = em.createQuery("select c from Certification c where c.student.person_id =:person_id").setParameter("person_id", studentId).getResultList();
        if(listCertifications == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return listCertifications;
        }
    }
    
    /**
     *
     * @param studentId
     * @param courseId
     * @return
     */
    public Certification findCertificationsByStudentIdandCourseId(int studentId, int courseId)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Certification certification = null;
        certification = (Certification) em.createQuery("select c from Certification c where c.student.person_id =:person_id and c.course.course_id=:course_id").setParameter("person_id", studentId).setParameter("course_id", courseId).getSingleResult();
        if(certification == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return certification;
        }
    }
}
