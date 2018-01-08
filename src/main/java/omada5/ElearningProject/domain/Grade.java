package omada5.ElearningProject.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The Grade Class
 * @author thegr
 */
@Entity
@Table(name="Grades")
public class Grade implements Serializable 
{
    @Id
    @Column(name = "Grade_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int grade_id;

    @Column(name = "Score")
    private double score;
    
    @Column(name = "File")
    private byte[] file;

    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="studentId")
    private Student student;
    
    @ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="projectId")
    private Project project;
    
    /**
     * Default Constructor of the Grade
     */
    public Grade(){}
    
    /**
     * returns the grade id
     * @return grade_id
     */
    public int getGrade_id() 
    {
        return this.grade_id;
    }

    /**
     * returns the score of the grade
     * @return score
     */
    public double getScore() 
    {
        return this.score;
    }
    
    /**
     * sets the score of the grade
     * @param score
     */
    public void setScore(double score) 
    {
        this.score = score;
    }
   
    /**
     * returns the project file the student uploads
     * @return file
     */
    public byte[] getFile() 
    {
        return this.file;
    }
    
    /**
     * sets the project file that the student uploads
     * @param file
     */
    public void setFile(byte[] file) 
    {
        this.file = file;
    }

    /**
     * returns the student of the grade
     * @return student
     */
    public Student getStudent() 
    {
        return this.student;
    }

    /**
     * sets the student of the grade
     * @param student
     */
    public void setStudent(Student student) 
    {
        this.student = student;
    }
    
    /**
     * returns the project of the grade
     * @return project
     */
    public Project getProject() 
    {
        return this.project;
    }

    /**
     * sets the project of the grade
     * @param project
     */
    public void setProject(Project project) 
    {
        this.project = project;
    }
}
