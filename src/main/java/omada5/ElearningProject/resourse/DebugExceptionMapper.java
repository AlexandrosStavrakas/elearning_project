package omada5.ElearningProject.resourse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 *
 * @author thegr
 */
@Provider
public class DebugExceptionMapper implements ExceptionMapper<Exception> 
{

    /**
     *
     * @param exception
     * @return
     */
    @Override
    public Response toResponse(Exception exception) 
    {
        exception.printStackTrace();
        return Response.serverError().entity(exception.getMessage()).build();
    } 
}