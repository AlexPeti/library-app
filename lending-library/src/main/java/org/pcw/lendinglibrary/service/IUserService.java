package org.pcw.lendinglibrary.service;

import org.pcw.lendinglibrary.dto.UserDTO;
import org.pcw.lendinglibrary.model.User;
import org.pcw.lendinglibrary.service.exceptions.AuthenticationException;
import org.pcw.lendinglibrary.service.exceptions.BookNotFoundException;
import org.pcw.lendinglibrary.service.exceptions.UserNotFoundException;
import org.pcw.lendinglibrary.service.exceptions.UsernameAlreadyExistsException;

import java.util.List;

public interface IUserService {

    User register(UserDTO userDTO);

    User updateUser(UserDTO userDTO) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;

    List<User> getUsersByUsername(String username) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

    User getUserById(Long id) throws UserNotFoundException;

    User authenticateUser(String username, String password) throws AuthenticationException;

    boolean isUsernameTaken(UserDTO userDTO, String username) throws UsernameAlreadyExistsException;

//    void borrowBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException;
    void borrowBook(String username, String title) throws UserNotFoundException, BookNotFoundException;
    void returnBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException;
}
