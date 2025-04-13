package org.isu_std.dao;

import org.isu_std.models.Admin;

import java.util.Optional;

public interface AdminDao {
    int getAdminID(String input);
    boolean insertAdmin(Admin admin);
    Optional<Admin> getOptionalAdmin(int adminId);
    boolean setAdminBarangayId(int barangayId, int adminId);
}
