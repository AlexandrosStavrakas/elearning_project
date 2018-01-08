package omada5.ElearningProject.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The Material Class
 * @author thegr
 */

@Entity
@Table(name = "Materials")
public class Material implements Serializable 
{
    @Column(name = "mat_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int mat_id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Description")
    private String description;
    
    @Column(name = "File")
    private byte[] file;
    
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="courseId")
    private Course course;

    /**
     * Default constructor of Material
     */
    public Material(){}
    
    /**
     * A constructor of Material
     * @param title
     * @param description
     */
    public Material(String title, String description)
    {
        this.title = title;
        this.description = description;
    }
    
    /**
     * returns the Material Id
     * @return mat_id
     */
    public int getMat_id() 
    {
        return this.mat_id;
    }

    /**
     *
     * @param mat_id
     */
    public void setMat_id(int mat_id) 
    {
        this.mat_id = mat_id;
    }
    /**
     * returns the title of the material
     * @return title
     */
    public String getTitle() 
    {
        return this.title;
    }

    /**
     * sets the title of the material
     * @param title
     */
    public void setTitle(String title) 
    {
        this.title = title;
    }

    /**
     * returns the description of the material
     * @return description
     */
    public String getDescription() 
    {
        return this.description;
    }

    /**
     * sets the description of the material
     * @param description
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }
    
    /**
     * returns the file of the material
     * @return file
     */
    public byte[] getFile() 
    {
        return this.file;
    }
    
    /**
     * sets a new file for the material
     * @param file
     */
    public void setFile(byte[] file) 
    {
        this.file = file;
    }
    
    /**
     * returns the course the material belongs
     * @return course
     */
    public Course getCourse() 
    {
        return this.course;
    }

    /**
     * sets the course the material belongs to
     * @param course
     */
    public void setCourse(Course course) 
    {
        this.course = course;
    }
}
