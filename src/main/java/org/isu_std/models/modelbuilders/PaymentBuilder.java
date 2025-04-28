package org.isu_std.models.modelbuilders;

import org.isu_std.models.Payment;

public class PaymentBuilder {
    private String paymentType;
    private String paymentNumber;
    private double documentCost;
    private String paymentDateTime;

    public PaymentBuilder paymentType(String paymentType){
        this.paymentType = paymentType;
        return this;
    }

    public PaymentBuilder paymentNumber(String paymentNumber){
        this.paymentNumber = paymentNumber;
        return this;
    }

    public PaymentBuilder documentCost(double documentCost){
        this.documentCost = documentCost;
        return this;
    }

    public PaymentBuilder paymentDateTime(String paymentDateTime){
        this.paymentDateTime = paymentDateTime;
        return this;
    }

    public Payment build(){
        return new Payment(
                this.paymentType,
                this.paymentNumber,
                this.documentCost,
                this.paymentDateTime
        );
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public double getDocumentCost() {
        return documentCost;
    }

    public String getPaymentDateTime() {
        return paymentDateTime;
    }
}
