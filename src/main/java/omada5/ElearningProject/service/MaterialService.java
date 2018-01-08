package omada5.ElearningProject.service;

import java.io.FileOutputStream;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import omada5.ElearningProject.domain.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * The MaterialService Class
 * @author thegr
 */
public class MaterialService 
{
private EntityManager em;

    /**
     * sets the entity manager for the service
     * @param em
     */
    public MaterialService(EntityManager em) 
    {
        this.em = em;
    }
    
    /**
     *
     * @param material
     * @return
     */
    public Material save(Material material) 
    {
        if (material != null | material.getTitle() != null | material.getDescription() != null) 
        {
            em.persist(material);
            return material;
        }
        return null;
    }
    
    /**
     * updates a Material
     * @param id
     * @param title
     * @param description
     * @return true if updated
     */
        public boolean saveOrUpdateMaterial(int id, String title, String description) 
        {
            if (id < 0)
            {
                return false;
            }
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            List<Material> materials;
            materials = em.createQuery("select m from Material m").getResultList();
            for(Material mr: materials)
            {
                if(mr.getMat_id() == id)
                {
                    if(title != null)
                        mr.setTitle(title);
                    if(description != null)
                        mr.setDescription(description);
                    em.merge(mr);
                    tx.commit();
                    return true;
                }
            }
            tx.rollback();
            return false;
        }
    
    /**
     * creates a new Material
     * @param title
     * @param description
     * @param course
     * @return true if creation succeed
     */
    public boolean CreateNewMaterial(String title, String description, Course course)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(course != null && title != null && description != null)
        {
            Material material = new Material(title, description);
            byte[] bFile;
            FileController f = new FileController();
            File file = new File("C:\\Users\\thegr\\Documents\\test1.txt");
            bFile = f.FileUpolader(file);
            material.setFile(bFile);
            material.setCourse(course);
            course.addMaterial(material);
            em.merge(material);
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
    
    /**
     * Creates a new Material for a course
     * @param title
     * @param description
     * @return Material
     */
    public Material CreateNewMaterial(String title, String description)
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(title != null && description != null)
        {
            Material material = new Material(title, description);
            byte[] bFile;
            FileController f = new FileController();
            File file = new File("C:\\Users\\thegr\\Documents\\test1.txt");
            bFile = f.FileUpolader(file);
            material.setFile(bFile);
            em.persist(material);
            tx.commit();
            return material;
        }
        else
        {
            tx.rollback();
            return null;
        }
    }
    
    /**
     *
     * @param material
     * @return
     */
    public boolean createMaterial(Material material) 
    {
        if (material != null | material.getTitle() != null | material.getDescription() != null) 
        {
            em.persist(material);
            return true;
        }
        return false;
    }

    /**
     * returns a List with all Materials	
     * @return listMaterials 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<Material> findAllMaterials() throws FileNotFoundException, IOException 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Material> listMaterials = em.createQuery("Select m FROM Material m").getResultList();
        if(listMaterials == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            int counter = 1;
            for(Material material: listMaterials)
            {
                FileOutputStream fileOuputStream = new FileOutputStream("C:\\Users\\thegr\\Documents\\fileuploaded"+counter+".txt"); 
                fileOuputStream.write(material.getFile());
                counter++;
            }
        }
        tx.commit();
        return listMaterials;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public Material findMaterialById(int id) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Material material = null;
        try 
        {
        	material = em.find(Material.class, id);
            tx.commit();
        } 
        catch (NoResultException ex) 
        {
            tx.rollback();
        }
        return material;
    }

    /**
     * deletes a Material
     * @param material
     * @return true if deletion succeed
     */
    public boolean deleteMaterial(Material material) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if (material != null) 
        {
            Course tmp = material.getCourse();
            tmp.removeMaterial(material);
            material.setCourse(null);
            em.remove(material);
            em.merge(tmp);
            tx.commit();
            return true;
        }
        tx.rollback();
        return false;
    }
    
    /**
     * assigns a Material to a Course
     * @param material
     * @param course
     * @return true if assignment succeed
     */
    public boolean AssignMaterialToCourse(Material material, Course course) 
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        if(course != null && material != null)
        {
            course.addMaterial(material);
            material.setCourse(course);
            em.merge(course);
            em.merge(material);
        }
        tx.commit();
        return true;
    }
    
    /**
     * returns a List with the Materials of a Course
     * @param c
     * @return listMaterials 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<Material> findMaterialByCourse(Course c) throws FileNotFoundException, IOException
    {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<Material> listMaterials = em.createQuery("Select m FROM Material m where m.course.course_id =:courseid").setParameter("courseid", c.getCourse_id()).getResultList();
        if(listMaterials == null)
        {
            tx.rollback();
            return null;
        }
        else
        {
            int counter = 1;
            for(Material material: listMaterials)
            {
                FileOutputStream fileOuputStream = new FileOutputStream("C:\\Users\\thegr\\Documents\\fileuploadedFMBC"+counter+".txt"); 
                fileOuputStream.write(material.getFile());
                counter++;
            }
        }
        tx.commit();
        return listMaterials;
    }
}
