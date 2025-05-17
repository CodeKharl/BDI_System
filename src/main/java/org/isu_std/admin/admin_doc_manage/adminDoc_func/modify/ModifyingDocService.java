package org.isu_std.admin.admin_doc_manage.adminDoc_func.modify;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.*;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation.ValidDocIDProvider;
import org.isu_std.dao.DocManageDao;
import org.isu_std.dao.DocumentDao;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.file_setup.FileChooser;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.models.model_builders.BuilderFactory;

import java.io.File;
import java.util.Optional;

public class ModifyingDocService {
    private final DocumentDao documentDao;
    private final DocManageDao docManageDao;
    private final ValidDocIDProvider validDocIDProvider;

    public ModifyingDocService(
            DocumentDao documentDao, DocManageDao docManageDao, ValidDocIDProvider validDocIDProvider
    ){
        this.documentDao = documentDao;
        this.docManageDao = docManageDao;
        this.validDocIDProvider = validDocIDProvider;
    }

    protected ModifyDocumentContext createModDocModel(int barangayID){
        return new ModifyDocumentContext(barangayID, BuilderFactory.createDocumentBuilder());
    }

    protected String[] getDocumentArrInfo(){
        return DocumentManageCodes.DOCUMENT_INFO.getArrCode();
    }

    protected final boolean modifyPerformed(ModifyDocumentContext modifyDocumentContext) throws OperationFailedException{
        if(docManageDao.modify(modifyDocumentContext)){
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

    protected int getValidDocID(){
        return this.validDocIDProvider.getValidatedId();
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

    protected boolean isDocumentFileModify(ModifyDocumentContext modifyDocumentContext){
        // Builder already set by a new file
        return modifyDocumentContext.getDocumentBuilder().getDocumentFile() != null;
    }

    protected void deletePrevDocFile(ModifyDocumentContext modifyDocumentContext) throws OperationFailedException{
        int barangayId = modifyDocumentContext.getBarangayId();
        int documentId = modifyDocumentContext.getDocumentId();

        DocumentFileDeletion documentFileDeletion = DocFileDeletionFactory
                .createDocFileDeletion(documentDao);

        documentFileDeletion.deletePerform(barangayId, documentId);
    }
}
