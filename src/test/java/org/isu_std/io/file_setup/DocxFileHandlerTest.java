package org.isu_std.io.file_setup;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DocxFileHandlerTest {
    @Test
    void editDocxPlaceHolders() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("{{name}}", "Kharl Denzell D. Bugarin");
        File file = new File("D:\\Downloads\\Barangay_Clearance_Form.docx");
        DocxFileHandler.editDocxPlaceHolders(file, hashMap);
    }
}