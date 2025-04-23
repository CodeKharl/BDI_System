package org.isu_std.admin.admin_main;

import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.Payment;
import org.isu_std.models.UserPersonal;

public class ReqDocsManagerBuilder {
    private final DocumentRequest documentRequest;

    private UserPersonal userPersonal;
    private Document document;
    private Payment payment;

    public ReqDocsManagerBuilder(DocumentRequest documentRequest){
        this.documentRequest = documentRequest;
    }

    public ReqDocsManagerBuilder userPersonal(UserPersonal userPersonal){
        this.userPersonal = userPersonal;
        return this;
    }

    public ReqDocsManagerBuilder document(Document document){
        this.document = document;
        return this;
    }

    public ReqDocsManagerBuilder payment(Payment payment){
        this.payment = payment;
        return this;
    }

    public ReqDocsManager build(){
        return new ReqDocsManager(this.documentRequest, this.userPersonal, this.document, this.payment);
    }
}
