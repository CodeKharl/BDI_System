package org.isu_std.user.user_document_request.document_Requirement_provider;

import org.isu_std.io.file_setup.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocRequirementService {
    protected List<File> createFileList(){
        return new ArrayList<>();
    }

    protected Optional<File> getDocFileChooser(String requirementType){
        return FileChooser.getOptionalDocFile(requirementType);
    }
}
