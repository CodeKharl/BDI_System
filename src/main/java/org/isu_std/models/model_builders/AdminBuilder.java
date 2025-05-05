package org.isu_std.models.model_builders;

import org.isu_std.models.Admin;

public class AdminBuilder {
    private int adminId;
    private String adminName;
    private int adminPin;
    private int barangayId;

    protected AdminBuilder(){}

    public AdminBuilder adminId(int adminId){
        this.adminId = adminId;
        return this;
    }

    public AdminBuilder adminName(String adminName){
        this.adminName = adminName;
        return this;
    }

    public AdminBuilder adminPin(int adminPin){
        this.adminPin = adminPin;
        return this;
    }

    public AdminBuilder barangayId(int barangayId){
        this.barangayId = barangayId;
        return this;
    }

    public Admin build(){
        return new Admin(adminId, adminName, adminPin, barangayId);
    }

    public int getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public int getAdminPin() {
        return adminPin;
    }

    public int getBarangayId() {
        return barangayId;
    }
}
