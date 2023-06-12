package org.pcw.lendinglibrary.service;

import org.pcw.lendinglibrary.dto.BookDTO;
import org.pcw.lendinglibrary.model.Book;
import org.pcw.lendinglibrary.service.exceptions.BookNotFoundException;

import java.util.List;

public interface IBookService {

    Book insertBook(BookDTO bookDTO);
    Book updateBook(BookDTO bookDTO) throws BookNotFoundException;
    void deleteBook(Long id) throws BookNotFoundException;
    List<Book> getBooksByTitle(String title) throws BookNotFoundException;
    Book getBookByTitle(String title) throws BookNotFoundException;
    Book getBookById(Long id) throws BookNotFoundException;
}
