package com.example.uaim_project.model;

import java.util.List;

public class Auction {
    private int auction_id;
    private String title;
    private String description;
    private double starting_price;
    private String start_time;
    private String end_time;
    private int user_id;
    private String image_url;
    private String status;
    private List<BidAuction> bids;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String img_url) {
        this.image_url = img_url;
    }

    public List<BidAuction> getBids() {
        return bids;
    }

    public void setBids(List<BidAuction> bids) {
        this.bids = bids;
    }

    // Getters and Setters
    public int getAuctionId() {
        return auction_id;
    }

    public void setAuctionId(int auction_id) {
        this.auction_id = auction_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return starting_price;
    }

    public void setStartingPrice(double starting_price) {
        this.starting_price = starting_price;
    }

    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String start_time) {
        this.start_time = start_time;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEndTime(String end_time) {
        this.end_time = end_time;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }
}
