package org.isu_std.models;

import org.isu_std.io.Util;

public record Barangay(
        int barangayId, String barangayName, String municipality, String province, int barangayPin
){
    public void printStats(){
        String fullName = getBrgyFullName();
        String brgyPinAsterisk = getBrgyPinAsterisk();

        Util.printSubSectionTitle(
                "Barangay Info (Barangay ID - Barangay Full Name - Barangay Pin(*))"
        );
        Util.printInformation("%d - %s - %s".formatted(barangayId, fullName, brgyPinAsterisk));
    }

    public String getBrgyFullName(){
        return "%s, %s, %s".formatted(barangayName, municipality, province);
    }

    private String getBrgyPinAsterisk(){
        String brgyPinStr = String.valueOf(barangayPin);
        return "*".repeat(brgyPinStr.length());
    }
}
