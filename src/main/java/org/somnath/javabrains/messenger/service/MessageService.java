package org.somnath.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.somnath.javabrains.messenger.database.DatabaseClass;
import org.somnath.javabrains.messenger.model.Message;

/*Implementation methods for the REST API's*/
public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		messages.put(1L, new Message(1, "Hello World", "Koushik"));
		messages.put(2L, new Message(2, "Hi", "Somnath"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}

	public Message getMessage(long id) {
		return messages.get(id);
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0)
			return null;
		messages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(long id) {
		return messages.remove(id);
	}

	/* Pagination Support and Filtering */
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForAnYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year)
				messagesForAnYear.add(message);
		}
		return messagesForAnYear;
	}

	public List<Message> getAllMessagesForAnYear(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if (start + size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start + size);

	}
	/* Pagination Support and Filtering */
}
