package org.isu_std.dao;

import org.isu_std.models.User;

import java.util.Optional;

public interface UserDao {
    int getUserId(String username);
    boolean addUser(User user);
    Optional<User> getOptionalUser(String username);
}
