package controller;

import services.MessageService;
import services.UserService;
import model.Message;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/messages")
public class MessageServlet extends HttpServlet {
    private MessageService messageService = new MessageService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");

        String action = request.getParameter("action");
        String contactId = request.getParameter("contactId");

        if ("conversation".equals(action) && contactId != null) {
            // Afficher une conversation spécifique
            try {
                Long contactUserId = Long.parseLong(contactId);
                User contact = userService.getUserById(contactUserId);

                if (contact != null) {
                    List<Message> conversation = messageService.getConversation(
                            currentUser.getId(), contactUserId);

                    // Marquer les messages comme lus
                    for (Message msg : conversation) {
                        if (msg.getReceiver().getId().equals(currentUser.getId())
                                && !msg.getIsRead()) {
                            messageService.markMessageAsRead(msg.getId());
                        }
                    }

                    request.setAttribute("contact", contact);
                    request.setAttribute("messages", conversation);
                }
            } catch (NumberFormatException e) {
                // Ignorer
            }
        }

        // Récupérer tous les utilisateurs pour la liste de contacts
        List<User> contacts = userService.getAllUsersExcept(currentUser.getId());
        List<Message> receivedMessages = messageService.getReceivedMessages(currentUser.getId());
        int unreadCount = messageService.getUnreadCount(currentUser.getId());

        request.setAttribute("contacts", contacts);
        request.setAttribute("receivedMessages", receivedMessages);
        request.setAttribute("unreadCount", unreadCount);
        request.setAttribute("currentUser", currentUser);

        request.getRequestDispatcher("/messages.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("send".equals(action)) {
            String receiverId = request.getParameter("receiverId");
            String content = request.getParameter("content");

            if (receiverId != null && content != null && !content.trim().isEmpty()) {
                try {
                    messageService.sendMessage(currentUser.getId(),
                            Long.parseLong(receiverId),
                            content);
                } catch (NumberFormatException e) {
                    // Ignorer
                }
            }

            // Rediriger vers la conversation
            response.sendRedirect(request.getContextPath() +
                    "/messages?action=conversation&contactId=" + receiverId);
        }
    }
}