package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import model.User;
import util.JpaUtil;

import java.util.List;

public class UserDAO {

    public User findByEmail(String email) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email =:email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            em.close();
        }
    }

    public User create(User user) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return user;
    }

    public User findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            return em.find(User.class, id);
        }finally {
            em.close();
        }
    }

    public List<User> findALl() {
        EntityManager em = JpaUtil.getEntityManager();
        try{
            String jpql = "SELECT u FROM User u ORDER BY u.firstName ASC, u.lastName ASC";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    public User update(User user) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User updated = em.merge(user);
            em.getTransaction().commit();
            return updated;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Echec mise à jour utilisateur", e);
        }finally {
            em.close();
        }
    }

    public void updatePassword(Long userId, String newPasswordHash) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setPassword(newPasswordHash);
                em.merge(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Echec mise à jour mot de passe", e);
        }finally {
            em.close();
        }
    }

    public void delete (Long userId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur DAO - Echec suppression utilisateur",e);
        }finally {
            em.close();
        }
    }

    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM User u WHERE u.email = :email";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("email", email.toLowerCase().trim());

            Long count = query.getSingleResult();
            return count != null && count > 0;
        } catch (NoResultException e) {
            return false;
        }
        catch (Exception e) {
            System.err.println("Erreur lors de la vérification de l'email: " + e.getMessage());
            return false;
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    /*public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("the user can't be null");
        }

        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.flush();
            em.getTransaction().commit();
            return user;
        }catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            String errorMessage = "user can't be saved";

            if (e.getMessage() != null) {
                if (e.getMessage().contains("Duplicate en try") || e.getMessage().contains("unique constraint")) {
                    if (e.getMessage().contains("email")) {
                        errorMessage = "Violation de contrainte d'unicité";
                    }
                }
            }
            throw new RuntimeException(errorMessage, e);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde de l'utilisateur" + e.getMessage(),e);
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }*/
}
