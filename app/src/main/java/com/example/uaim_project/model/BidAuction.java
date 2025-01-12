package com.example.uaim_project.model;

public class BidAuction {
    private double bid_price;
    private String bid_time;
    private int user_id;

    @Override
    public String toString() {
        return "BidAuction{" +
                "bidPrice=" + bid_price +
                ", bidTime='" + bid_time + '\'' +
                ", userId=" + user_id +
                '}';
    }

    // Gettery i settery
    public double getBidPrice() {
        return bid_price;
    }

    public void setBidPrice(double bidPrice) {
        this.bid_price = bidPrice;
    }

    public String getBidTime() {
        return bid_time;
    }

    public void setBidTime(String bidTime) {
        this.bid_time = bidTime;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }
}
