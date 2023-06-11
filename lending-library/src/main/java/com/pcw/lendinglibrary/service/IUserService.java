package com.pcw.lendinglibrary.service;

import com.pcw.lendinglibrary.dto.UserDTO;
import com.pcw.lendinglibrary.model.User;
import com.pcw.lendinglibrary.service.exceptions.*;

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

    void borrowBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException;

    void returnBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException;
}
