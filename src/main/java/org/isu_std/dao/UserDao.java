package org.isu_std.dao;

import org.isu_std.models.Barangay;
import org.isu_std.models.User;

import java.util.Optional;

public interface UserDao {
    Optional<Integer> getUserId(String username);
    boolean addUser(User user);
    Optional<User> getOptionalUser(String username);
    boolean updateUserInfo(String chosenDetail, User user);
    boolean updateUserBarangay(User newUser);
}
