package com.example.uaim_project.model;

public class Transaction {
    private int transaction_id;
    private int auction_id;
    private String auction_title;
    private String payment_status;
    private String transaction_time;

    // Gettery i Settery

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(int auction_id) {
        this.auction_id = auction_id;
    }

    public String getAuction_title() {
        return auction_title;
    }

    public void setAuction_title(String auction_title) {
        this.auction_title = auction_title;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }
}

