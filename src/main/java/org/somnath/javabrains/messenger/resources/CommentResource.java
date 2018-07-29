package org.somnath.javabrains.messenger.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.somnath.javabrains.messenger.model.Comment;
import org.somnath.javabrains.messenger.service.CommentService;

@Path("/")
public class CommentResource {

	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return commentService.getAllComments(messageId);
	}

	@POST
	public Comment addMessage(@PathParam("messageId") long messageId, Comment comment) {
		return commentService.addComment(messageId, comment);
	}

	@PUT
	@Path("/{commentId}")
	public Comment updateMessage(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId,
			Comment comment) {
		comment.setId(commentId);
		return commentService.updateComment(messageId, comment);
	}

	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long id,
			@PathParam("commentId") long commentId) {
		commentService.removeComment(messageId, commentId);
	}

}
