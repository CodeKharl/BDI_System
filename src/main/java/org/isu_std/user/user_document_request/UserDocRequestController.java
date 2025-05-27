package org.isu_std.user.user_document_request;

import org.isu_std.client_context.UserContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_document_request.document_request_contexts.DocInfoContext;
import org.isu_std.user.user_document_request.document_request_contexts.DocRequestContext;
import org.isu_std.user.user_document_request.document_request_contexts.UserInfoContext;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDocRequestController {
    private final UserDocRequestService userDocRequestService;

    private final UserInfoContext userInfoContext;
    private final DocRequestContext docRequestContext;
    private DocInfoContext docInfoContext;

    protected UserDocRequestController(
            UserDocRequestService userDocRequestService, UserContext userContext
    ){
        this.userDocRequestService = userDocRequestService;
        this.userInfoContext = userDocRequestService.createUserInfoContext(userContext.getUser());
        this.docRequestContext = userDocRequestService.createDocRequestMod();
    }

    protected boolean setUserPersonal(){
        try{
            int userId = userInfoContext.getUser().userId();
            UserPersonal userPersonal = userDocRequestService.getUserPersonal(userId);

            userInfoContext.setUserPersonal(userPersonal);

            return true;
        }catch (ServiceException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setBrgyDocs(){
        try {
            docInfoContext = userDocRequestService
                    .createUserReqModel(userInfoContext.getUser().barangayId());

            return true;
        } catch (ServiceException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printAvailableDocs(){
        Util.printMessage("Available Documents : Document Name - Price - Requirements");

        var count = new AtomicInteger();
        docInfoContext.getBarangayDocumentsMap()
                .forEach((_,document) -> {
                    Util.printChoice(
                            "%d. %s".formatted(count.get() + 1, document.getDetails())
                    );

            count.getAndIncrement();
        });
    }

    protected void setChoiceDocument(int choice){
        Integer[] documentKeys = docInfoContext.getDocumentKeys();
        int documentId = documentKeys[choice - 1];

        docRequestContext.setDocRequest(
                documentId,
                docInfoContext.getBarangayDocumentsMap().get(documentId)
        );
    }

    protected String getDocumentName(){
        return docRequestContext.getDocument().documentName();
    }

    protected boolean setDocUserRequirements(){
        String[] requirementsInfo = docRequestContext.getDocument().getRequirementsArr();
        List<File> requirementFiles = userDocRequestService
                .createDocRequirement(requirementsInfo)
                .getUserDocReqList();

        if(!requirementFiles.isEmpty()){
            docRequestContext.setRequirements(requirementFiles);
            return true;
        }

        return false;
    }

    protected void printDocRequirementsPath(){
        List<File> docFileList = docRequestContext.getDocRequirementFiles();

        Util.printSubSectionTitle("Selected Requirement Files");
        docFileList.forEach(file -> Util.printInformation(file.getName()));
    }

    protected void printAllInformations(){
        Util.printSectionTitle("Information Confirmation");
        docRequestContext.getDocument().printDetails();
        userInfoContext.getUserPersonal().printPersonalStats();
        printDocRequirementsPath();
    }

    protected boolean isAddDocRequestSuccess(){
        User user = userInfoContext.getUser();
        int documentId = docRequestContext.getDocumentId();

        try{
            DocumentRequest documentRequest = userDocRequestService.createDocReq(
                    userDocRequestService.createReferenceId(documentId),
                    user.userId(),
                    user.barangayId(),
                    documentId,
                    docRequestContext.getDocRequirementFiles()
            );

            if(isRequestValid(documentRequest)){
                userDocRequestService.addDocumentRequest(documentRequest);

                return true;
            }
        }catch (ServiceException | OperationFailedException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    private boolean isRequestValid(DocumentRequest documentRequest){
        try {
            userDocRequestService.checkDocRequestIfUnique(documentRequest);

            return true;
        }catch (IllegalArgumentException | NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected int getBrgyDocsMapSize(){
        return docInfoContext.getBarangayDocumentsMap().size();
    }
}
