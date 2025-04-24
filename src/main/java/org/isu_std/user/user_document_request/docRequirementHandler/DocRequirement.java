package org.isu_std.user.user_document_request.docRequirementHandler;

import org.isu_std.io.collections.ChoiceCollection;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocRequirement {
    private final DocRequirementController docRequirementController;

    public DocRequirement(DocRequirementController docRequirementController){
        this.docRequirementController = docRequirementController;
    }

    public List<File> getUserDocReqList(){
        String[] requirements = docRequirementController.getRequirementsArr();

        Util.printSectionTitle("Requirement Section");
        Util.printInformation("Needed Requirements -> " + Arrays.toString(requirements));
        Util.printMessage("Section Guide : The System is automatically open the file explore to locate a file.");

        return setUserDocReqProcess(requirements.length);
    }

    private List<File> setUserDocReqProcess(int requirementArrLength){
        int count = 0;
        while(count < requirementArrLength){
            if(docRequirementController.addDocRequirements(count)){
                count++;
                continue;
            }

            if(!isContinueToLocate()){
                return new ArrayList<>();
            }
        }

        return docRequirementController.getRequirementFiles();
    }

    private boolean isContinueToLocate(){
        return SystemInput.isPerformConfirmed(
                "Continue to locate Requirement Files",
                ChoiceCollection.OPEN.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }
}
