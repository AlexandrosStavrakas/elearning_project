package omada5.ElearningProject.domain;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The Uni_Admin Class
 * @author thegr
 */
@Entity
@DiscriminatorValue("Uni_Admin")
public class Uni_Admin extends Person implements Serializable 
{

    /**
     * A constructor for Uni_Admin
     * @param name
     * @param surname
     * @param email
     * @param password
     */
    public Uni_Admin(String name, String surname, String email, String password) 
    {
        super.setName(name);
        super.setSurname(surname);
        super.setEmail(email);
        super.setPassword(password);
    }

    /**
     * Default constructor for Uni_Admin
     */
    public Uni_Admin(){}
    
}
