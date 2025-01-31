package com.example.uaim_project.model;

public class BidRequest {
    private int auction_id;
    private int user_id;
    private double bid_price;

    public BidRequest(int auctionId, int user_id, double bidPrice) {
        this.auction_id = auctionId;
        this.bid_price = bidPrice;
        this.user_id = user_id;
    }

    public int getAuction_id() {
        return auction_id;
    }

    public void setAuction_id(int auction_id) {
        this.auction_id = auction_id;
    }

    public double getBid_price() {
        return bid_price;
    }

    public void setBid_price(double bid_price) {
        this.bid_price = bid_price;
    }
}
