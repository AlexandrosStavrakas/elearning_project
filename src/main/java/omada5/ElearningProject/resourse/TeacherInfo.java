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
public class TeacherInfo 
{
    private Integer person_id;

    private String name;

    private String surname;

    private String email;
    
    private String password;

    /**
     *
     */
    public TeacherInfo(){}
    
    /**
     *
     * @param teacher
     */
    public TeacherInfo(Teacher teacher) 
    {
        person_id = teacher.getPerson_id();
        name = teacher.getName();
        surname = teacher.getSurname();
        email = teacher.getEmail();
        password = teacher.getPassword();
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
    public static TeacherInfo wrap(Teacher t) 
    {
        return new TeacherInfo(t);
    }

    /**
     *
     * @param teachers
     * @return
     */
    public static List<TeacherInfo> wrap(List<Teacher> teachers) 
    {
        List<TeacherInfo> teacherInfoList = new ArrayList<>();
        for (Teacher t : teachers) 
        {
                teacherInfoList.add(new TeacherInfo(t));
        }
        return teacherInfoList;
    }

    /**
     *
     * @param em
     * @return
     */
    /*
    public Teacher getTeacher(EntityManager em) 
    {
        Teacher teacher;

        if (person_id > 0) 
        {
            teacher = em.find(Teacher.class, person_id);
        } 
        else 
        {
            teacher = new Teacher();
        }

        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setCourses(courses);

        return teacher;
    }*/
}
