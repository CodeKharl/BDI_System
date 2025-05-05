package org.isu_std.models.model_builders;

import org.isu_std.io.Util;
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

    public void setPayment(Payment payment){
        this.paymentType = payment.paymentType();
        this.paymentNumber = payment.paymentNumber();
        this.documentCost = payment.documentCost();
        this.paymentDateTime = payment.paymentDateTime();
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

    public String toString(){
        return "%s - %s - %.2f - %s".formatted(paymentType, paymentNumber, documentCost, paymentDateTime);
    }

    public void printPaymentDetails(){
        Util.printSubSectionTitle("Payment Details");
        Util.printInformation("Payment Type -> " + paymentType);
        Util.printInformation("Payment Number -> " + paymentNumber);
        Util.printInformation("Document Cost -> " + documentCost);
        Util.printInformation("Payment DateTime -> " + paymentDateTime);
    }
}
