package omada5.ElearningProject.resourse;

import static omada5.ElearningProject.resourse.ElearningUri.MATERIALS;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import omada5.ElearningProject.domain.*;
import omada5.ElearningProject.service.*;
import omada5.ElearningProject.resourse.*;;
/**
 *
 * @author thegr
 */
@Path(MATERIALS)
public class MaterialResource extends AbstractResource
{
	 @Context 
	 UriInfo uriInfo;
	 
    /**
     *
     * @param materialInfo
     * @return
     */
    @POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 public Response createMaterial(MaterialInfo materialInfo) 
	 {
		 EntityManager em = getEntityManager();
		 Material material = materialInfo.getMaterial(em);
		 MaterialService materialService = new MaterialService(em);
		 material = materialService.save(material);
		 UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		 URI newMaterialUri = ub.path(Integer.toString(material.getMat_id())).build();
		 em.close();
		 return Response.created(newMaterialUri).build();
	 }
}
