package org.isu_std.dao;

import org.isu_std.models.Admin;

import java.util.Optional;

public interface AdminDao {
    Optional<Integer> findAdminIDByName(String adminName);
    boolean insertAdmin(Admin admin);
    Optional<Admin> findOptionalAdmin(int adminId);
    boolean updateAdminBrgyId(int barangayId, int adminId);
    boolean updateAdminInfo(String chosenAttributeName, Admin admin);
    boolean deleteAdmin(int adminId);
}
