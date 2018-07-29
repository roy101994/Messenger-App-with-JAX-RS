package org.somnath.javabrains.messenger.resources;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/*Param Annotations*/

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	/*
	 * Matrix Param format
	 * example:http://localhost:8080/messenger/webapi/injectdemo/matrixParam;
	 * param=value
	 * 
	 */
	@GET
	@Path("/matrixParam")
	public String getMatrixParamsUsingAnnotations(@MatrixParam("param") String matrixParam) {
		return "Matrix Param" + ":" + matrixParam;
	}

	/* Header Param example */
	@GET
	@Path("/headerParam")
	public String getHeaderParamUsingAnnotations(@HeaderParam("authSessionId") String authSessionId) {
		return "Header Param" + ":" + authSessionId;
	}

	/*---------------------- Context  Params Annotations----------------------------*/
	@GET
	@Path("/contextInformationforUri")
	public String getUriUsingContext(@Context UriInfo uriInfo) {
		MultivaluedMap<String, String> path = uriInfo.getQueryParameters();
		String temp = "";
		for (Entry<String, List<String>> map : path.entrySet()) {
			temp += map.getValue() + "\r";
		}
		return "Path:" + temp;
	}

	@GET
	@Path("/contextInformationforHeaders")
	public String getHeaderInformationUsingContext(@Context HttpHeaders headers) {
		return "" + headers.getMediaType().toString();
	}

}
