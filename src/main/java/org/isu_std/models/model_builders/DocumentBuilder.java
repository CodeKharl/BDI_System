package org.isu_std.models.model_builders;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.io.Util;
import org.isu_std.models.Document;

import java.io.File;

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

    public Object[] getValues(){
        return new Object[]{
                documentName, price, requirements, documentFile
        };
    }

    public void resetValues(){
        this.documentName = null;
        this.price = 0.0;
        this.requirements = null;
        this.documentFile = null;
    }
}
