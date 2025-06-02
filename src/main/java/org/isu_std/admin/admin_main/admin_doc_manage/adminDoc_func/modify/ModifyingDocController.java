package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.io.file_setup.DocxMessage;

import java.io.File;
import java.util.Optional;

public class ModifyingDocController {
    private final ModifyingDocService modifyingDocService;
    private final ModifyDocumentContext modifyDocumentContext;
    private final String[] documentAttributeNames;

    public ModifyingDocController(ModifyingDocService modifyingDocService, int barangayId){
        this.modifyingDocService = modifyingDocService;
        this.documentAttributeNames = modifyingDocService.getDocAttributeNames();
        this.modifyDocumentContext = modifyingDocService.createModDocContext(barangayId);
    }

    protected String[] getDocAttributeNames(){
        return this.documentAttributeNames;
    }

    protected final boolean setValidDocumentId(){
        int documentId = modifyingDocService.getValidDocID();

        if(documentId != 0){
            modifyDocumentContext.setDocumentId(documentId);
            return true;
        }

        return false;
    }

    protected final void setChosenDocAttributeName(int inputChoice){
        modifyDocumentContext.setChosenDocAttributeName(
                documentAttributeNames[inputChoice - 1]
        );
    }

    protected String getChosenDocAttributeName(){
        return modifyDocumentContext.getChosenDocAttributeName();
    }

    protected final boolean isDocNameOrPriceValid(String chosenDocAttributeName, String input){
        try{
            if(chosenDocAttributeName.equals(documentAttributeNames[0])){
                modifyingDocService.checkDocumentName(input);
            }else {
                modifyingDocService.checkDocumentPrice(input);
            }

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setDocName(String documentName){
        if(documentName == null){
            return false;
        }

        try {
            modifyingDocService.checkDocumentName(documentName);
            modifyDocumentContext
                    .getDocumentBuilder()
                    .documentName(documentName);

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setPrice(String strPrice){
        if(strPrice == null){
            return false;
        }

        try {
            int price = modifyingDocService.checkDocumentPrice(strPrice);
            modifyDocumentContext.getDocumentBuilder().price(price);

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setRequirement(){
        Optional<String> requirementInfo = modifyingDocService.getOptionalRequirements();

        if(requirementInfo.isPresent()){
            this.modifyDocumentContext
                    .getDocumentBuilder()
                    .requirements(requirementInfo.get());

            return true;
        }

        return false;
    }

    protected boolean setDocFile(){
        Util.printMessage(DocxMessage.NEED_DOCX_FILE_MESSAGE.getMessage());
        
        try {
            Optional<File> optionalDocFile = modifyingDocService.getOptionalDocumentFile();

            if (optionalDocFile.isPresent()) {
                File file = optionalDocFile.get();
                this.modifyDocumentContext.getDocumentBuilder().documentFile(file);

                return true;
            }

            Util.printMessage("No selected file!");
        }catch (ServiceException e){
            Util.printException(e.getMessage());
        }
        
        return false;
    }

    protected final void modifyProcess(){
        try{
            if(modifyingDocService.isDocumentFileModify(modifyDocumentContext)){
               modifyingDocService.deletePrevDocFile(modifyDocumentContext);
            }

            if(modifyingDocService.modifyPerformed(modifyDocumentContext)){
                Util.printMessage("Modify Success!");
            }
        }catch (OperationFailedException | ServiceException e){
            Util.printException(e.getMessage());
        }

        modifyDocumentContext.getDocumentBuilder().resetValues();
    }

    protected void printDocDetail(){
        Util.printMessage("Detail to Modify -> %s".formatted(
                modifyDocumentContext.getChosenDocAttributeName())
        );
    }
}
