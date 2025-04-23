package org.isu_std.admin.admin_main.approved_documents.approved_documents_export;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ApprovedDocExportTest {
    @Test
    void docFileMoveDirCorrect() throws IOException {
        ApprovedDocExportService approvedDocExportService = new ApprovedDocExportService();

        File actualFile = new File("C:\\Users\\asus\\Documents\\INDIVIDUALIZE FITNESS PROGRAM.docx");
        Path targetPath = Paths.get("C:\\Users\\asus\\Documents\\New folder", actualFile.getName());

        approvedDocExportService.docFileMoveDirPerformed(actualFile, targetPath);
    }
}