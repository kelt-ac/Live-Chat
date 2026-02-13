package services;

import dao.MessageDAO;
import dao.UserDAO;
import model.Message;
import model.User;
import websocket.ChatEndpoint;

import java.time.LocalDateTime;
import java.util.Date;

public class MessageService {

    private final MessageDAO messageDAO;
    private final UserDAO userDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.userDAO = new UserDAO();
    }

    public MessageService(MessageDAO messageDAO, UserDAO userDAO) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
    }

    public Message sendMessage(Long senderId, Long receiverId, String content) {
        if (senderId == null || receiverId == null) {
            throw new IllegalArgumentException("the sender and the receiver are required");
        }

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("the message`s content can't be empty!");
        }

        User sender = userDAO.findById(senderId);
        User receiver = userDAO.findById(receiverId);

        if (sender == null) {
            throw new RuntimeException("sender doesn't exist");
        }
        if (receiver == null) {
            throw new RuntimeException("reciever doesn't exist");
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content.trim());
        message.setDate_time(new Date());
        message.setIsRead(false);

        Message savedMessage = messageDAO.save(message);

        //Notification
        try {
            String notification = String.format(
                    "{\"type\":\"NEW_MESSAGE\",\"messageId\":%d,\"from\":\"%s %s\",\"content\":\"%s\"}",
                    savedMessage.getId(),
                    sender.getFirstName(),
                    sender.getLastName(),
                    content.replace("\"", "\\\"")
            );
            ChatEndpoint.sendToUser(receiverId.intValue(), notification);
        } catch (Exception e) {
            System.err.println("Erreur notification Web Socket: " + e.getMessage());
        }
        return savedMessage;
    }
}
