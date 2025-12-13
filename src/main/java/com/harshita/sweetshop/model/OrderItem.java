package com.harshita.sweetshop.model;

public class OrderItem {
    private Long sweetId;
    private int quantity;

    public Long getSweetId() {
        return sweetId;
    }

    public void setSweetId(Long sweetId) {
        this.sweetId = sweetId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
