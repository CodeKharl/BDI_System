package org.isu_std.admin.requested_documents.req_files_view;

import org.isu_std.admin.requested_documents.RequestedDocumentService;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.file_setup.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReqFilesViewService {
    protected void reqDocViewPerformed(File requirementFile) throws IOException {
        FileChooser.openFile(requirementFile);
    }
}
