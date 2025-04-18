package org.isu_std.models;

import org.isu_std.io.Util;

import java.io.File;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;

public record Document(String documentName, double price, String requirements, File documentFile) {
    @Override
    public String toString() {
        return "%s - %.2f - %s -> %s".formatted(documentName, price, requirements, documentFile.getName());
    }

    public String getDetails(){
        return "%s - %.2f - %s".formatted(documentName, price, requirements);
    }

    public void printDetailsWithDocumentFile() {
        Util.printSectionTitle("Document Information");
        Util.printInformation("Document Name -> %s".formatted(documentName));
        Util.printInformation("Price -> %.2f".formatted(price));
        Util.printInformation("Requirements -> %s".formatted(requirements));
        Util.printInformation("Document File -> %s".formatted(documentFile.getName()));
    }

    public void printDetails(){
        Util.printInformation("Document Name -> %s".formatted(documentName));
        Util.printInformation("Price -> %.2f".formatted(price));
        Util.printInformation("Requirements -> %s".formatted(requirements));
    }

    public String[] getRequirementsArr() {
        return requirements.split(" : ");
    }
}
