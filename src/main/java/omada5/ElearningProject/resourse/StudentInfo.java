/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class StudentInfo 
{
    private Integer person_id;

    private String name;

    private String surname;

    private String email;
    
    private String password;

    /**
     *
     */
    public StudentInfo(){}
    
    /**
     *
     * @param student
     */
    public StudentInfo(Student student) 
    {
        person_id = student.getPerson_id();
        name = student.getName();
        surname = student.getSurname();
        email = student.getEmail();
        password = student.getPassword();
    }

    /**
     *
     * @return
     */
    public int getPerson_id() 
    {
        return this.person_id;
    }
    
    /**
     *
     * @param person_id
     */
    public void setPerson_id(int person_id) 
    {
        this.person_id = person_id;
    }

    /**
     *
     * @return
     */
    public String getName() 
    {
        return this.name;
    }

    /**
     * sets a name to a Person
     * @param name 
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * returns the Surname of a Person
     * @return surname
     */
    public String getSurname() 
    {
        return this.surname;
    }

    /**
     * sets a Surname to a Person
     * @param surname
     */
    public void setSurname(String surname) 
    {
        this.surname = surname;
    }

    /**
     * returns the email of a Person
     * @return email
     */
    public String getEmail() 
    {
        return this.email;
    }

    /**
     * sets an email to a Person
     * @param email
     */
    public void setEmail(String email) 
    {
        this.email = email;
    }

    /**
     * returns the password of Person
     * @return password
     */
    public String getPassword() 
    {
        return this.password;
    }

    /**
     * sets a password for a Person
     * @param password
     */
    public void setPassword(String password) 
    {
        this.password = password;
    }
    
    /**
     *
     * @param t
     * @return
     */
    public static StudentInfo wrap(Student t) 
    {
        return new StudentInfo(t);
    }

    /**
     *
     * @param students
     * @return
     */
    public static List<StudentInfo> wrap(List<Student> students) 
    {
        List<StudentInfo> studentInfoList = new ArrayList<>();
        for (Student t : students) 
        {
                studentInfoList.add(new StudentInfo(t));
        }
        return studentInfoList;
    }

    /**
     *
     * @param em
     * @return
     */
    public Student getStudent(EntityManager em) 
    {
        Student student;

        if (person_id > 0) 
        {
            student = em.find(Student.class, person_id);
        } 
        else 
        {
            student = new Student();
        }

        student.setName(name);
        student.setSurname(surname);
        student.setEmail(email);
        student.setPassword(password);

        return student;
    }
}
