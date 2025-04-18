package org.isu_std.admin.admin_doc_manage.adminDoc_func.add;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentFileHandler;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentConfig;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.RequirementHandler;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.io.file_setup.DocxFileHandler;
import org.isu_std.models.Document;
import org.isu_std.dao.DocManageDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.models.modelbuilders.BuilderFactory;
import org.isu_std.models.modelbuilders.DocumentBuilder;

import java.io.File;
import java.util.Optional;

public class AddingDocService {
    private final DocManageDao docManageRepository;

    public AddingDocService(DocManageDao documentRepository){
        this.docManageRepository = documentRepository;
    }

    protected void addPerformed(int barangayId, Document document) {
        if(!docManageRepository.add(barangayId, document)){
            throw new OperationFailedException("Failed to add the document! Please try again.");
        }
    }

    protected DocumentBuilder getDocumentBuilder(){
        return BuilderFactory.createDocumentBuilder();
    }

    public void checkedDocName(String documentName) {
        if(!Validation.isInputLengthAccepted(DocumentConfig.MIN_DOCNAME_LENGTH.getValue(), documentName)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("document name")
            );
        }
    }

    public int getCheckedDocPrice(String strPrice){
        if(Validation.isInputMatchesNumbers(strPrice)){
            return Integer.parseInt(strPrice);
        }

        throw new IllegalArgumentException(
                InputMessageCollection.INPUT_NUM_ONLY.getFormattedMessage("price")
        );
    }

    protected Optional<String> getOptionalRequirement(){
        return RequirementHandler.getRequirements();
    }

    protected Optional<File> getOptionalDocumentFile() throws IllegalArgumentException{
        Optional<File> optionalFile = DocumentFileHandler.getOptionalDocFile();
        optionalFile.ifPresent(DocxFileHandler::docxFileOrThrow);

        return optionalFile;
    }
}
