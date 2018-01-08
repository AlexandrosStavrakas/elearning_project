package omada5.ElearningProject.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * University class
 * @author thegr
 */
@Entity
@Table(name = "Universities")
public class University implements Serializable 
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uni_id;

    @Column(name="Name")
    private String name;

    @Column(name="Email")
    private String email;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private Uni_Admin uni_Admin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="university", fetch=FetchType.LAZY)
    private List<Course> courses = new ArrayList <Course>();

    /**
     * A constructor for university
     * @param name the name of the university
     * @param email the email of the university
     */
    public University(String name, String email) 
    {
        this.name = name;
        this.email = email;
    }
    
    /**
     * Default constructor for University
     */
    public University(){}

    /**
     * returns the university Id value
     * @return uni_id
     */
    public int getUni_id() 
    {
        return this.uni_id;
    }

    /**
     * returns the name of the university
     * @return name
     */
    public String getName() 
    {
        return this.name;
    }

    /**
     * sets the name of the university
     * @param name
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * returns the email of the university
     * @return email
     */
    public String getEmail() 
    {
        return this.email;
    }

    /**
     * sets the email of the university
     * @param email
     */
    public void setEmail(String email) 
    {
        this.email = email;
    }

    /**
     * returns the university administrator
     * @return uni_Admin
     */
    public Uni_Admin getUni_Admin() 
    {
        return this.uni_Admin;
    }

    /**
     * sets the new university administrator
     * @param uni_Admin
     */
    public void setUni_Admin(Uni_Admin uni_Admin) 
    {
        this.uni_Admin = uni_Admin;
    }

    /**
     * returns the list of the university courses
     * @return courses
     */
    public List<Course> getCourses() 
    {
        return this.courses;
    }
 
    /**
     * adds a new course to university courses list
     * @param course
     */
    public void addCourses(Course course)
    {
        if(course != null)
            courses.add(course);
    }
    
    /**
     * removes an existing course from university courses list 
     * @param course
     */
    public void removeCourses(Course course)
    {
        //if(course != null)
        courses.remove(course);
    }
}
