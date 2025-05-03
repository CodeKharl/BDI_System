package org.isu_std.user_brgy_select;

import org.isu_std.dao.BarangayDao;

public class BrgySelectFactory {
    public static BrgySelect createBrgySelect(BarangayDao barangayDao){
        var brgySelectService = new BrgySelectService(barangayDao);
        var brgySelectController = new BrgySelectController(brgySelectService);

        return new BrgySelect(brgySelectController);
    }
}
