package org.isu_std.models;

import java.io.File;
import java.util.List;

public record DocumentRequest(
        String referenceId,
        int userId,
        int barangayId,
        int documentId,
        List<File> requirementDocList
){

    @Override
    public String toString(){
        // only print the two values to hide the other values.
        return "%d - %d".formatted(userId, documentId);
    }

    public String getWithReferenceId(){
        return "%s -> %d - %d".formatted(referenceId, userId, documentId);
    }
}
