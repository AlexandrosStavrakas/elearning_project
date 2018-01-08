package omada5.ElearningProject.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import omada5.ElearningProject.domain.*;

/**
 * The GradeService Class
 * @author thegr
 */
public class GradeService 
{
    private final EntityManager em;

    /**
     * sets the entity manager for the service
     * @param em
     */
    public GradeService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     * returns a List with all Projects
     * @return listProjects
     */
    public List<Project> findAllProjects()
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Project> listProjects;
        listProjects = em.createQuery("select p from Project p").getResultList();

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
     * @return
     */
    public List<Grade> findAllGrades()
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Grade> listGrades;
        listGrades = em.createQuery("select g from Grade g").getResultList();

        if(listGrades == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return listGrades;
        }
    }
    
    /**
     *
     * @param grade
     * @return
     */
    public Grade save(Grade grade) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (grade.getGrade_id() > 0) 
        {
            grade = em.merge(grade);
        } 
        else 
        {
            em.persist(grade);
        }
        tx.commit();
        return grade;
    }
    
    /**
     * creates a new Grade
     * @param p
     * @param s
     * @param f
     * @return the Grade
     */
    public Grade createGrade(Project p, Student s, File f)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Grade g = new Grade();
        g.setProject(p);
        g.setStudent(s);
        byte[] bFile;
        FileController file = new FileController();
        bFile = file.FileUpolader(f);
        g.setFile(bFile);
        em.persist(g);
        tx.commit();
        return g;
    }
    
    /**
     * uploads a Project file
     * @param g
     * @param f
     */
    public void uploadProjectFile(Grade g, File f)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        byte[] bFile;
        FileController file = new FileController();
        bFile = file.FileUpolader(f);
        g.setFile(bFile);
        em.persist(g);
        tx.commit();
    }
    
    /**
     * returns a List the Grades of a Project
     * @param project
     * @return results
     */
    public List<Grade> findGradeByProject(Project project) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Grade> results;
        results = em.createQuery("select g from Grade g join fetch g.project as p where p.project_id =:pid").setParameter("pid", project.getProject_id()).getResultList();
        if(results.isEmpty())
        {
            tx.rollback();
            return null;
        }
        else
        {
            tx.commit();
            return results;
        }
    }
    
    /**
     * sets a Score to a Grade
     * @param grade
     * @param score
     * @return true if the addition of score succeed
     */
    public boolean setScoretoGrade(Grade grade, int score) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (grade != null) 
        {
            grade.setScore(score);
            em.merge(grade);
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
     * 
     * @param grades
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void getFileForGrade(List<Grade> grades) throws FileNotFoundException, IOException
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        int counter = 1;
        for(Grade g:grades)
        {
            FileOutputStream fileOuputStream = new FileOutputStream("C:\\Users\\thegr\\Documents\\project_for_grade"+counter+".txt"); 
            fileOuputStream.write(g.getFile());
            counter++;
            em.merge(g);
        }
        tx.commit();
    }
}
