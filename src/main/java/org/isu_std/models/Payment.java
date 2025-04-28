package org.isu_std.models;

public record Payment(String paymentType, String paymentNumber, double documentCost, String paymentDateTime){
    @Override
    public String toString(){
        return "%s - %s - %.2f - %s".formatted(paymentType, paymentNumber, documentCost, paymentDateTime);
    }
}
