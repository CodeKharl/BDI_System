package org.isu_std.admin.admin_main.req_files_view;

import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.io.file_setup.FileChooser;

import java.io.File;
import java.io.IOException;

public class ReqFilesViewService {
    protected void reqFileViewPerform(File requirementFile){
        try {
            FileChooser.openFile(requirementFile);
        }catch (IOException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Cannot open the requirement file : " + requirementFile);
        }
    }
}
