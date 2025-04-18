package org.isu_std.admin.admin_main.req_files_view;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;

public class RequirementFilesView {
    private final ReqFilesViewController reqFilesViewController;

    public RequirementFilesView(ReqFilesViewController reqFilesViewController){
        this.reqFilesViewController = reqFilesViewController;
    }

    public void viewProcess(){
        int backLengthValue = reqFilesViewController.getFileListLength() + 1;
        reqFilesViewController.printChoiceReqDocFiles();
        Util.printChoices(
                "%d. Back to Document Request View".formatted(backLengthValue)
        );

        while(true){
            int reqFileChoice = SystemInput.getIntChoice(
                    "Enter the file number you want to view : ", backLengthValue
            );

            if(reqFileChoice == backLengthValue){
                return;
            }

            reqFilesViewController.reqFileViewPerformed(reqFileChoice);
        }

    }
}
