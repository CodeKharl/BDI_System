package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.io.file_setup.DocxFileHandler;
import org.isu_std.io.file_setup.DocxMessage;
import org.isu_std.models.UserPersonal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

public class DocumentFileHandler {
    public static Optional<File> getOptionalDocFile(){
        while(true) {
            Util.printSubSectionTitle("Choosing File Document");
            Util.printMessage(DocxMessage.NEED_DOCX_FILE_MESSAGE.getMessage());

            Optional<File> optionalFile = getOptionalFile();
            if (optionalFile.isEmpty()) {
                return Optional.empty();
            }

            if(modificationDocumentProcess(optionalFile.get())){
                Util.printMessage("Document file accepted!");
                return optionalFile;
            }

            Util.printMessage("File not accepted! Please try again.");
        }
    }

    private static Optional<File> getOptionalFile(){
        do {
            Optional<File> docFile = FileChooser.getOptionalDocFile("Document File");
            if (docFile.isPresent()) {
                return docFile;
            }

        } while (isRepeatChoosingConfirmed());

        return Optional.empty();
    }

    private static boolean isRepeatChoosingConfirmed(){
        return SystemInput.isPerformConfirmed(
                "Choosing file repeat confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }

    private static boolean modificationDocumentProcess(File documentFile){
        Field[] userPersonalFields = UserPersonal.class.getDeclaredFields();
        Set<String> placeHolders = DocxFileHandler.convertFieldsToPlaceHoldersSet(userPersonalFields);

        printAvailableTextPlaceHolders(placeHolders);
        FileChooser.openFile(documentFile);

        if(!isConfirmedToDocValidate()){
            return false;
        }

        return isDocumentFileAccepted(documentFile, placeHolders);
    }

    private static boolean isDocumentFileAccepted(File documentFile, Set<String> placeHolders){
        try{
            if(!DocxFileHandler.containsPlaceHoldersInParagraphs(documentFile, placeHolders)){
                Util.printMessage("File has no placeholders!");
                return false;
            }
        }catch(IOException e){
            Util.printException(e.getMessage());
        }
        return true;
    }

    private static void printAvailableTextPlaceHolders(Set<String> userPerPlaceHolders){
        Util.printSubSectionTitle("Available Document Text Place-Holders");
        Util.printMessage("This place holders can only be use on paragraphs!");

        for(String holder: userPerPlaceHolders){
            Util.printChoices(holder);
        }
    }


    private static boolean isConfirmedToDocValidate(){
        return SystemInput.isPerformConfirmed(
                "Document Validation Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.EXIT_CODE.getValue()
        );
    }
}
