package org.isu_std.models.model_builders;

import org.isu_std.models.Barangay;

public class BarangayBuilder {
    private int barangayId;
    private String barangayName;
    private String municipality;
    private String province;
    private int barangayPin;

    protected BarangayBuilder(){}

    public BarangayBuilder barangayId(int barangayId){
        this.barangayId = barangayId;
        return this;
    }

    public BarangayBuilder barangayName(String barangayName){
        this.barangayName = barangayName;
        return this;
    }

    public BarangayBuilder municipality(String municipality){
        this.municipality = municipality;
        return this;
    }

    public BarangayBuilder province(String province){
        this.province = province;
        return this;
    }

    public BarangayBuilder barangayPin(int barangayPin){
        this.barangayPin = barangayPin;
        return this;
    }

    public int getBarangayId() {
        return barangayId;
    }

    public String getBarangayName() {
        return barangayName;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getProvince() {
        return province;
    }

    public int getBarangayPin() {
        return barangayPin;
    }

    public Barangay build(){
        return new Barangay(barangayId, barangayName, municipality, province, barangayPin);
    }
}
