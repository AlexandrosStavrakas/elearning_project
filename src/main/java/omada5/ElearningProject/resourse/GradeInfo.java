package omada5.ElearningProject.resourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import omada5.ElearningProject.domain.*;

/**
 *
 * @author thegr
 */
@XmlRootElement
public class GradeInfo 
{
    private int grade_id;

    private double score;

    private String file;

    private int studentId;

    private int projectId;
    
    /**
     *
     */
    public GradeInfo(){}
    
    /**
     *
     * @param grade_id
     * @param score
     * @param file
     * @param studentId
     * @param projectId
     */
    public GradeInfo(int grade_id, double score, String file, int studentId, int projectId) 
    {
        this(score, file, studentId, projectId);
        this.grade_id = grade_id;

    }

    /**
     *
     * @param score
     * @param file
     * @param studentId
     * @param projectId
     */
    public GradeInfo(double score, String file, int studentId, int projectId)
    {
        //super();
    	this.score = score;
    	this.file = file;
        this.studentId = studentId;            
        this.projectId = projectId;
    }
    
    /**
     *
     * @param g
     */
    public GradeInfo(Grade g)
    {
        grade_id= g.getGrade_id();
        score = g.getScore();
        file = Arrays.toString(g.getFile());
        
        studentId = g.getStudent().getPerson_id();
        projectId = g.getProject().getProject_id();
    }
    
    /**
     * returns the grade id
     * @return grade_id
     */
    public int getGrade_id() 
    {
        return this.grade_id;
    }
    
    /**
     *
     * @param grade_id
     */
    public void setGrade_id(int grade_id) 
    {
        this.grade_id = grade_id;
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
    public String getFile() 
    {
        return this.file;
    }
    
    /**
     * sets the project file that the student uploads
     * @param file
     */
    public void setFile(String file) 
    {
        this.file = file;
    }

    /**
     * returns the student of the grade
     * @return student
     */
    public int getStudentId() 
    {
        return this.studentId;
    }

    /**
     * sets the student of the grade
     * @param studentId
     */
    public void setStudent(int studentId) 
    {
        this.studentId = studentId;
    }
    
    /**
     * returns the project of the grade
     * @return project
     */
    public int getProject() 
    {
        return this.projectId;
    }

    /**
     * sets the project of the grade
     * @param projectId
     */
    public void setProjectId(int projectId) 
    {
        this.projectId = projectId;
    }
    
    /**
     *
     * @param g
     * @return
     */
    public static GradeInfo wrap(Grade g) 
    {
        return new GradeInfo(g);
    }

    /**
     *
     * @param grades
     * @return
     */
    public static List<GradeInfo> wrap(List<Grade> grades) 
    {
        List<GradeInfo> gradeInfoList = new ArrayList<>();
        for (Grade g : grades) 
        {
                gradeInfoList.add(new GradeInfo(g));
        }
        return gradeInfoList;
    }

    /**
     *
     * @param em
     * @return
     */
    public Grade getGrade(EntityManager em) 
    {
        Grade grade;

        if (grade_id > 0) 
        {
        	grade = em.find(Grade.class, grade_id);
        } 
        else 
        {
        	grade = new Grade();
            if(projectId > 0)
            {
                Project project = em.getReference(Project.class, projectId);
                grade.setProject(project);
            }
            if(studentId > 0)
            {
                Student student = em.getReference(Student.class, studentId);
                grade.setStudent(student);
            }
        }  	
        return grade;
    }

}
