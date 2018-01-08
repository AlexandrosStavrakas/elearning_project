package omada5.ElearningProject.resourse;

/**
 *
 * @author thegr
 */
public class ElearningUri 
{

    /**
     *
     */
    public static final String UNIVERSITIES = "universities";
    
    /**
     *
     */
    public static final String ADMINS = "admins";
    
    /**
     *
     */
    public static final String TEACHERS = "teachers";
    
    /**
     *
     */
    public static final String STUDENTS = "students";
    
    /**
     *
     */
    public static final String COURSES = "courses";
    
    /**
     *
     */
    public static final String MATERIALS = "materials";
    
    /**
     *
     */
    public static final String PROJECTS = "projects";
    
    /**
     *
     */
    public static final String GRADES = "grades";
    
    /**
     *
     */
    public static final String CERTIFICATES = "certificates";
    
    /**
     *
     */
    public static final String COURSE_SEARCH = "courses/search";
    
    /**
     *
     */
    public static final String SET_GRADE_TO_PROJECT = "grades/teachers/courses/projects/students/";
    
    //public static final String REGISTER = "courses/register";
    
    //everybody

    /**
     *
     * @param id
     * @return
     */
    public static String courseIdUri(String id) 
    {
        return COURSES + "/" + id;
    }
    
  //student

    /**
     *
     * @param id
     * @return
     */
    public static String studentIdUri(String id) 
    {
        return STUDENTS + "/" + id;
    }
    
    /**
     *
     * @param cid
     * @param sid
     * @return
     */
    public static final String register(String cid, String sid)
    {
    	return "courses/" + cid + "/students/" + sid + "/register/";
    }
    
    /**
     *
     * @param cid
     * @param sid
     * @return
     */
    public static final String unregister(String cid, String sid)
    {
    	return "courses/" + cid + "/students/" + sid + "/unregister/";
    }
    
    //everybody

    /**
     *
     * @param title
     * @return
     */
    public static String courseSearchUri(String title) 
    {
        return COURSE_SEARCH + "?title=" + title;
    }
    
    //everybody

    /**
     *
     * @param surname
     * @return
     */
    public static String courseSearchTeacherUri(String surname) 
    {
        return COURSE_SEARCH_TEACHER + "?teacher=" + surname;
	}
    
    /**
     *
     */
    public static final String COURSE_SEARCH_TEACHER = "courses/searchTeacher";
    
    //loged in as student

    /**
     *
     * @param id
     * @return
     */
    public static String courseSearchStudentUri(String id) 
    {
        return COURSES + "/" + STUDENTS + "/" + id;
    }
    
    //loged in as student

    /**
     *
     * @param cid
     * @param sid
     * @return
     */
    public static String CourseIdStudentIdUri(String cid, String sid) 
    {
        return COURSES + "/" + cid + "/" + STUDENTS + "/" + sid;
    }
    
    
  //loged in as student

    /**
     *
     * @param id
     * @return
     */
    public static String certificateByStudentIdUri(String id) 
    {
        return CERTIFICATES + "/" + STUDENTS + "/" + id;
    }
    
  //loged in as student

    /**
     *
     * @param sid
     * @param cid
     * @return
     */
    public static String certificateByStudentIdAndCourseIdUri(String sid, String cid) 
    {
        return CERTIFICATES + "/" + STUDENTS + "/" + sid + "/" + COURSES + "/" + cid;
    }

    //logged in as admin

    /**
     *
     * @param cid
     * @param aid
     * @return
     */
    public static String courseIdAdminId(String cid, String aid)
    {
    	return "courses/" + cid + "/admins/" + aid + "/";
    }
    
    /**
     *
     * @param id
     * @return
     */
    public static String courseProjectUri(String id) 
    {
        return COURSES + "/" + id + "/projects";
    }
    
    /**
     *
     * @param id
     * @return
     */
    public static String courseMaterialUri(String id) 
    {
        return COURSES + "/" + id + "/materials";
    }
    
    /**
     *
     * @return
     */
    public static String selectProjectIdTeacher()
    {
    	return GRADES + "/" + TEACHERS + "/" + "/" + COURSES + "/" + "/" + PROJECTS + STUDENTS +"/";
    }
    
    /**
     *
     * @param cid
     * @param pid
     * @return
     */
    public static String courseProjectsGrades(String cid, String pid)
    {
    	//return "courses/"+cid+"/projects/"+pid+"/students/";
    	return COURSES+"/" + cid + "/" + PROJECTS + "/" + pid + "/" + STUDENTS;
    }
    
    /**
     *
     * @param cid
     * @return
     */
    public static String CourseIdProjectIdUri(String cid) 
    {
        return COURSES + "/" + cid + "/" + PROJECTS + "/create";
    }
    
    /**
     *
     * @param cid
     * @param pid
     * @return
     */
    public static String CourseIdProjectIdUriUpdate(String cid, String pid) 
    {
        return COURSES + "/" + cid + "/" + PROJECTS + "/" + pid;
    }
    
    /**
     *
     * @param cid
     * @param pid
     * @return
     */
    public static String CourseIdProjectIdUriDelete(String cid, String pid) 
    {
        return COURSES + "/" + cid + "/" + PROJECTS + "/" + pid + "/delete";
    }
    
    /**
     *
     * @param cid
     * @return
     */
    public static String CourseIdMaterialIdUri(String cid) 
    {
        return COURSES + "/" + cid + "/" + MATERIALS + "/create";
    }
    
    /**
     *
     * @param cid
     * @param mid
     * @return
     */
    public static String CourseIdMaterialIdUriUpdate(String cid, String mid) 
    {
        return COURSES + "/" + cid + "/" + MATERIALS + "/" + mid;
    }
    
    /**
     *
     * @param cid
     * @param mid
     * @return
     */
    public static String CourseIdMaterialIdUriDelete(String cid, String mid) 
    {
        return COURSES + "/" + cid + "/" + MATERIALS + "/" + mid + "/delete";
    }
    /*
    /{courseId:[0-9]*}/projects/{projectId:[0-9]*}
    
    
    
    public static final String COURSE_SEARCH_STUDENT = "courses/searchStudent";
    
        //
	 //courses/{id}, <br>
	 // e.g. /courses/1
	 ///
	public static String courseIdUri(String id) 
        {
            return COURSES + "/" + id;
	}

	//
	 //courses?title={title}, <br>
	 // e.g. /courses?title=JAVA
	 //
	

        
        public static String courseSearchStudentUri(String id) 
        {
            return COURSE_SEARCH_STUDENT + "?student=" + id;
        }
        
        public static String teacherUri(String id) 
        {
            return TEACHERS + "/" + id;
	}
        
    */
}
