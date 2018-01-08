package omada5.ElearningProject.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The Course class
 * @author thegr
 */
@Entity
@Table(name = "Courses")
public class Course implements Serializable 
{
    @Id
    @Column(name = "Course_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int course_id;   

    @Column(name = "Title", length=200, nullable=false)
    private String title; 

    @Column(name = "Description", length=200, nullable=false)
    private String description;

    @Column(name = "Field", length=200, nullable=false)
    private String field;
	
    @Column(name = "University_Course_Id", nullable=false, unique=true)
    private String uniCourseId;

    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL, mappedBy="course", fetch=FetchType.LAZY)
    private List<Project> projects = new ArrayList<Project>();

    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL, mappedBy="course", fetch=FetchType.LAZY)
    private List<Material> materials = new ArrayList<Material>();

    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) 
    @JoinColumn(name="teacherid")
    private Teacher teacher;

    @ManyToMany(mappedBy="courses",fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<Student>();
    
    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="universityid")
    private University university;

    /**
     * Default constructor for Course
     */
    public Course(){}
    
    /**
     * A constructor for course
     * @param title
     * @param description
     * @param field
     * @param uniCourseId
     */
    public Course(String title, String description, String field, String uniCourseId)
    {
        this.title = title;
        this.description = description;
        this.field = field;
        this.uniCourseId = uniCourseId;
    }

    /**
     * returns the course Id
     * @return course_id
     */
    public int getCourse_id() 
    {
        return this.course_id;
    }
    
    /**
     * sets the course_id
     * @param course_id the id of the course
     */
    public void setCourse_id(int course_id) 
    {
        this.course_id = course_id;
    }
    
    /**
     * returns the title of the course
     * @return title
     */
    public String getTitle() 
    {
        return this.title;
    }

    /**
     * sets a title for the course
     * @param title is the title of the course
     */
    public void setTitle(String title) 
    {
        this.title = title;
    }

    /**
     * returns the course description
     * @return description
     */
    public String getDescription() 
    {
        return this.description;
    }

    /**
     * sets the description of the course
     * @param description is the description of the course
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }

    /**
     * returns the scientific field of the course
     * @return field 
     */
    public String getField() 
    {
        return this.field;
    }

    /**
     * sets the scientific field of the course
     * @param field is the field of the course
     */
    public void setField(String field) 
    {
        this.field = field;
    }

    /**
     * returns a list with the projects of the course
     * @return projects
     */
    public List<Project> getProjects() 
    {
        return this.projects;
    }

    /**
     * sets a list with the projects of the course
     * @param projects the projects the course has
     */
    public void setProjects(List<Project> projects) 
    {
        this.projects = projects;
    }

    /**
     * returns a list with the materials of the course
     * @return materials
     */
    public List<Material> getMaterials() 
    {
        return this.materials;
    }

    /**
     * sets the materials of the course
     * @param materials the materials the course has
     */
    public void setMaterials(List<Material> materials) 
    {
        this.materials = materials;
    }

    /**
     * returns a set of students who attend this course
     * @return students
     */
    public List<Student> getStudents() 
    {
        return this.students;
    }

    /**
     * sets a set of students who attend this course
     * @param students the students who attend the course
     */
    public void setStudents(List<Student> students) 
    {
        this.students = students;
    }

    /**
     * returns the teacher of the course
     * @return teacher
     */
    public Teacher getTeacher() 
    {
        return this.teacher;
    }

    /**
     * sets the teacher of the course
     * @param teacher the teacher who teaches the course
     */
    public void setTeacher(Teacher teacher) 
    {
        this.teacher = teacher;
    }
    
    /**
     * returns the university course Id
     * @return uniCourseId
     */
    public String getUniCourseId() 
    {
        return this.uniCourseId;
    }

    /**
     * sets the university course Id
     * @param uniCourseId the university code of the course
     */
    public void setUniCourseId(String uniCourseId) 
    {
        this.uniCourseId = uniCourseId;
    }
    
    /**
     * returns the university in which the course belongs
     * @return university
     */
    public University getUniversity() 
    {
        return this.university;
    }

    /**
     * sets the university in which the course belongs
     * @param university the university the course belongs to
     */
    public void setUniversity(University university) 
    {
        this.university = university;
    }
    
    /**
     * adds a new project to courses project list
     * @param project the project which added to the course
     */
    public void addProject(Project project) 
    {
        projects.add(project);
    }
    
    /**
     * removes an existing project from the project course list
     * @param project the project which removed from the course
     */
    public void removeProject(Project project)
    {
        projects.remove(project);
    }
    
    /**
     * adds a material to the material course list
     * @param material the material which added to the course
     */
    public void addMaterial(Material material) 
    {
        materials.add(material);
    }
    
    /**
     * removes an existing material from the material course list
     * @param material the material which removed from the course
     */
    public void removeMaterial(Material material)
    {
        materials.remove(material);
    }
    
    /**
     * adds a student to the students course list
     * @param student
     */
    void addStudents(Student student) 
    {
        if (student != null) 
        {
            student.getCourses().add(this);
            this.students.add(student);
        }
    }

    /**
     * removes a student from the students course list
     * @param student
     */
    void removeStudent(Student student) 
    {
        if (student != null) 
        {
            student.getCourses().remove(this);
            this.students.remove(student);
        }
    }
}
