package omada5.ElearningProject.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The Person Class
 * @author thegr
 */
@Entity
@Table(name = "Persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length = 31, name = "Person_Type", discriminatorType = DiscriminatorType.STRING)
public abstract class Person implements Serializable 
{
    @Id
    @Column(name = "Person_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int person_id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

    /**
     * returns the id of a Person
     * @return person_id
     */
    public int getPerson_id() 
    {
        return this.person_id;
    }

    /**
     * returns the name of a Person
     * @return name
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
}
