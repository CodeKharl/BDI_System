package org.isu_std.dao;

import org.isu_std.models.Barangay;

import java.util.Optional;

public interface BarangayDao {
    Optional<Barangay> findOptionalBarangay(int barangayId);
    boolean insertBarangay(Barangay barangay);
    Optional<Integer> findBarangayId(Barangay barangay);
}
