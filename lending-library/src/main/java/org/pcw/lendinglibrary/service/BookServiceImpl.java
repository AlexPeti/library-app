package org.pcw.lendinglibrary.service;

import org.pcw.lendinglibrary.dao.IBookDAO;
import org.pcw.lendinglibrary.dto.BookDTO;
import org.pcw.lendinglibrary.model.Book;
import org.pcw.lendinglibrary.service.exceptions.BookNotFoundException;
import org.pcw.lendinglibrary.service.util.JPAHelper;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class BookServiceImpl implements IBookService {

    @Inject
    private IBookDAO bookDAO;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Book insertBook(BookDTO bookDTO) {
        try {
            JPAHelper.beginTransaction();

            Book book = modelMapper.map(bookDTO, Book.class);
            bookDAO.insert(book);

            JPAHelper.commitTransaction();
            return book;
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public Book updateBook(BookDTO bookDTO) throws BookNotFoundException {
        Book bookToUpdate;
        try {
            JPAHelper.beginTransaction();
            bookToUpdate = modelMapper.map(bookDTO,Book.class);
            if (bookDAO.getById(bookToUpdate.getId()) == null) {
                throw new BookNotFoundException("Book not found");
            }
            bookDAO.update(bookToUpdate);
            JPAHelper.commitTransaction();
            return bookToUpdate;
        } catch(BookNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteBook(Long id) throws BookNotFoundException {
        try {
            JPAHelper.beginTransaction();

            if (bookDAO.getById(id) == null) {
                throw new BookNotFoundException("Book not found");
            }
            bookDAO.delete(id);
            JPAHelper.commitTransaction();

        } catch (BookNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<Book> getBooksByTitle(String title) throws BookNotFoundException {
        List<Book> books;
        try {
            JPAHelper.beginTransaction();
            books = bookDAO.getByTitle(title);
            if (books.size() == 0) {
                throw new BookNotFoundException("Book not found");
            }
            JPAHelper.commitTransaction();
            return books;
        } catch (BookNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public Book getBookByTitle(String title) throws BookNotFoundException {
        Book book;
        try {
            JPAHelper.beginTransaction();
            book = bookDAO.getBookByTitle(title);
            if (book.getTitle() == null) {
                throw new BookNotFoundException("Book not found");
            }
            JPAHelper.commitTransaction();
            return book;
        } catch (BookNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public Book getBookById(Long id) throws BookNotFoundException {
        Book book;

        try {
            JPAHelper.beginTransaction();
            book = bookDAO.getById(id);

            if (book == null) {
                throw new BookNotFoundException("Book not found");
            }
            JPAHelper.commitTransaction();
            return book;
        } catch (BookNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }
}
