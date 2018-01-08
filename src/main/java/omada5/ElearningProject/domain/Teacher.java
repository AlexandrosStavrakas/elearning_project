package omada5.ElearningProject.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The Teacher Class
 * @author thegr
 */
@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends Person implements Serializable 
{
    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL,mappedBy="teacher", fetch=FetchType.LAZY)
    private List<Course> courses = new ArrayList <Course>();

    /**
     * A constructor for Teacher
     * @param name
     * @param surname
     * @param email
     * @param password
     */
    public Teacher(String name, String surname, String email, String password) 
    {
        super.setName(name);
        super.setSurname(surname);
        super.setEmail(email);
        super.setPassword(password);
    }
    
    /**
     * Another constructor for Teacher
     * @param name
     * @param surname
     * @param email
     * @param password
     * @param courses
     */

    
    /**
    * Default constructor for Teacher 
    */
    public Teacher(){}
    
    /**
     * returns the list of teacher courses
     * @return courses
     */
    public List<Course> getCourses() 
    {
        return this.courses;
    }

    /**
     * sets a list of teacher courses
     * @param courses
     */
    public void setCourses(List<Course> courses) 
    {
        this.courses = courses;
    }
    
    /**
     * Adds a course to teachers courses list
     * @param course
     */
    public void addCourse(Course course)
    {
        if(course != null)
            courses.add(course);
    }
    
    /**
     * Removes a course to teachers courses list
     * @param course
     */
    public void removeCourse(Course course)
    {
        //if(course != null)
            courses.remove(course);
    }
}
