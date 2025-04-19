package org.isu_std.io.file_setup;

import org.isu_std.models.UserPersonal;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DocxFileHandlerTest {
    @Test
    void editDocxPlaceHolders() throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("{{name}}", "Kharl Denzell D. Bugarin");
        hashMap.put("{{sex}}", "Male");
        hashMap.put("{{civilStatus}}", "Single");
        File file = new File("D:\\Downloads\\Barangay_Clearance_Form_V2.docx");
        DocxFileHandler.editDocxPlaceHolders(file, hashMap);
    }

    @Test
    void containsPlaceHolder() throws IOException{
        File file = new File("D:\\Downloads\\Barangay_Clearance_Form_V2.docx");

        Field[] userPersonalFields = UserPersonal.class.getDeclaredFields();
        Set<String> placeHolders = new HashSet<>();

        for(Field holder : userPersonalFields){
            placeHolders.add("{{%s}}".formatted(holder.getName()));
        }

        boolean isValid = DocxFileHandler.containsPlaceHoldersInParagraphs(file, placeHolders);
        assertTrue(isValid);
    }
}