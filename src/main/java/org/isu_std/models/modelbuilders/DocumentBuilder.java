package org.isu_std.models.modelbuilders;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.io.Util;
import org.isu_std.models.Document;

import java.io.File;
import java.util.Arrays;

public class DocumentBuilder {
    private String documentName;
    private double price;
    private String requirements;
    private File documentFile;

    public DocumentBuilder documentName(String documentName){
        this.documentName = documentName;
        return this;
    }

    public DocumentBuilder price(double price){
        this.price = price;
        return this;
    }

    public DocumentBuilder requirements(String requirements){
        this.requirements = requirements;
        return this;
    }

    public DocumentBuilder documentFile(File documentFile){
        this.documentFile = documentFile;
        return this;
    }

    public Document build(){
        return new Document(
                this.documentName,
                this.price,
                this.requirements,
                this.documentFile
        );
    }

    public String getDocumentName() {
        return documentName;
    }

    public double getPrice() {
        return price;
    }

    public String getRequirements() {
        return requirements;
    }

    public File getDocumentFile() {
        return documentFile;
    }

    public void printStatus(){
        Object[] objects = {documentName, price, requirements, documentFile.getName()};
        String[] labels = DocumentManageCodes.DOCUMENT_INFO.getArrCode();

        for(int i = 0; i < labels.length; i++){
            Util.printInformation("%s -> %s".formatted(labels[i], objects[i]));
        }
    }
}
