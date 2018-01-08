package omada5.ElearningProject.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import omada5.ElearningProject.domain.*;

/**
 * The ProjectService
 * @author thegr
 */
public class ProjectService 
{
    private EntityManager em;

    /**
     * sets the entity for the service
     * @param em
     */
    public ProjectService(EntityManager em) 
    {
        this.em = em;
    }
      
    /**
     * updates a course project
     * @param id
     * @param title
     * @param description
     * @return true if it is update or false if it is not
     */
    public boolean saveOrUpdateProject(int id, String title, String description) 
    {
        if (id < 0)
        {
            return false;
        }
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Project> projects;
        projects = em.createQuery("select p from Project p").getResultList();
        for(Project pr: projects)
        {
            if(pr.getProject_id() == id)
            {
                if(title != null)
                    pr.setTitle(title);
                if(description != null)
                    pr.setDescription(description);
                em.merge(pr);
                tx.commit();
                return true;
            }
        }
        tx.rollback();
        return false;
    }
    
    
    /**
     * creates a new Project
     * @param project
     * @return true if creation succeed
     */
    public boolean createProject(Project project) 
    {
        if (project != null | project.getTitle() != null | project.getDescription() != null) 
        {
            em.persist(project);
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param project
     * @return
     */
    public Project save(Project project) 
    {
        if (project != null | project.getTitle() != null | project.getDescription() != null) 
        {
            em.persist(project);
            return project;
        }
        return null;
    }

    /**
     * returns a List with all Projects
     * @return listProjects
     */
    public List<Project> findAllProjects()
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Project> listProjects = em.createQuery("Select p FROM Project p").getResultList();
        if(listProjects == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return listProjects;
        }
    }
    
    /**
     *
     * @param id
     * @return
     */
    public Project findProjectById(int id) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Project project = null;
        try 
        {
            project = em.find(Project.class, id);
            tx.commit();
        } 
        catch (NoResultException ex) 
        {
            tx.rollback();
        }
        return project;
    }
    
    /**
     * returns a List with Projects of a Course
     * @param c
     * @return listProjects
     */
    public List<Project> findProjectByCourse(Course c)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Project> listProjects = em.createQuery("Select p FROM Project p where p.course.course_id =:courseid").setParameter("courseid", c.getCourse_id()).getResultList();
        if(listProjects == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return listProjects;
        }
        
    }

    /**
     * deletes a Project
     * @param project
     * @return true if deletion succeed
     */
    public boolean deleteProject(Project project) 
    {
        EntityTransaction tx = em.getTransaction();
        
        if (project != null) 
        {
        	Course course = findCourseByProjectTitle(project.getTitle());
        	tx.begin();
        	project.setCourse(null);
        	course.removeProject(project);
            em.remove(project);
            em.merge(course);
            return true;
        }
        tx.commit();
        return false;
    }
    
    /**
     * assigns a Project to a Course
     * @param project
     * @param course
     * @return true if assignment succeed
     */
    public boolean AssignProjectToCourse(Project project, Course course) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(course != null && project != null)
        {
            course.addProject(project);
            course = em.merge(course);
            project.setCourse(course);
            //project = 
            em.merge(project);
            tx.commit();
            return true;
        }
        else
        {
            tx.rollback();
            return false;
        }
        
    }
    
    /**
     * checks the number of Projects
     * @param c
     * @return true if Projects are less than 3
     */
    public boolean checkProjectNumber(Course c)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Project> projects = em.createQuery("select p from Project p where p.course.course_id like :courseid").setParameter("courseid", c.getCourse_id()).getResultList();
        tx.commit();
        if(projects.size()<3)
            return true;
        else
        {
            tx.rollback();
            return false;
        }
    }
    
    /**
     * returns a Course by ProjectTitle
     * @param title
     * @return the Course
     */
    public Course findCourseByProjectTitle(String title) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(title != null)
        {
            List<Course> results = null;
            results = em.createQuery("select c from Course c join fetch c.projects as p where p.title like :projecttitle").setParameter("projecttitle", title).getResultList();
            tx.commit();
            return results.get(0);
        }
        else
        {
            tx.rollback();
            return null;
        }
    }
    
    /**
     * creates a new Project
     * @param title
     * @param description
     * @param course
     * @return true if creation succeed
     */
    public boolean CreateNewProject(String title, String description, Course course)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(course != null && title != null && description != null)
        {
            Project project = new Project(title, description);
            project.setCourse(course);
            course.addProject(project);
            em.merge(project);
            em.merge(course);
            tx.commit();
            return true;
        }
        else
        {
            tx.rollback();
            return false;
        }
    }
}
