package org.isu_std.user.user_document_request;

import org.isu_std.client_context.UserContext;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;
import org.isu_std.user.user_document_request.document_request_contexts.DocInfoContext;
import org.isu_std.user.user_document_request.document_request_contexts.DocRequestContext;
import org.isu_std.user.user_document_request.document_request_contexts.UserInfoContext;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDocumentRequestController {
    private final UserDocumentRequestService userDocumentRequestService;

    private final UserInfoContext userInfoContext;
    private final DocRequestContext docRequestContext;
    private DocInfoContext docInfoContext;

    protected UserDocumentRequestController(
            UserDocumentRequestService userDocumentRequestService, UserContext userContext
    ){
        this.userDocumentRequestService = userDocumentRequestService;
        this.userInfoContext = userDocumentRequestService.createUserInfoContext(userContext.getUser());
        this.docRequestContext = userDocumentRequestService.createDocRequestMod();
    }

    protected boolean setUserPersonal(){
        try{
            int userId = userInfoContext.getUser().userId();
            UserPersonal userPersonal = userDocumentRequestService.getUserPersonal(userId);

            userInfoContext.setUserPersonal(userPersonal);

            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setBrgyDocs(){
        try {
            docInfoContext = userDocumentRequestService
                    .createUserReqModel(userInfoContext.getUser().barangayId());

            return true;
        } catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printAvailableDocs(){
        Util.printMessage("Available Documents : Document Name - Price - Requirements");

        AtomicInteger count = new AtomicInteger();
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

    protected boolean isDocumentChoiceAccepted(int choice){
        try{
            int brgyDocMapLength = docInfoContext.getBarangayDocumentsMap().size();
            userDocumentRequestService.checkDocumentChoice(brgyDocMapLength, choice);

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setDocUserRequirements(){
        String[] requirementsInfo = docRequestContext.getDocument().getRequirementsArr();
        List<File> requirementFiles = userDocumentRequestService
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
            DocumentRequest documentRequest = userDocumentRequestService.createDocReq(
                    userDocumentRequestService.createReferenceId(documentId),
                    user.userId(),
                    user.barangayId(),
                    documentId,
                    docRequestContext.getDocRequirementFiles()
            );

            userDocumentRequestService.checkDocRequestIfUnique(documentRequest);
            userDocumentRequestService.addDocumentRequest(documentRequest);
            return true;
        }catch (OperationFailedException | IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
