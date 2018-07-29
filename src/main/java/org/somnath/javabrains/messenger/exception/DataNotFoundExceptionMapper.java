package org.somnath.javabrains.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.somnath.javabrains.messenger.model.ErrorMessage;

/*Usage of this Provider Annotation is to register this bean to JAX-RS.
 * So that JAX-RS can recognize this exception class.
 * If a DataNotFound Exception is thrown now onwards the dafault Tomcat error Message will not come
 * */
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, "http://test.com");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}

}
