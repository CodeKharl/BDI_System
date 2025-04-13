package org.isu_std.user.userDocumentRequest;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.io.collections.InputMessageCollection;
import org.isu_std.models.DocumentRequest;
import org.isu_std.user.userDocumentRequest.docReqManager.DocInfoManager;
import org.isu_std.user.userDocumentRequest.docReqManager.DocRequestManager;
import org.isu_std.user.userDocumentRequest.docRequirementHandler.DocRequirement;
import org.isu_std.user.userDocumentRequest.docRequirementHandler.DocRequirementController;
import org.isu_std.user.userDocumentRequest.docRequirementHandler.DocRequirementService;
import org.isu_std.user.userDocumentRequest.reference_generator.ReferenceConfig;
import org.isu_std.user.userDocumentRequest.reference_generator.ReferenceGenerator;

import java.io.File;
import java.util.List;
import java.util.Map;

public class UserDocReqService{
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;

    public UserDocReqService(DocumentDao documentDao, DocumentRequestDao documentRequestDao){
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
    }

    protected DocInfoManager createUserReqModel(int barangayId) throws NotFoundException{
        return new DocInfoManager(getBrgyDocsMap(barangayId));
    }

    private Map<Integer, Document> getBrgyDocsMap(int barangayId){
        Map<Integer, Document> docsHolder = documentDao.getDocumentMap(barangayId);

        if(docsHolder.isEmpty()){
            throw new NotFoundException(
                    "There's no existing documents! Please cooperate to the admin of your barangay."
            );
        }

        return docsHolder;
    }

    protected DocRequestManager createDocRequestMod(){
        return new DocRequestManager();
    }

    protected void checkDocumentChoice(int brgyDocMapLength, int choice){
        if(choice < 1 || choice > brgyDocMapLength){
            throw new IllegalArgumentException(
                    InputMessageCollection.INPUT_INVALID.getFormattedMessage("choice")
            );
        }
    }

    protected DocRequirement createDocRequirement(String[] requirementsInfo){
        return new DocRequirement(
                new DocRequirementController(
                        new DocRequirementService(),
                        requirementsInfo
                )
        );
    }

    protected DocumentRequest createDocReq(
            String referenceId, int userId, int barangayId, int documentId, List<File> reqFileLIst
    ){
        return new DocumentRequest(referenceId, userId, barangayId, documentId, reqFileLIst);
    }

    protected String createReferenceId(int documentId){
        return ReferenceGenerator.createReferenceId(
                String.valueOf(documentId),
                (String) ReferenceConfig.TIME_FORMAT.getValue(),
                (String) ReferenceConfig.ALPHABET.getValue() + ReferenceConfig.NUMBERS.getValue(),
                (int) ReferenceConfig.DEFAULT_RANDOM_LENGTH.getValue()
        );
    }

    protected void checkDocRequestIfUnique(DocumentRequest documentRequest){
        int docRequestCount = documentRequestDao.getUserDocRequestCount(documentRequest);
        if(docRequestCount > 0){
            throw new IllegalArgumentException("Duplicate document request! Request will cancelled.");
        }
    }

    protected void addDocumentRequest(DocumentRequest documentRequest){
        if(!documentRequestDao.addDocRequest(documentRequest)){
            throw new OperationFailedException("Request Failed! Please try again.");
        }
    }
}
