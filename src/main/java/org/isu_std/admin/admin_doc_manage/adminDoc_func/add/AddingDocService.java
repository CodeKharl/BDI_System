package org.isu_std.admin.admin_doc_manage.adminDoc_func.add;

import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_file_deletion.DocumentFileManager;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentConfig;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.DocumentManageCodes;
import org.isu_std.admin.admin_doc_manage.adminDoc_func.others.RequirementProvider;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Document;
import org.isu_std.dao.DocManageDao;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.io.Validation;
import org.isu_std.models.model_builders.BuilderFactory;
import org.isu_std.models.model_builders.DocumentBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AddingDocService {
    private final DocManageDao docManageDao;

    public AddingDocService(DocManageDao docManageDao){
        this.docManageDao = docManageDao;
    }

    protected String[] getInfoArr(){
        return DocumentManageCodes.DOCUMENT_INFO.getArrCode();
    }

    protected DocumentBuilder getDocumentBuilder(){
        return BuilderFactory.createDocumentBuilder();
    }

    protected void checkedDocName(String documentName) {
        if(!Validation.isInputLengthAccepted(
                DocumentConfig.MIN_DOCNAME_LENGTH.getValue(), documentName
        )){
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
        return RequirementProvider.getRequirements();
    }

    protected Optional<File> getOptionalDocumentFile(){
        try {
            return DocumentFileManager.getOptionalDocFile();
        }catch (IOException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "There some problem on getting your document file! Please try again some time"
            );
        }
    }

    protected void addPerform(int barangayId, Document document){
        try {
            if (!docManageDao.addDocument(barangayId, document)) {
                throw new OperationFailedException("Failed to add the document! Please try again.");
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to insert document : " + document);
        }
    }
}
