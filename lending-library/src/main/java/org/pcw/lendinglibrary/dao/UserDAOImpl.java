package org.pcw.lendinglibrary.dao;

import org.pcw.lendinglibrary.model.User;
import org.pcw.lendinglibrary.service.util.JPAHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class UserDAOImpl implements IUserDAO {

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

    @Override
    public User insert(User user) {
        EntityManager em = getEntityManager();
        em.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        getEntityManager().merge(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        User userToDelete = em.find(User.class, id);
        em.remove(userToDelete);
    }

    @Override
    public List<User> getByUsername(String username) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> selectQuery = builder.createQuery(User.class);
        Root<User> root = selectQuery.from(User.class);
        ParameterExpression<String> uUsername = builder.parameter(String.class);
        selectQuery.select(root).where((builder.like(root.get("username"),uUsername)));

        TypedQuery<User> query = getEntityManager().createQuery(selectQuery);
        query.setParameter(uUsername, username + "%");
        return query.getResultList();
    }

    @Override
    public User getUsersByUsername(String username) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> selectQuery = builder.createQuery(User.class);
        Root<User> root = selectQuery.from(User.class);
        ParameterExpression<String> uUsername = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("username"), uUsername));

        TypedQuery<User> query = getEntityManager().createQuery(selectQuery);
        query.setParameter(uUsername, username + "%");
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public User getUserByUsername(String username) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> selectQuery = builder.createQuery(User.class);
        Root<User> root = selectQuery.from(User.class);
        ParameterExpression<String> uUsername = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("username"), uUsername));

        TypedQuery<User> query = getEntityManager().createQuery(selectQuery);
        query.setParameter(uUsername, username + "%");
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public User getById(Long id) {
        EntityManager em = getEntityManager();
        return em.find(User.class, id);
    }

    @Override
    public boolean isAuthenticated(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            String hashedPassword = user.getPassword();
            return BCrypt.checkpw(password, hashedPassword);
        }
        return false;
    }
}
