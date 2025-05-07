package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.io.file_setup.DocxMessage;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.Optional;
import java.util.Set;

public class DocumentFileManager {
    public static Optional<File> getOptionalDocFile(){
        while(true) {
            Util.printSubSectionTitle("Choosing File Document");
            Util.printMessage(DocxMessage.NEED_DOCX_FILE_MESSAGE.getMessage());

            Optional<File> optionalFile = getOptionalFile();
            if (optionalFile.isEmpty()) {
                return Optional.empty();
            }

            File file = optionalFile.get();
            if(!DocxFileManager.isDocxFile(file)){
                Util.printMessage(DocxMessage.NOT_DOCX_FILE_MESSAGE.getMessage());
                continue;
            }

            if(isModificationSuccess(file)){
                return Optional.of(file);
            }
        }
    }

    private static boolean isModificationSuccess(File docxFile){
        if(modificationDocumentProcess(docxFile)){
            Util.printMessage("Document file accepted!");
            return true;
        }

        return false;
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
        Set<String> placeHolders = getNeededPlaceHolders();

        printAvailableTextPlaceHolders(placeHolders);
        FileChooser.openFile(documentFile);

        if(!isConfirmedToDocValidate()){
            return false;
        }

        return isDocumentFileAccepted(documentFile, placeHolders);
    }

    private static Set<String> getNeededPlaceHolders(){
        // Main information for the output document file. E.g. name, age, sex, etc.
        RecordComponent[] recordComponents = UserPersonal.class.getRecordComponents();
        Set<String> placeHolders = DocxFileManager.convertRecComToPlaceHoldersSet(recordComponents);

        // Date
        String dateHolder = DocxFileManager.convertToPlaceholder("date");
        placeHolders.add(dateHolder);

        return placeHolders;
    }

    private static boolean isDocumentFileAccepted(File documentFile, Set<String> placeHolders){
        try{
            if(!DocxFileManager.containsPlaceHoldersInParagraphs(documentFile, placeHolders)){
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
        userPerPlaceHolders.forEach(Util::printChoice);
    }


    private static boolean isConfirmedToDocValidate(){
        return SystemInput.isPerformConfirmed(
                "Document Validation Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.EXIT_CODE.getValue()
        );
    }
}
