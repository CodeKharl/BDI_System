package org.isu_std.admin.admin_main.approved_documents.approved_doc_confirm_export;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.models.Payment;

import java.io.*;
import java.lang.reflect.RecordComponent;
import java.nio.file.Path;

public class ReceiptCreator {
    private final static String RECEIPT_FILE_NAME = "Receipt!.txt";

    protected static void createReceiptFile(Path targetDirectory, RequestDocumentContext requestDocumentContext) throws IOException{
        File receiptFile = new File(
                targetDirectory.toString() + File.separator + RECEIPT_FILE_NAME
        );

        try(FileWriter fileWriter = new FileWriter(receiptFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ){
            String referenceId = requestDocumentContext.documentRequest().referenceId();
            bufferedWriter.write("REFERENCE ID -> %s".formatted(referenceId));
            bufferedWriter.newLine();

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
                    .append(label.getName().toUpperCase())
                    .append(" - ");
        }

        return stringBuilder.toString();
    }
}
