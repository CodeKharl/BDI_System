package org.isu_std.models;

import org.isu_std.io.Util;

public record Barangay(
        int barangayId, String barangayName, String municipality, String province, int barangayPin
){
    public void printFullBrgyNameOnly(){
        String fullName = getBrgyFullName();

        Util.printSubSectionTitle(
                "Barangay Information (Barangay Full Name)"
        );
        Util.printInformation("%s".formatted(fullName));
    }

    public String getBrgyFullName(){
        return "%s, %s, %s".formatted(barangayName, municipality, province);
    }

    private String getBrgyPinAsterisk(){
        String brgyPinStr = String.valueOf(barangayPin);
        return "*".repeat(brgyPinStr.length());
    }
}
