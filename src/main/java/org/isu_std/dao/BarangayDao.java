package org.isu_std.dao;

import org.isu_std.models.Barangay;

import java.util.Optional;

public interface BarangayDao {
    Optional<Barangay> getOptionalBarangay(int barangayId);
    boolean addBarangay(Barangay barangay);
    int getBarangayId(Barangay barangay);
}
