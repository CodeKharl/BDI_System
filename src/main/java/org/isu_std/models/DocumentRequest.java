package org.isu_std.models;

import org.isu_std.io.Util;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
}
