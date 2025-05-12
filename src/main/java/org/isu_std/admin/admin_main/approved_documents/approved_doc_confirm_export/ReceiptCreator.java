package org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.models.Payment;

import java.io.*;
import java.lang.reflect.RecordComponent;
import java.nio.file.Path;

public class ReceiptCreator {
    protected static void createReceiptFile(
            String fileName, Path targetPath, RequestDocumentContext requestDocumentContext
    ) throws IOException{
        File receiptFile = new File(targetPath.toString() + File.separator + fileName);

        try(FileWriter fileWriter = new FileWriter(receiptFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ){
            bufferedWriter.write(fileName);
            bufferedWriter.newLine();

            String referenceId = requestDocumentContext.documentRequest().referenceId();
            bufferedWriter
                    .append("Reference ID -> ")
                    .append(referenceId);

            addReceiptContents(bufferedWriter, requestDocumentContext);
        }
    }

    private static void addReceiptContents(BufferedWriter bufferedWriter, RequestDocumentContext requestDocumentContext) throws IOException{
        Payment payment = requestDocumentContext.payment();
        String labels = getReceiptLabelStr(payment);

        bufferedWriter.append(labels);
        bufferedWriter.newLine();
        bufferedWriter.append(payment.toString());
    }

    private static String getReceiptLabelStr(Payment payment){
        RecordComponent[] recordComponents = payment.getClass().getRecordComponents();
        StringBuilder stringBuilder = new StringBuilder();

        for(RecordComponent label : recordComponents){
            stringBuilder
                    .append(" ")
                    .append(label.getName().toUpperCase());
        }

        return stringBuilder.toString();
    }
}
