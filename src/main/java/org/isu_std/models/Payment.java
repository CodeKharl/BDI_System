package org.isu_std.models;

import org.isu_std.io.Util;

public record Payment(String paymentType, String paymentNumber, double documentCost, String paymentDateTime){
    @Override
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
