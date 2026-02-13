package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Message;
import util.JpaUtil;

import java.util.List;

public class MessageDAO {

    public Message save(Message message) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            em.getTransaction().begin();

            if (message.getSender() != null && message.getSender().getId() != null){
                message.setSender(em.merge(message.getSender()));
            }
            if (message.getReceiver() != null && message.getReceiver().getId() != null) {
                message.setReceiver(em.merge(message.getReceiver()));
            }
            em.persist(message);
            em.getTransaction().commit();
            return message;
        } catch (Exception e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Echec sauvegarde message", e);
        }finally {
            em.close();
        }
    }

    public Message findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Message.class, id);
        }finally {
            em.close();
        }
    }

    public List<Message> findConversation(Long userId1, Long userId2) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT m FROM Message m " +
                    "WHERE (m.sender.id =: user1 AND m.receiver.id =: user2) " +
                    "OR (m.sender.id =: user2 AND m.receiver.id =: user1 ) " +
                    "ORDER BY m.date_time ASC";

            TypedQuery<Message> query = em.createQuery(jpql, Message.class);
            query.setParameter("user1", userId1);
            query.setParameter("user2", userId2);

            return query.getResultList();
        }finally {
            em.close();
        }
    }

    public List<Message> findUnreadByUserId(Long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            String jpql = "SELECT m FROM Message m " +
                    "WHERE m.receiver.id =: userId " +
                    "AND m.isRead = false " + " ORDER By m.date_time DESC";

            TypedQuery<Message> query = em.createQuery(jpql, Message.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    public List<Message> findAllByUserId(Long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT m FROM Message m " +
                    "WHERE m.sender.id =: userId OR m.receiver.id =: userId " +
                    "ORDER BY m.date_time DESC ";

            TypedQuery<Message> query = em.createQuery(jpql, Message.class);
            query.setParameter("userId", userId);

            return query.getResultList();
        }finally {
            em.close();
        }
    }

    public List<Message> searchByKeyword(String keyword, Long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            String jpql = "SELECT m FROM Message m " +
                    "WHERE (m.sender.id =: userId OR m.receiver.id =: userId) " +
                    "AND LOWER(m.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "ORDER BY m.date_time DESC ";

            TypedQuery<Message> query = em.createQuery(jpql, Message.class);
            query.setParameter("userId", userId);
            query.setParameter("keyword", keyword);

            return query.getResultList();
        }finally {
            em.close();
        }
    }

    public Long countUnreadByUserId(Long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            String jpql = "SELECT COUNT(m) FROM Message m " +
                    "WHERE m.receiver.id = : userId AND m.isRead = false ";

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("userId", userId);

            return query.getSingleResult();
        }finally {
            em.close();
        }
    }

    public void markAsRead(Long messageId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Message message = em.find(Message.class, messageId);
            if (message != null && !message.getIsRead()) {
                message.setIsRead(true);
                em.merge(message);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Echec marquage lu", e);
        }finally {
            em.close();
        }
    }

    public void markAllAsReadBetweenUsers(Long receiverId, Long senderId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            String jpql = "UPDATE Message m SET m.isRead = true " +
                    "WHERE m.receiver.id = :receiverId " +
                    "AND m.sender.id = :senderId " +
                    "AND m.isRead = false";

            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("receiverId", receiverId);
            query.setParameter("senderId", senderId);


            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Échec marquage tous comme lus", e);
        } finally {
            em.close();
        }
    }

    public void delete(Long messageId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Message message = em.find(Message.class, messageId);
            if (message != null) {
                em.remove(message);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Échec suppression message", e);
        } finally {
            em.close();
        }
    }

    public void deleteConversation(Long userId1, Long userId2) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            String jpql = "DELETE FROM Message m " +
                    "WHERE (m.sender.id = :user1 AND m.receiver.id = :user2) " +
                    "OR (m.sender.id = :user2 AND m.receiver.id = :user1)";

            em.createQuery(jpql)
                    .setParameter("user1", userId1)
                    .setParameter("user2", userId2)
                    .executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Échec suppression conversation", e);
        } finally {
            em.close();
        }
    }
}
