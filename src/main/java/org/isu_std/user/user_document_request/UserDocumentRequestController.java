package org.isu_std.user.user_document_request;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;
import org.isu_std.user.user_document_request.document_request_contexts.DocInfoContext;
import org.isu_std.user.user_document_request.document_request_contexts.DocRequestContext;
import org.isu_std.user.user_document_request.document_request_contexts.UserInfoContext;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDocumentRequestController {
    private final UserDocumentRequestService userDocumentRequestService;
    private final UserInfoContext userInfoContext;

    private final DocRequestContext docRequestContext;
    private DocInfoContext docInfoContext;

    protected UserDocumentRequestController(UserDocumentRequestService userDocumentRequestService, UserInfoContext userInfoContext){
        this.userDocumentRequestService = userDocumentRequestService;
        this.userInfoContext = userInfoContext;
        this.docRequestContext = userDocumentRequestService.createDocRequestMod();
    }

    protected boolean setBrgyDocs(){
        try {
            docInfoContext = userDocumentRequestService
                    .createUserReqModel(userInfoContext.user().barangayId());

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
        userInfoContext.userPersonal().printPersonalStats();
        printDocRequirementsPath();
    }

    protected boolean isAddDocRequestSuccess(){
        try{
            DocumentRequest documentRequest = userDocumentRequestService.createDocReq(
                    userDocumentRequestService.createReferenceId(docRequestContext.getDocumentId()),
                    userInfoContext.user().userId(),
                    userInfoContext.user().barangayId(),
                    docRequestContext.getDocumentId(),
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
