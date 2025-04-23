package org.isu_std.models;

public record Payment(String paymentType, double documentCost, double paymentAmount){
    @Override
    public String toString(){
        return "%s - %.2f - %.2f".formatted(paymentType, documentCost, paymentAmount);
    }
}
