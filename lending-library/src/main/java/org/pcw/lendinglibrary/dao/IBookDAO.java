package org.pcw.lendinglibrary.dao;

import org.pcw.lendinglibrary.model.Book;

import java.util.List;

public interface IBookDAO {

    Book insert(Book book);
    Book update(Book book);
    void delete(long id);
    List<Book> getByTitle(String title);
    Book getBookByTitle(String title);
    Book getById(Long id);
}
