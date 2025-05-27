package org.isu_std.admin.admin_main.req_files_view;

import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.ServiceException;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReqFilesViewController {
    private final ReqFilesViewService reqFilesViewService;
    private final List<File> requirementFileList;

    protected ReqFilesViewController(ReqFilesViewService reqFilesViewService, List<File> requirementFileList){
        this.reqFilesViewService = reqFilesViewService;
        this.requirementFileList = requirementFileList;
    }

    protected void printChoiceReqFiles(){
        Util.printSubSectionTitle("User Requirement Files");

        for(int i = 0; i < requirementFileList.size(); i++){
            File file = requirementFileList.get(i);

            Util.printChoice("%d. %s".formatted(i + 1, file.getName()));
        }
    }

    protected int getFileListLength(){
        return this.requirementFileList.size();
    }

    protected void reqFileViewPerform(int choice){
        try{
            File requirementFile = requirementFileList.get(choice - 1);

            Util.printMessage("Opening the %s...".formatted(requirementFile.getName()));
            reqFilesViewService.reqFileViewPerform(requirementFile);
        }catch (ServiceException e){
            Util.printException(e.getMessage());
        }
    }
}
