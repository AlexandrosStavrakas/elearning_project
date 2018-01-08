package omada5.ElearningProject.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The Student Class
 * @author thegr
 */
@Entity
@DiscriminatorValue("Student")
public class Student extends Person implements Serializable 
{
    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL, mappedBy="student", fetch=FetchType.LAZY)
    private List<Certification> certifications = new ArrayList<Certification>();

    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL, mappedBy="student", fetch=FetchType.LAZY)
    private List<Grade> grades = new ArrayList<Grade>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE },fetch=FetchType.LAZY)
    @JoinTable(name="Student_Course",joinColumns = {@JoinColumn(name="StudentId")},inverseJoinColumns = {@JoinColumn(name="CourseId")})
    private List<Course> courses = new ArrayList<Course>();

    /**
     * Default constructor for Student
     */
    public Student(){}
    
    /**
     * A constructor for Student
     * @param name
     * @param surname
     * @param email
     * @param password
     */
    public Student(String name, String surname, String email, String password) 
    {
        super.setName(name);
        super.setSurname(surname);
        super.setEmail(email);
        super.setPassword(password);
    }

    /**
     * returns the list of certifications student has
     * @return certifications
     */
    public List<Certification> getCertifications() 
    {
        return this.certifications;
    }

    /**
     * returns the list of grades student has
     * @return grades
     */
    public List<Grade> getGrades() 
    {
        return this.grades;
    }


    /**
     * returns the list of courses which student has
     * @return courses
     */
    public List<Course> getCourses() 
    {
        return this.courses;
    }


    /**
     * adds a course to the list of courses of a student
     * @param course
     */
    public void addCourses(Course course) 
    {
        if (course != null)
            course.addStudents(this);
    }
    
    /**
     * removes a course from the list of courses
     * @param course
     */
    public void removeCourse(Course course) 
    {
        if (course != null) 
            course.removeStudent(this);
    }
    
    /**
     * adds a grade to the grade list of the student
     * @param grade
     */
    public void addGrade(Grade grade) 
    {
        grades.add(grade);
    }
    
    /**
     * adds a certification to the certification list of the student
     * @param certification
     */
    public void addCertification(Certification certification) 
    {
        certifications.add(certification);
    }
    
    /**
     * removes a grade from a grade list of a student
     * @param grade
     */
    public void removeGrade(Grade grade)
    {
        grades.remove(grade);
    }

}
