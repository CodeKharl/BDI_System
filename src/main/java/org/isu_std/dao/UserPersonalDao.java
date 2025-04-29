package org.isu_std.dao;

import org.isu_std.models.UserPersonal;

import java.util.Optional;

public interface UserPersonalDao {
    Optional<UserPersonal> getOptionalUserPersonal(int userId);
    boolean addUserPersonal(int userId, UserPersonal userPersonal);
    boolean modifyUserPersonal(int userId, UserPersonal userPersonal);
}
