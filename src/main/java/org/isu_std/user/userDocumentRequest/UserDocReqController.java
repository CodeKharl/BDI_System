package org.isu_std.user.userDocumentRequest;

import org.isu_std.io.Util;
import org.isu_std.io.exception.NotFoundException;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.DocumentRequest;
import org.isu_std.user.userDocumentRequest.docReqManager.DocInfoManager;
import org.isu_std.user.userDocumentRequest.docReqManager.DocRequestManager;
import org.isu_std.user.userDocumentRequest.docReqManager.UserInfoManager;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDocReqController {
    private final UserDocReqService userDocReqService;
    private final UserInfoManager userInfoManager;

    private final DocRequestManager docRequestManager;
    private DocInfoManager docInfoManager;

    protected UserDocReqController(UserDocReqService userDocReqService, UserInfoManager userInfoManager){
        this.userDocReqService = userDocReqService;
        this.userInfoManager = userInfoManager;
        this.docRequestManager = userDocReqService.createDocRequestMod();
    }

    protected boolean setBrgyDocs(){
        try {
            docInfoManager = userDocReqService
                    .createUserReqModel(userInfoManager.user().barangayId());

            return true;
        } catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected void printAvailableDocs(){
        Util.printMessage("Available Documents : Document Name - Price - Requirements");

        AtomicInteger count = new AtomicInteger();
        docInfoManager.getBarangayDocumentsMap()
                .forEach((_,document) -> {
                    Util.printChoices(
                            "%d. %s".formatted(count.get() + 1, document.getDetails())
                    );

            count.getAndIncrement();
        });
    }

    protected void setChoiceDocument(int choice){
        Integer[] documentKeys = docInfoManager.getDocumentKeys();
        int documentId = documentKeys[choice - 1];

        docRequestManager.setDocRequest(
                documentId,
                docInfoManager.getBarangayDocumentsMap().get(documentId)
        );
    }

    protected String getDocumentName(){
        return docRequestManager.getDocument().documentName();
    }

    protected boolean isDocumentChoiceAccepted(int choice){
        try{
            int brgyDocMapLength = docInfoManager.getBarangayDocumentsMap().size();
            userDocReqService.checkDocumentChoice(brgyDocMapLength, choice);

            return true;
        }catch (IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    protected boolean setDocUserRequirements(){
        String[] requirementsInfo = docRequestManager.getDocument().getRequirementsArr();
        List<File> requirementFiles = userDocReqService
                .createDocRequirement(requirementsInfo)
                .getUserDocReqList();

        if(!requirementFiles.isEmpty()){
            docRequestManager.setRequirements(requirementFiles);
            return true;
        }

        return false;
    }

    protected void printDocRequirementsPath(){
        List<File> docFileList = docRequestManager.getDocRequirementFiles();

        Util.printSubSectionTitle("Selected Requirement Files");
        docFileList.forEach(file -> Util.printInformation(file.getName()));
    }

    protected void printAllInformations(){
        Util.printSectionTitle("Information Confirmation");
        docRequestManager.getDocument().printDetails();
        userInfoManager.userPersonal().printPersonalStats();
        printDocRequirementsPath();
    }

    protected boolean isAddDocRequestSuccess(){
        try{
            DocumentRequest documentRequest = userDocReqService.createDocReq(
                    userDocReqService.createReferenceId(docRequestManager.getDocumentId()),
                    userInfoManager.user().userId(),
                    userInfoManager.user().barangayId(),
                    docRequestManager.getDocumentId(),
                    docRequestManager.getDocRequirementFiles()
            );

            userDocReqService.checkDocRequestIfUnique(documentRequest);
            userDocReqService.addDocumentRequest(documentRequest);
            return true;
        }catch (OperationFailedException | IllegalArgumentException e){
            Util.printException(e.getMessage());
        }

        return false;
    }
}
