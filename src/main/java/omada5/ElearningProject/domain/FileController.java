package omada5.ElearningProject.domain;

import java.io.File;
import java.io.FileInputStream;

/**
 * The FileController Class
 * @author thegr
 */
public class FileController 
{

    /**
     * Default constructor 
     */
    public FileController(){}
    
    /**
     * uploads a file
     * @param file
     * @return a file into array of bytes
     */
    public byte[] FileUpolader(File file)
    {
        byte[] bFile = new byte[(int) file.length()];

        try 
        {
	     FileInputStream fileInputStream = new FileInputStream(file);
	     //convert file into array of bytes
	     fileInputStream.read(bFile);
	     fileInputStream.close();
        } 
        catch (Exception e) 
        {
	     e.printStackTrace();
        }
        return bFile;
    }
    
}
