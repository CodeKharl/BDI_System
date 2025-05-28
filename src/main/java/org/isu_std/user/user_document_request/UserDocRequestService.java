package org.isu_std.user.user_document_request;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Document;
import org.isu_std.io.collections_enum.InputMessageCollection;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_document_request.document_request_contexts.DocInfoContext;
import org.isu_std.user.user_document_request.document_request_contexts.DocRequestContext;
import org.isu_std.user.user_document_request.document_requirement_provider.DocRequirementProvider;
import org.isu_std.user.user_document_request.document_requirement_provider.DocRequirementController;
import org.isu_std.user.user_document_request.document_requirement_provider.DocRequirementService;
import org.isu_std.user.user_document_request.document_request_contexts.UserInfoContext;
import org.isu_std.user.user_document_request.reference_generator.ReferenceConfig;
import org.isu_std.user.user_document_request.reference_generator.ReferenceGenerator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDocRequestService {
    private final DocumentDao documentDao;
    private final DocumentRequestDao documentRequestDao;
    private final UserPersonalDao userPersonalDao;

    public UserDocRequestService(
            DocumentDao documentDao, DocumentRequestDao documentRequestDao, UserPersonalDao userPersonalDao
    ){
        this.documentDao = documentDao;
        this.documentRequestDao = documentRequestDao;
        this.userPersonalDao = userPersonalDao;
    }

    protected DocInfoContext createUserReqModel(int barangayId) throws NotFoundException{
        return new DocInfoContext(getBrgyDocsMap(barangayId));
    }

    protected UserInfoContext createUserInfoContext(User user){
        return new UserInfoContext(user);
    }

    protected UserPersonal getUserPersonal(int userId){
        try {
            Optional<UserPersonal> optionalUserPersonal = userPersonalDao.getOptionalUserPersonal(userId);

            return optionalUserPersonal.orElseThrow(
                    () -> new NotFoundException(
                            "Theres no existing personal information of your account!"
                    )
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch user personal info with user_id : " + userId);
        }
    }

    private Map<Integer, Document> getBrgyDocsMap(int barangayId){
        try {
            Map<Integer, Document> docsHolder = documentDao.getDocumentMap(barangayId);

            if (docsHolder.isEmpty()) {
                throw new NotFoundException(
                        "There's no existing documents! Please cooperate to the admin of your barangay."
                );
            }

            return docsHolder;
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException(
                    "Failed to fetch barangay document map with barangay_id : " + barangayId
            );
        }
    }

    protected DocRequestContext createDocRequestMod(){
        return new DocRequestContext();
    }

    protected DocRequirementProvider createDocRequirement(String[] requirementsInfo){
        return new DocRequirementProvider(
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
        int docRequestCount = getDocRequestCount(documentRequest);

        if(docRequestCount > 0){
            throw new IllegalArgumentException(
                    "Duplicate document request! Request will cancelled."
            );
        }
    }

    private int getDocRequestCount(DocumentRequest documentRequest){
        try {
            Optional<Integer> docRequestCount = documentRequestDao
                    .getUserDocRequestCount(documentRequest);

            return docRequestCount.orElseThrow(
                    () -> new NotFoundException("Document requests not found")
            );
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch request count by : " + documentRequest);
        }
    }

    protected void addDocumentRequest(DocumentRequest documentRequest){
        try {
            if (!documentRequestDao.addDocRequest(documentRequest)) {
                throw new OperationFailedException(
                        "Request Failed! Please try again."
                );
            }
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to insert document request : " + documentRequest);
        }
    }
}
