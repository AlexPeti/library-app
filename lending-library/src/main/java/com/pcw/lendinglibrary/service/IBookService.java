package com.pcw.lendinglibrary.service;

import com.pcw.lendinglibrary.dto.BookDTO;
import com.pcw.lendinglibrary.model.Book;
import com.pcw.lendinglibrary.service.exceptions.BookNotFoundException;

import java.util.List;

public interface IBookService {

    Book insertBook(BookDTO bookDTO);
    Book updateBook(BookDTO bookDTO) throws BookNotFoundException;
    void deleteBook(Long id) throws BookNotFoundException;
    List<Book> getBooksByTitle(String title) throws BookNotFoundException;
    Book getBookByTitle(String title) throws BookNotFoundException;
    Book getBookById(Long id) throws BookNotFoundException;
}
