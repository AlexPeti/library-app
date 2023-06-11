package com.pcw.lendinglibrary.dao;

import com.pcw.lendinglibrary.model.Book;
import com.pcw.lendinglibrary.service.util.JPAHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class BookDAOImpl implements IBookDAO {

    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

    @Override
    public Book insert(Book book) {
        EntityManager em = getEntityManager();
        em.persist(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        getEntityManager().merge(book);
        return book;
    }

    @Override
    public void delete(long id) {
        EntityManager em = getEntityManager();
        Book bookToDelete = em.find(Book.class,id);
        em.remove(bookToDelete);
    }

    @Override
    public List<Book> getByTitle(String title) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Book> selectQuery = builder.createQuery(Book.class);
        Root<Book> root = selectQuery.from(Book.class);
        ParameterExpression<String> tTitle = builder.parameter(String.class);
        selectQuery.select(root).where((builder.like(root.get("title"),tTitle)));

        TypedQuery<Book> query = getEntityManager().createQuery(selectQuery);
        query.setParameter(tTitle, title + "%");
        return query.getResultList();
    }

    @Override
    public Book getBookByTitle(String title) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Book> selectQuery = builder.createQuery(Book.class);
        Root<Book> root = selectQuery.from(Book.class);
        ParameterExpression<String> tTitle = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("title"), tTitle));

        TypedQuery<Book> query = getEntityManager().createQuery(selectQuery);
        query.setParameter(tTitle, title + "%");
        List<Book> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
        return em.find(Book.class,id);
    }
}
