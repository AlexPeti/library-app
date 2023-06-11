package com.pcw.lendinglibrary.dao;

import com.pcw.lendinglibrary.model.User;

import java.util.List;

public interface IUserDAO {

    User insert(User user);

    User update(User user);

    void delete(Long id);

    List<User> getByUsername(String username);

    User getUsersByUsername(String username);

    User getUserByUsername(String username);

    User getById(Long id);

    boolean isAuthenticated(String username, String password);
}
