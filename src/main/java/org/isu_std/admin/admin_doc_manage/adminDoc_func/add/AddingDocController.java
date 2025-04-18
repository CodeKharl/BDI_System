package org.isu_std.admin.admin_doc_manage.adminDoc_func.add;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.io.file_setup.MSWordMessage;
import org.isu_std.models.modelbuilders.DocumentBuilder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CancellationException;

public class AddingDocController {
    private final AddingDocService addingDocService;
    private final DocumentBuilder documentBuilder;
    private final int barangayId;

    public AddingDocController(AddingDocService addingDocService, int barangayId){
        this.addingDocService = addingDocService;
        this.documentBuilder = addingDocService.getDocumentBuilder();
        this.barangayId = barangayId;
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
            addingDocService.addPerformed(this.barangayId, documentBuilder.build());
            Util.printMessage(
                    "The %s document has been added!".formatted(documentBuilder.getDocumentName())
            );
        } catch (OperationFailedException e){
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
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printDocument(){
        documentBuilder.printStatus();
    }
}

