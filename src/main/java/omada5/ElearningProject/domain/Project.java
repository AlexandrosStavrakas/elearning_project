package omada5.ElearningProject.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The Project Class
 * @author thegr
 */
@Entity
@Table(name = "Projects")
public class Project implements Serializable 
{
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int project_id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;
    
    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="courseId")
    private Course course;
    
    /**
     * Default constructor of Project
     */
    public Project(){}
    
    /**
     * A constructor of Project
     * @param title
     * @param description
     */
    public Project(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    /**
     * returns the project Id of the project 
     * @return project_id
     */
    public int getProject_id() 
    {
        return this.project_id;
    }
    
    /**
     *
     * @param project_id
     */
    public void setProject_id(int project_id) 
    {
    	this.project_id = project_id;
    }

    /**
     * returns the project title
     * @return title
     */ 
    public String getTitle() 
    {
        return this.title;
    }

    /**
     * sets the title of the project
     * @param title
     */
    public void setTitle(String title) 
    {
        this.title = title;
    }

    /**
     * returns the description of the project
     * @return description
     */
    public String getDescription() 
    {
        return this.description;
    }

    /**
     * sets the description of the project
     * @param description
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }
    
    /**
     * returns the course the project belongs
     * @return course
     */
    public Course getCourse() 
    {
        return this.course;
    }

    /**
     * sets the course the project belongs
     * @param course
     */
    public void setCourse(Course course) 
    {
        this.course = course;
    }
}
