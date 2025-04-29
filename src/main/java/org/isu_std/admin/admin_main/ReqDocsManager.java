package org.isu_std.admin.admin_main;

import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.Payment;
import org.isu_std.models.UserPersonal;

public record ReqDocsManager(DocumentRequest documentRequest, UserPersonal userPersonal, Document document,
                             Payment payment) {

    public String getReferenceId(){
        return this.documentRequest.referenceId();
    }

    public int getUserId() {
        return this.documentRequest.userId();
    }

    public int getDocumentId() {
        return this.documentRequest.documentId();
    }

}
