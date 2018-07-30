package org.somnath.javabrains.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/*----------------------POJO-------------------------------*/
@XmlRootElement
public class Message {

	private long id;
	private String message;
	private Date created;
	private String author;
	private Map<Long, Comment> comments = new HashMap<>();
	private List<Link> links = new ArrayList<Link>();

	/*
	 * Why No Args constructors Required If your bean has no contructors then no
	 * need for no-args constructors. But if your bean has constructor with
	 * arguments then JVM will not provide you with default constructor
	 * implicitly.You have to provide default constructor explicitly so that
	 * Jackson (very popular and efficient java based library to serialize or
	 * map java objects to JSON and vice versa) can instantiate bean using
	 * Reflection
	 */
	public Message() {

	}

	public Message(long id, String message, String author) {
		super();
		this.id = id;
		this.message = message;
		this.created = new Date();
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/*----------------------POJO-------------------------------*/

	/*
	 * The usage of this annotation is that we don't want the comments list to
	 * show up everytime when we call message API's
	 */
	@XmlTransient
	public Map<Long, Comment> getComments() {
		return comments;
	}

	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}

	public List<Link> getLink() {
		return links;
	}

	public void setLink(List<Link> link) {
		this.links = link;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}

	/*----------------------POJO-------------------------------*/

}
