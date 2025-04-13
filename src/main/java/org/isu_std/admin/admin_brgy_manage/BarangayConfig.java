package org.isu_std.admin.admin_brgy_manage;

public enum BarangayConfig {
    MIN_BARANGAY_NAME_LENGTH(2),
    MIN_MUNICIPALITY_PROVINCE_LENGTH(4),
    MIN_PIN_LENGTH(6);

    private final int value;

    BarangayConfig(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
