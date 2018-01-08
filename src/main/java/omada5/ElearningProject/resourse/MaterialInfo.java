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
public class MaterialInfo 
{
    private int material_id;

    private String title;

    private String description;

    private int course_id;
    
    /**
     *
     */
    public MaterialInfo(){}
    
    /**
     *
     * @param m
     */
    public MaterialInfo(Material m)
    {
        material_id = m.getMat_id();
        title = m.getTitle();
        description = m.getDescription();
        course_id = m.getCourse().getCourse_id();
    }
    
    /**
     *
     * @return
     */
    public int getMaterial_id() 
    {
        return this.material_id;
    }

    /**
     *
     * @param material_id
     */
    public void setMaterial_id(int material_id) 
    {
        this.material_id = material_id;
    }
    /**
     * returns the project title
     * @return title
     */ 
    public String getTitle() 
    {
        return this.title;
    }

    /**
     * sets the title of the project
     * @param title
     */
    public void setTitle(String title) 
    {
        this.title = title;
    }

    /**
     * returns the description of the project
     * @return description
     */
    public String getDescription() 
    {
        return this.description;
    }

    /**
     * sets the description of the project
     * @param description
     */
    public void setDescription(String description) 
    {
        this.description = description;
    }
    
    /**
     * returns the course the project belongs
     * @return course
     */
    public int getCourseId() 
    {
        return this.course_id;
    }

    /**
     * sets the course the project belongs
     * @param course_id
     */
    public void setCourseId(int course_id) 
    {
        this.course_id = course_id;
    }
    
    /**
     *
     * @param m
     * @return
     */
    public static MaterialInfo wrap(Material m) 
    {
        return new MaterialInfo(m);
    }

    /**
     *
     * @param materials
     * @return
     */
    public static List<MaterialInfo> wrap(List<Material> materials) 
    {
        List<MaterialInfo> materialInfoList = new ArrayList<>();
        for (Material m : materials) 
        {
                materialInfoList.add(new MaterialInfo(m));
        }
        return materialInfoList;
    }
    
    /**
     *
     * @param em
     * @return
     */
    public Material getMaterial(EntityManager em) 
    {
        Material material;

        if (material_id > 0) 
        {
            material = em.find(Material.class, material_id);
        } 
        else 
        {
            material = new Material();
            material.setTitle(title);
            material.setDescription(description);
            if(course_id > 0)
            {
            	Course course = em.getReference(Course.class, course_id);
                material.setCourse(course);
            }
            
        }
        return material;
    }
}
