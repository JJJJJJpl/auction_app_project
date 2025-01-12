package com.example.uaim_project.api;

import com.example.uaim_project.model.Auction;
import com.example.uaim_project.model.Bid;
import com.example.uaim_project.model.BidRequest;
import com.example.uaim_project.model.LoginRequest;
import com.example.uaim_project.model.LoginResponse;
import com.example.uaim_project.model.MessageResponse;
import com.example.uaim_project.model.RegisterRequest;
import com.example.uaim_project.model.Transaction;
import com.example.uaim_project.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/register")
    Call<MessageResponse> register(@Body RegisterRequest registerRequest);

    @GET("/auctions")
    Call<List<Auction>> getAuctions();

    @GET("auction/{auction_id}")
    Call<Auction> getAuctionDetails(@Path("auction_id") int auctionId);

    @POST("bid")
    Call<ResponseBody> placeBid(@Header("Authorization") String token, @Body BidRequest bidRequest);

    @GET("/user/{user_id}")
    Call<User> getUser(@Path("user_id") int userId, @Header("Authorization") String token);

    // Endpoint: GET /user/<user_id>/bids
    @GET("/user/{user_id}/bids")
    Call<List<Bid>> getUserBids(@Path("user_id") int userId, @Header("Authorization") String token);

    // Endpoint: GET /user/<user_id>/transactions
    @GET("/user/{user_id}/transactions")
    Call<List<Transaction>> getUserTransactions(@Path("user_id") int userId, @Header("Authorization") String token);

}


