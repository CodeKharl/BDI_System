package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.io.file_setup.MSWordMessage;

import java.io.File;
import java.util.Optional;

public class DocumentFileHandler {
    public static Optional<File> getOptionalDocFile(){
        Util.printSubSectionTitle("Choosing File Document");
        Util.printMessage(MSWordMessage.NEED_DOCX_FILE_MESSAGE.getMessage());
        Util.printMessage(MSWordMessage.NEED_CONTAIN_TEXT_PLACEHOLDERS.getMessage());

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
}
