package org.isu_std.admin.admin_main.req_files_view;

import java.io.File;
import java.util.List;

public class ReqFilesViewFactory {
    private ReqFilesViewFactory(){}

    public static RequirementFilesView createReqFilesView(List<File> requirementFiles){
        ReqFilesViewService reqFilesViewService = new ReqFilesViewService();
        ReqFilesViewController reqFilesViewController = new ReqFilesViewController(
                reqFilesViewService, requirementFiles
        );

        return new RequirementFilesView(reqFilesViewController);
    }
}
