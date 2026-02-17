package websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class ChatEndpoint {

    // Stocker les sessions WebSocket par userId
    private static Map<Long, Session> userSessions = new ConcurrentHashMap<>();
    private static Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Nouvelle connexion WebSocket: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            // Le message reçu est au format JSON
            JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
            String type = jsonMessage.get("type").getAsString();

            if ("register".equals(type)) {
                // Enregistrer la session avec l'userId
                Long userId = jsonMessage.get("userId").getAsLong();
                userSessions.put(userId, session);
                System.out.println("Utilisateur " + userId + " enregistré pour WebSocket");

            } else if ("message".equals(type)) {
                // Envoyer un message en temps réel
                Long senderId = jsonMessage.get("senderId").getAsLong();
                Long receiverId = jsonMessage.get("receiverId").getAsLong();
                String content = jsonMessage.get("content").getAsString();

                // Créer la notification
                JsonObject notification = new JsonObject();
                notification.addProperty("type", "new_message");
                notification.addProperty("senderId", senderId);
                notification.addProperty("content", content);
                notification.addProperty("timestamp", System.currentTimeMillis());

                // Envoyer au destinataire si connecté
                Session receiverSession = userSessions.get(receiverId);
                if (receiverSession != null && receiverSession.isOpen()) {
                    receiverSession.getBasicRemote().sendText(gson.toJson(notification));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        // Retirer la session de la map
        userSessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
        System.out.println("Connexion WebSocket fermée: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    // Méthode utilitaire pour envoyer une notification à un utilisateur spécifique
    public static void sendNotification(Long userId, JsonObject notification) {
        Session session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(gson.toJson(notification));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}