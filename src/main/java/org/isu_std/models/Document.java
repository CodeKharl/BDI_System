package org.isu_std.models;

import org.isu_std.io.Util;

import java.io.File;

public record Document(String documentName, double price, String requirements, File documentFile) {
    @Override
    public String toString() {
        return "%s - %.2f - %s -> %s".formatted(documentName, price, requirements, documentFile.getName());
    }

    public void printlnStats() {
        Util.printSectionTitle("Document Information");
        Util.printInformation("Document Name -> %s".formatted(documentName));
        Util.printInformation("Price -> %.2f".formatted(price));
        Util.printInformation("Requirements -> %s".formatted(requirements));
        Util.printInformation("Document File -> %s".formatted(documentFile.getName()));
    }

    public String[] getRequirementsArr() {
        return requirements.split(" : ");
    }
}
