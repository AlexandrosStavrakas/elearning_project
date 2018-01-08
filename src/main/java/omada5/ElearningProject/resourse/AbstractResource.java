package omada5.ElearningProject.resourse;

import javax.persistence.EntityManager;
import omada5.ElearningProject.persistence.JPAUtil;
/**
 *
 * @author thegr
 */
public class AbstractResource 
{

    /**
     *
     * @return
     */
    protected EntityManager getEntityManager() 
    {
        return JPAUtil.getCurrentEntityManager();
    }
}