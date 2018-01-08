package omada5.ElearningProject.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The Certification Class
 * @author thegr
 */
@Entity
@Table(name="Certifications")
public class Certification implements Serializable 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cert_id;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="studentId")
    private Student student;
    
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="courseId")
    private Course course;
    
    /**
     *
     */
    public Certification(){}

    /**
     * returns the Id of the Certification
     * @return cert_id
     */
    public int getCert_id() 
    {
        return this.cert_id;
    }


    /**
     * returns a student with this certification
     * @return student
     */
    public Student getStudent() 
    {
        return this.student;
    }

    /**
     * sets a student who takes a certification
     * @param student is the student who has the certification
     */
    public void setStudent(Student student) 
    {
        this.student = student;
    }
    
    /**
     * returns the course with certification
     * @return course
     */
    public Course getCourse() 
    {
        return this.course;
    }

    /**
     * sets a course for certification
     * @param course is the course of the certification
     */
    public void setCourse(Course course) 
    {
        this.course = course;
    }
}
