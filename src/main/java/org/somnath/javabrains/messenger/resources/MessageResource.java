package org.somnath.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
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
import javax.ws.rs.core.UriInfo;

import org.somnath.javabrains.messenger.exception.DataNotFoundException;
import org.somnath.javabrains.messenger.model.Message;
import org.somnath.javabrains.messenger.model.Profile;
import org.somnath.javabrains.messenger.service.MessageService;

/*---------------------------CLASS CONTAINING ALL THE HTTP METHODS---------------------------------*/
@Path("/messages")
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@QueryParam("year") int year, @QueryParam("start") int start,
			@QueryParam("size") int size) {

		if (year > 0)
			return messageService.getAllMessagesForYear(year);
		if (start >= 0 && size > 0)
			return messageService.getAllMessagesForAnYear(start, size);
		return messageService.getAllMessages();
	}
	/*------------------------------Using Bean Param Annotations------------------------------------*/

	@GET
	@Path("/beanParams")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessagesUsingBeanParamAnnotations(@BeanParam MessageFilterBeanParam filterBean) {

		if (filterBean.getYear() > 0)
			return messageService.getAllMessagesForYear(filterBean.getYear());
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0)
			return messageService.getAllMessagesForAnYear(filterBean.getStart(), filterBean.getSize());
		return messageService.getAllMessages();
	}

	/*------------------------------Using Bean Param Annotations------------------------------------*/

	/*------------------------------Using Exceptions------------------------------------------------*/
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long id) {
		Message message = messageService.getMessage(id);
		if (message == null) {
			throw new DataNotFoundException("Message with id:" + " " + id + " " + "not Found");
		}
		return message;
	}
	/*------------------------------Using Exceptions------------------------------------------------*/

	@GET
	@Path("/hateoas/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessageWithLinks(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriInfoForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(message.getId()))
				.build().toString();
		return url;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build();
		return uri.toString();
	}

	private String getUriInfoForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class)
				.path(MessageResource.class, "getCommentResource").path(CommentResource.class)
				.resolveTemplate("messageId", message.getId()).build();
		return uri.toString();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}

	/*---------------------Sending Status Codes and Location Headers-------------------------------------
	 * Usage of it is when you want to send custom status code like for example 200 status code is when
	 * you have got successful response but for successful creation you have 201
	 * We then make use of Response object
	 * */
	@POST
	@Path("/{messageStatusCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(message.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri).status(Status.CREATED).entity(newMessage).build();
	}

	/*---------------------Sending Status Codes and location Headers--------------------------------------*/

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	/*---------------------Sending Status Codes and location Headers--------------------------------------*/

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}

	/*-----------------------Implementing Sub Resources-------------------------------------*/
	/*
	 * Comments follow Messages.But rather than describing all the Api's for
	 * comments here in this MessageResource we can create Sub-Resources for
	 * that
	 */
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

}
