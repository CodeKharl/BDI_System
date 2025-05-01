package org.isu_std.admin.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.RequirementProvider;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.docIdValidation.DocIDValidation;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentConfig;
import org.isu_std.dao.DocManageDao;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.models.modelbuilders.BuilderFactory;

import java.io.File;
import java.util.Optional;

public class ModifyingDocService {
    private final DocManageDao docManageRepository;
    private final DocIDValidation docIDValidation;

    public ModifyingDocService(DocManageDao docManageRepository, DocIDValidation docIDValidation){
        this.docManageRepository = docManageRepository;
        this.docIDValidation = docIDValidation;
    }

    private static final class Holder{
        private static ModifyingDocService instance;
    }

    public static ModifyingDocService getInstance(DocManageDao documentRepository, DocIDValidation docIDValidation){
        if(Holder.instance == null){
            Holder.instance = new ModifyingDocService(documentRepository, docIDValidation);
        }

        return Holder.instance;
    }

    protected ModifyDocumentContext createModDocModel(int barangayID){
        return new ModifyDocumentContext(barangayID, BuilderFactory.createDocumentBuilder());
    }

    protected String[] getDocumentArrInfo(){
        return DocumentManageCodes.DOCUMENT_INFO.getArrCode();
    }

    protected final boolean modifyPerformed(ModifyDocumentContext modifyDocumentContext) {
        if(docManageRepository.modify(modifyDocumentContext)){
            return true;
        }

        throw new OperationFailedException("Failed to modify the document.");
    }

    protected final void checkDocumentName(String documentName){
        if(!Validation.isInputLengthAccepted(
                DocumentConfig.MIN_DOCNAME_LENGTH.getValue(), documentName)
        ){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_SHORT.getFormattedMessage("document name")
            );
        }
    }

    protected final int checkDocumentPrice(String strPrice){
        if(!Validation.isInputMatchesNumbers(strPrice)){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_NUM_ONLY.getFormattedMessage("price")
            );
        }

        return Integer.parseInt(strPrice);
    }

    public final void checkInputChoice(int inputChoice, int docInfoLength){
        // Input choice can be 1, 2, and 3. Early Validate in this method to fasten the process.
        if(inputChoice > 0 && inputChoice <= docInfoLength){
            return;
        }

        throw new IllegalArgumentException(
                InputMessageCollection.INPUT_INVALID.getFormattedMessage("input choice")
        );
    }

    protected int getValidDocID(int barangayId){
        return this.docIDValidation.getInputDocumentId(barangayId);
    }

    protected Optional<String> getOptionalRequirements(){
        return RequirementProvider.getRequirements();
    }

    protected Optional<File> getOptionalDocumentFile(){
        Optional<File> optionalFile = FileChooser.getOptionalDocFile("Document File");
        optionalFile.ifPresent(DocxFileManager::docxFileOrThrow);

        return optionalFile;
    }

    protected void openDocumentFile(File file){
        FileChooser.openFile(file);
    }
}
