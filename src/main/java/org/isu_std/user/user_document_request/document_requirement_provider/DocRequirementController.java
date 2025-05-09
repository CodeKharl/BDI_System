package org.isu_std.user.user_document_request.document_requirement_provider;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class DocRequirementController {
    private final DocRequirementService docRequirementService;
    private final String[] requirements;
    private final List<File> requirementFiles;

    public DocRequirementController(DocRequirementService docRequirementService, String[] requirements){
        this.docRequirementService = docRequirementService;
        this.requirements = requirements;
        this.requirementFiles = docRequirementService.createFileList();
    }

    protected boolean addDocRequirements(int count){
        Optional<File> file = docRequirementService.getDocFileChooser(requirements[count]);

        if(file.isPresent()){
            file.map(this.requirementFiles::add);
            return true;
        }

        return false;
    }

    public String[] getRequirementsArr(){
        return this.requirements;
    }

    protected List<File> getRequirementFiles(){
        return requirementFiles;
    }
}
