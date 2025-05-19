package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.isu_std.doc_output_file_provider.DocOutFileContext;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections_enum.ChoiceCollection;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.io.file_setup.DocxMessage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class DocumentFileManager {
    public static Optional<File> getOptionalDocFile() throws IOException{
        while(true) {
            Util.printSubSectionTitle("Choosing Document File");
            Util.printMessage(DocxMessage.NEED_DOCX_FILE_MESSAGE.getMessage());

            Optional<File> optionalFile = getOptionalFile();
            if (optionalFile.isEmpty()) {
                return Optional.empty();
            }

            if(validationPerform(optionalFile.get())){
                return optionalFile;
            }
        }
    }

    private static boolean validationPerform(File file) throws IOException{
        if(!DocxFileManager.isDocxFile(file)){
            Util.printMessage(DocxMessage.NOT_DOCX_FILE_MESSAGE.getMessage());
            return false;
        }

        return isValidationSuccess(file);
    }

    private static boolean isValidationSuccess(File docxFile) throws IOException{
        if(validationDocumentProcess(docxFile)){
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

    private static boolean validationDocumentProcess(File documentFile) throws IOException{
        Set<String> placeHolders = DocOutFileContext.getPlaceHolderSet();

        DocOutFileContext.printAvailableTextPlaceHolders(placeHolders);
        FileChooser.openFile(documentFile);

        if(!isConfirmedToDocValidate()){
            Util.printMessage("Back to file choosing");
            return false;
        }

        return isDocumentFileAccepted(documentFile, placeHolders);
    }

    private static boolean isDocumentFileAccepted(File documentFile, Set<String> placeHolders)
            throws IOException{
        if(!DocxFileManager.containsPlaceHoldersInParagraphs(documentFile, placeHolders)){
            Util.printMessage("File has no placeholders!");
            return false;
        }

        return true;
    }

    private static boolean isConfirmedToDocValidate(){
        return SystemInput.isPerformConfirmed(
                "Document Validation Confirmation",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.EXIT_CODE.getValue()
        );
    }
}
