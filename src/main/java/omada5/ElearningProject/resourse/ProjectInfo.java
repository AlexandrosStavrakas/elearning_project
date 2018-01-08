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
public class ProjectInfo 
{
    private int project_id;

    private String title;

    private String description;

    private int course_id;
    
    /**
     *
     */
    public ProjectInfo(){}
    
    /**
     *
     * @param p
     */
    public ProjectInfo(Project p)
    {
        project_id = p.getProject_id();
        title = p.getTitle();
        description = p.getDescription();
        course_id = p.getCourse().getCourse_id();
    }
    
    /**
     *
     * @return
     */
    public int getProject_id() 
    {
        return this.project_id;
    }

    /**
     *
     * @param project_id
     */
    public void setProject_id(int project_id) 
    {
        this.project_id = project_id;
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
     * @param p
     * @return
     */
    public static ProjectInfo wrap(Project p) 
    {
        return new ProjectInfo(p);
    }

    /**
     *
     * @param projects
     * @return
     */
    public static List<ProjectInfo> wrap(List<Project> projects) 
    {
        List<ProjectInfo> projectInfoList = new ArrayList<>();
        for (Project p : projects) 
        {
                projectInfoList.add(new ProjectInfo(p));
        }
        return projectInfoList;
    }
    
    /**
     *
     * @param em
     * @return
     */
    public Project getProject(EntityManager em) 
    {
        Project project;

        if (project_id > 0) 
        {
            project = em.find(Project.class, project_id);
        } 
        else 
        {
            project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            if(course_id > 0)
            {
            	Course course = em.getReference(Course.class, course_id);
                project.setCourse(course);
            }
            
        }
        return project;
    }
}
