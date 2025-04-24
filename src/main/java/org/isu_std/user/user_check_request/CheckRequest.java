package org.isu_std.user.user_check_request;

import org.isu_std.io.Util;
import org.isu_std.user.UserProcess;

public class CheckRequest implements UserProcess {
    private final CheckRequestController checkRequestController;

    public CheckRequest(CheckRequestController checkRequestController){
        this.checkRequestController = checkRequestController;
    }

    @Override
    public void processPerformed(String processTitle){
        Util.printSectionTitle(processTitle);

        if(!checkRequestController.isExistingDocMapSet()){
            return;
        }

        checkRequestController.printDocumentDetails();
    }
}
