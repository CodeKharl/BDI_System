package org.isu_std.admin.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.io.file_setup.DocxMessage;

import java.io.File;
import java.util.Optional;

public class ModifyingDocController {
    private final String[] document_info;
    private final ModifyingDocService modifyingDocService;
    private final ModifyDocManager modifyDocManager;

    public ModifyingDocController(ModifyingDocService modifyingDocService, int barangayId){
        this.modifyingDocService = modifyingDocService;
        this.document_info = modifyingDocService.getDocumentArrInfo();
        this.modifyDocManager = modifyingDocService.createModDocModel(barangayId);
    }

    protected final boolean setValidDocumentId(){
        int documentId = modifyingDocService.getValidDocID(modifyDocManager.getBarangayId());

        if(documentId != 0){
            modifyDocManager.setDocumentId(documentId);
            return true;
        }

        return false;
    }

    protected final void setDocumentDetail(int inputChoice){
        modifyDocManager.setDocumentDetail(
                document_info[inputChoice - 1]
        );
    }

    protected String[] getDocumentInfos(){
        return this.document_info;
    }

    protected String getDocumentDetail(){
        return modifyDocManager.getDocumentDetail();
    }

    protected final boolean isDocDetailAccepted(int detailChoice){
        try{
            modifyingDocService.checkInputChoice(detailChoice, document_info.length);
            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected final boolean isDocNameOrPriceValid(String documentDetail, String input){
        try{
            if(documentDetail.equals(document_info[0])){
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
            modifyDocManager
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
            modifyDocManager.getDocumentBuilder().price(price);

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setRequirement(){
        Optional<String> requirementInfo = modifyingDocService.getOptionalRequirements();

        if(requirementInfo.isPresent()){
            this.modifyDocManager
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
                modifyingDocService.openDocumentFile(file);
                this.modifyDocManager.getDocumentBuilder().documentFile(file);

                return true;
            }

            Util.printMessage("No selected file!");
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }
        
        return false;
    }

    protected void printDocumentInfos(){
        Util.printChoices(document_info);
    }

    public final void modifyProcess(){
        try{
            if(modifyingDocService.modifyPerformed(modifyDocManager)){
                Util.printMessage("Modify Success!");
            }
        }catch (OperationFailedException e){
            Util.printException(e.getMessage());
        }
    }

    protected void printDocDetail(){
        Util.printMessage("Detail to Modify -> %s".formatted(modifyDocManager.getDocumentDetail()));
    }
}
