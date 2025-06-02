package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.add;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.model_builders.DocumentBuilder;

import java.io.File;
import java.util.Optional;

public class AddingDocController {
    private final AddingDocService addingDocService;
    private final DocumentBuilder documentBuilder;
    private final int barangayId;

    public AddingDocController(AddingDocService addingDocService, int barangayId){
        this.addingDocService = addingDocService;
        this.documentBuilder = addingDocService.getDocumentBuilder();
        this.barangayId = barangayId;
    }

    protected String[] getDocumentAttributeNames(){
        return this.addingDocService.getDocumentAttributeNames();
    }

    protected final boolean setDocumentInformation(int count, String input){
        try {
            switch (count) {
                case 0 -> {
                    addingDocService.checkedDocName(input);
                    this.documentBuilder.documentName(input);
                }
                case 1 -> documentBuilder.price(addingDocService.getCheckedDocPrice(input));
            }
            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected final void processDocument(){
        try {
            addingDocService.addPerform(barangayId, documentBuilder.build());

            Util.printMessage(
                    "The %s document has been added!".formatted(documentBuilder.getDocumentName())
            );
        } catch (OperationFailedException | ServiceException e){
            Util.printException(e.getMessage());
        }
    }

    protected boolean setDocumentRequirements(){
        Optional<String> requirement = addingDocService.getOptionalRequirement();

        if(requirement.isPresent()){
            this.documentBuilder.requirements(requirement.get());
            return true;
        }

        return false;
    }

    protected boolean setDocumentFile(){
        try {
            Optional<File> optionalDocFile = addingDocService.getOptionalDocumentFile();

            if (optionalDocFile.isPresent()) {
                File docFile = optionalDocFile.get();
                this.documentBuilder.documentFile(docFile);

                return true;
            }

            Util.printMessage("No selected document file!");
        }catch (ServiceException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printDocument(){
        documentBuilder
                .build()
                .printDetailsWithDocumentFile();
    }
}

