package com.pcw.lendinglibrary.service;

import com.pcw.lendinglibrary.dao.IBookDAO;
import com.pcw.lendinglibrary.dao.IUserDAO;
import com.pcw.lendinglibrary.dto.UserDTO;
import com.pcw.lendinglibrary.model.Book;
import com.pcw.lendinglibrary.model.User;
import com.pcw.lendinglibrary.service.exceptions.AuthenticationException;
import com.pcw.lendinglibrary.service.exceptions.BookNotFoundException;
import com.pcw.lendinglibrary.service.exceptions.UserNotFoundException;
import com.pcw.lendinglibrary.service.exceptions.UsernameAlreadyExistsException;
import com.pcw.lendinglibrary.service.util.JPAHelper;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class UserServiceImpl implements IUserService {

    @Inject
    private IUserDAO userDAO;

    @Inject
    private IBookDAO bookDAO;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public User register(UserDTO userDTO) {
        try {
            JPAHelper.beginTransaction();

            User user = modelMapper.map(userDTO, User.class);

            userDAO.insert(user);

            JPAHelper.commitTransaction();
            return user;
        } catch (Exception e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public User updateUser(UserDTO userDTO) throws UserNotFoundException {
        User userToUpdate;
        try {
            JPAHelper.beginTransaction();
            userToUpdate = modelMapper.map(userDTO, User.class);
            if (userDAO.getById(userToUpdate.getId()) == null) {
                throw new UserNotFoundException("User not found");
            }
            userDAO.update(userToUpdate);
            JPAHelper.commitTransaction();
            return userToUpdate;
        } catch (UserNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        try {
            JPAHelper.beginTransaction();

            if (userDAO.getById(id) == null) {
                throw new UserNotFoundException("User not found");
            }
            userDAO.delete(id);
            JPAHelper.commitTransaction();

        } catch (UserNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<User> getUsersByUsername(String username) throws UserNotFoundException {
        List<User> users;
        try {
            JPAHelper.beginTransaction();
            users = userDAO.getByUsername(username);
            if (users.size() == 0) {
                throw new UserNotFoundException("User not found");
            }
            JPAHelper.commitTransaction();
            return users;
        } catch (UserNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        try {
            JPAHelper.beginTransaction();
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
            JPAHelper.commitTransaction();
            return user;
        } catch (UserNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        User user;

        try {
            JPAHelper.beginTransaction();
            user = userDAO.getById(id);

            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
            JPAHelper.commitTransaction();
            return user;
        } catch (UserNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public User authenticateUser(String username, String password) throws AuthenticationException {
        try {
            JPAHelper.beginTransaction();

            User user = userDAO.getUserByUsername(username);

            if (user == null || !userDAO.isAuthenticated(username, password)) {
                throw new AuthenticationException("Invalid username or password");
            }

            JPAHelper.commitTransaction();
            return user;
        } catch (AuthenticationException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public boolean isUsernameTaken(UserDTO userDTO, String username) throws UsernameAlreadyExistsException {
        try {
            JPAHelper.beginTransaction();
            List<User> userList = userDAO.getByUsername(username);
            for (User user : userList) {
                if (user.getUsername().equals(username)) {
                    throw new UsernameAlreadyExistsException("Username already exists: " + username);
                }
            }
            return false;
        } catch (UsernameAlreadyExistsException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        }  finally {
            JPAHelper.commitTransaction();
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void borrowBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException {
        try {
            JPAHelper.beginTransaction();
            User user = userDAO.getById(userId);
            Book book = bookDAO.getById(bookId);

            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
            if (book == null) {
                throw new BookNotFoundException("Book not found");
            }

            if (user.getBorrowedBook() == null && book.getBorrower() == null) {
                user.setBorrowedBook(book);
                book.setBorrower(user);

                // Update the state of the user and book entities
                userDAO.update(user);
                bookDAO.update(book);
            }
            JPAHelper.commitTransaction();
        } catch (UserNotFoundException | BookNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void returnBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException {
        try {
            JPAHelper.beginTransaction();

            User user = userDAO.getById(userId);
            Book book = bookDAO.getById(bookId);

            if (user == null) {
                throw new UserNotFoundException("User not found");
            }
            if (book == null) {
                throw new BookNotFoundException("Book not found");
            }

            if (user.getBorrowedBook() == book && book.getBorrower() == user) {
                user.setBorrowedBook(null);
                book.setBorrower(null);

                // Update the state of the user and book entities
                userDAO.update(user);
                bookDAO.update(book);
            }
            JPAHelper.commitTransaction();
        } catch (UserNotFoundException | BookNotFoundException e) {
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }
}
