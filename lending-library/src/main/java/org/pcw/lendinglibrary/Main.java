package org.pcw.lendinglibrary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.pcw.lendinglibrary.model.Book;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");
        EntityManager em = emf.createEntityManager();


        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Cecil Martin");
        book.setBorrower(null);

        Book book2 = new Book();
        book2.setTitle("The Pragmatic Programmer");
        book2.setAuthor("Dave Thomas");
        book2.setBorrower(null);

        Book book3 = new Book();
        book3.setTitle("Introduction To Algorithms");
        book3.setAuthor("Ronald Rivest");
        book3.setBorrower(null);

        em.getTransaction().begin();

        em.persist(book);
        em.persist(book2);
        em.persist(book3);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
