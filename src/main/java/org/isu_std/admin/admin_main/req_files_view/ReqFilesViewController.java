package org.isu_std.admin.admin_main.req_files_view;

import org.isu_std.io.Util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReqFilesViewController {
    private final ReqFilesViewService reqFilesViewService;
    private final List<File> requirementFileList;

    public ReqFilesViewController(ReqFilesViewService reqFilesViewService, List<File> requirementFileList){
        this.reqFilesViewService = reqFilesViewService;
        this.requirementFileList = requirementFileList;
    }

    protected void printChoiceReqDocFiles(){
        Util.printSubSectionTitle("User Requirement Files");

        AtomicInteger count = new AtomicInteger();
        requirementFileList.forEach(
                (file) -> Util.printChoices(
                        "%d. %s".formatted(count.getAndIncrement() + 1, file.getName())
                )
        );
    }

    protected int getFileListLength(){
        return this.requirementFileList.size();
    }

    protected void reqFileViewPerformed(int index){
        try{
            File requirmentFile = requirementFileList.get(index - 1);
            Util.printMessage("Opening the %s...".formatted(requirmentFile.getName()));

            reqFilesViewService.reqDocViewPerformed(requirmentFile);
        }catch (IOException e){
            Util.printException(e.getMessage());
        }

    }
}
