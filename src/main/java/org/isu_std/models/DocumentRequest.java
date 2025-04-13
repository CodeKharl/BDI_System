package org.isu_std.models;

import org.isu_std.io.Util;

import java.io.File;
import java.util.List;

public record DocumentRequest(
        String referenceId,
        int userId,
        int barangayId,
        int documentId,
        List<File> requirementDocList
){
    public String getDocIdWithUserId(){
        return "%d - %d".formatted(userId, documentId);
    }
}
