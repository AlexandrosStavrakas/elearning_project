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
public class CourseInfo 
{
    private int Course_id;

    private String title;

    private String description;

    private String field;
    
    private String University_Course_Id;

    private int teacherid;
    
    private int universityid;

    /**
     *
     */
    public CourseInfo(){}
    
    /**
     *
     * @param Course_id
     * @param title
     * @param description
     * @param field
     * @param University_Course_Id
     * @param teacherid
     * @param universityid
     */
    public CourseInfo(int Course_id, String title, String description, String field, String University_Course_Id, int teacherid, int universityid) 
    {
        this(title, description, field, University_Course_Id, teacherid, universityid);
        this.Course_id = Course_id;

    }

    /**
     *
     * @param title
     * @param description
     * @param field
     * @param University_Course_Id
     * @param teacherid
     * @param universityid
     */
    public CourseInfo(String title, String description, String field, String University_Course_Id, int teacherid, int universityid) 
    {
        super();
        this.title = title;
        this.description = description;
        this.field = field;
        this.University_Course_Id = University_Course_Id;
        this.teacherid = teacherid;            
        this.universityid = universityid;
    }
    
    /**
     *
     * @param title
     * @param description
     * @param field
     * @param University_Course_Id
     * @param universityid
     */
    public CourseInfo(String title, String description, String field, String University_Course_Id, int universityid) 
    {
        super();
        this.title = title;
        this.description = description;
        this.field = field;
        this.University_Course_Id = University_Course_Id;
        this.universityid = universityid;
    }

    /**
     *
     * @param course
     */
    public CourseInfo(Course course) 
    {
        Course_id = course.getCourse_id();
        title = course.getTitle();
        description = course.getDescription();
        field = course.getField();
        University_Course_Id = course.getUniCourseId();
        teacherid = course.getTeacher().getPerson_id();
        universityid = course.getUniversity().getUni_id();
    }

    /**
     *
     * @return
     */
    public int getCourse_id() 
    {
        return Course_id;
    }

    /**
     *
     * @param Course_id
     */
    public void setCourse_id(int Course_id) 
    {
        this.Course_id = Course_id;
    }

    /**
     *
     * @return
     */
    public String getTitle() 
    {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) 
    {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getDescription() 
    {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getField() 
    {
        return field;
    }

    /**
     *
     * @param field
     */
    public void setField(String field) 
    {
        this.field = field;
    }

    /**
     *
     * @return
     */
    public String getUniversity_Course_Id() 
    {
        return University_Course_Id;
    }

    /**
     *
     * @param University_Course_Id
     */
    public void setUniversity_Course_Id(String University_Course_Id) 
    {
        this.University_Course_Id = University_Course_Id;
    }

    /**
     *
     * @return
     */
    public int getTeacherId() 
    {
        return teacherid;
    }

    /**
     *
     * @param teacherid
     */
    public void setTeacherId(int teacherid) 
    {
        this.teacherid = teacherid;
    }

    /**
     *
     * @return
     */
    public int getUniversityId() 
    {
        return universityid;
    }

    /**
     *
     * @param universityid
     */
    public void setUniversityId(int universityid) 
    {
        this.universityid = universityid;
    }

    /**
     *
     * @param c
     * @return
     */
    public static CourseInfo wrap(Course c) 
    {
        return new CourseInfo(c);
    }

    /**
     *
     * @param courses
     * @return
     */
    public static List<CourseInfo> wrap(List<Course> courses) 
    {
        List<CourseInfo> courseInfoList = new ArrayList<>();
        for (Course c : courses) 
        {
                courseInfoList.add(new CourseInfo(c));
        }
        return courseInfoList;
    }

    /**
     *
     * @param em
     * @return
     */
    public Course getCourse(EntityManager em) 
    {
        Course course;

        if (Course_id > 0) 
        {
            course = em.find(Course.class, Course_id);
        } 
        else 
        {
            course = new Course();
        }
        //course.setCourse_id(Course_id);
        course.setTitle(title);
        course.setDescription(description);
        course.setField(field);
        course.setUniCourseId(University_Course_Id);

        if(teacherid > 0)
        {
            Teacher teacher = em.getReference(Teacher.class, teacherid);
            course.setTeacher(teacher);
        }
        if(universityid > 0)
        {
                University uni = em.getReference(University.class, universityid);
                course.setUniversity(uni);
        }
        return course;
    }
}
