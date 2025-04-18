package org.isu_std.admin.admin_main.req_files_view;

import org.isu_std.io.file_setup.FileChooser;

import java.io.File;
import java.io.IOException;

public class ReqFilesViewService {
    protected void reqDocViewPerformed(File requirementFile) throws IOException {
        FileChooser.openFile(requirementFile);
    }
}
