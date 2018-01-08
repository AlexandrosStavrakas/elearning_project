package omada5.ElearningProject.resourse;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import omada5.ElearningProject.domain.*;

/**
 *
 * @author thegr
 */
@XmlRootElement
public class CertificateInfo 
{
    private int cert_id;

    private int student_id;

    private int course_id;
    
    /**
     *
     */
    public CertificateInfo(){}
    
    /**
     *
     * @param cert_id
     * @param student_id
     * @param course_id
     */
    public CertificateInfo(int cert_id, int student_id, int course_id) 
    {
        this(student_id, course_id);
        this.cert_id = cert_id;

    }

    /**
     *
     * @param student_id
     * @param course_id
     */
    public CertificateInfo(int student_id, int course_id) 
    {
        //super();
        this.student_id = student_id;            
        this.course_id = course_id;
    }
    
    /**
     *
     * @param c
     */
    public CertificateInfo(Certification c)
    {
        cert_id = c.getCert_id();
        student_id = c.getStudent().getPerson_id();
        course_id = c.getCourse().getCourse_id();
    }
        
    /**
     *
     * @return
     */
    public int getCert_id() 
    {
        return this.cert_id;
    }
    
    /**
     *
     * @param cert_id
     */
    public void setCert_id(int cert_id)
    {
    	this.cert_id = cert_id;
    }
   
    /**
     *
     * @return
     */
    public int getStudentId() 
    {
        return this.student_id;
    }
  
    /**
     *
     * @param student_id
     */
    public void setStudentId(int student_id) 
    {
        this.student_id = student_id;
    }
    
    /**
     *
     * @return
     */
    public int getCourseId() 
    {
        return this.course_id;
    }

    /**
     *
     * @param course_id
     */
    public void setCourseId(int course_id) 
    {
        this.course_id = course_id;
    }
    
    /**
     *
     * @param c
     * @return
     */
    public static CertificateInfo wrap(Certification c) 
    {
        return new CertificateInfo(c);
    }

    /**
     *
     * @param certifications
     * @return
     */
    public static List<CertificateInfo> wrap(List<Certification> certifications) 
    {
        List<CertificateInfo> certInfoList = new ArrayList<>();
        for (Certification c : certifications) 
        {
                certInfoList.add(new CertificateInfo(c));
        }
        return certInfoList;
    }

    /**
     *
     * @param em
     * @return
     */
    public Certification getCertification(EntityManager em) 
    {
        Certification certificate;

        if (cert_id > 0) 
        {
            certificate = em.find(Certification.class, cert_id);
        } 
        else 
        {
            certificate = new Certification();
            if(course_id > 0)
            {
                Course course = em.getReference(Course.class, course_id);
                certificate.setCourse(course);
            }
            if(student_id > 0)
            {
                Student student = em.getReference(Student.class, student_id);
                certificate.setStudent(student);
            }
        }  	
        return certificate;
    }
}
