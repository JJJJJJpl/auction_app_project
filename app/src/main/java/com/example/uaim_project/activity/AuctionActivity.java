package com.example.uaim_project.activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.uaim_project.R;
import com.example.uaim_project.api.ApiClient;
import com.example.uaim_project.api.ApiService;
import com.example.uaim_project.model.Auction;
import com.example.uaim_project.model.BidRequest;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuctionActivity extends AppCompatActivity {
    private TextView titleTextView, descriptionTextView, priceTextView, endTimeTextView, highestBidTextView;
    private EditText bidAmountTextEdit;
    private Button bidButton, backButton;
    private ImageView auctionImageView;
    private ApiService apiService;
    private int auctionId;
    private String accessToken;
    private double currentPrice;
    private boolean isAuctionActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_activity);

        // Inicjalizacja widoków
        auctionImageView = findViewById(R.id.auctionImageView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        priceTextView = findViewById(R.id.priceTextView);
        endTimeTextView = findViewById(R.id.endTimeTextView);
        highestBidTextView = findViewById(R.id.highestBidTextView);
        bidAmountTextEdit = findViewById(R.id.bidAmountTextEdit);
        bidButton = findViewById(R.id.bidButton);
        backButton = findViewById(R.id.backButton);

        // Pobranie danych z Intentu
        auctionId = getIntent().getIntExtra("auction_id", -1);
        accessToken = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("AccessToken", "");

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchAuctionDetails();

        if (accessToken.isEmpty()){
            bidButton.setEnabled(false);
            bidAmountTextEdit.setEnabled(false);
        }

        // Obsługa przycisku licytacji
        bidButton.setOnClickListener(v -> placeBid());

        // Obsługa przycisku powrotu
        backButton.setOnClickListener(v -> finish());
    }

    private void fetchAuctionDetails() {
        apiService.getAuctionDetails(auctionId).enqueue(new Callback<Auction>() {
            @Override
            public void onResponse(Call<Auction> call, Response<Auction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Auction auction = response.body();
                    titleTextView.setText(auction.getTitle());
                    descriptionTextView.setText(auction.getDescription());
                    priceTextView.setText("Starting Price: $" + auction.getStartingPrice());
                    endTimeTextView.setText("End Time: " + auction.getEndTime());

                    // Wyświetlanie obrazka aukcji
                    String imageUrl = "http://10.0.2.2:5000/imagesForAuctions/" + auction.getImageUrl();
                    Glide.with(AuctionActivity.this).load(imageUrl).into(auctionImageView);

                    // Wyświetlanie najwyższej stawki
                    if (auction.getBids() != null && !auction.getBids().isEmpty()) {
                        double highestBid = auction.getBids().get(0).getBidPrice();
                        Log.i("log", "Bids: " + auction.getBids().get(0).toString());
                        highestBidTextView.setText("Highest Bid: $" + highestBid);
                        currentPrice = highestBid;
                    } else {
                        highestBidTextView.setText("No bids yet");
                        currentPrice = auction.getStartingPrice();
                    }

                    // Blokowanie licytacji, jeśli aukcja nie jest aktywna
                    isAuctionActive = "aktywny".equals(auction.getStatus()) && !accessToken.isEmpty();
                    bidButton.setEnabled(isAuctionActive);
                    bidAmountTextEdit.setEnabled(isAuctionActive);

                    if (!isAuctionActive) {
                        Toast.makeText(AuctionActivity.this, "Auction is not active", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AuctionActivity.this, "Failed to load auction details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Auction> call, Throwable t) {
                Toast.makeText(AuctionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void placeBid() {
        String bidAmount = bidAmountTextEdit.getText().toString();
        if (bidAmount.isEmpty()) {
            Toast.makeText(this, "Please enter a bid amount", Toast.LENGTH_LONG).show();
            return;
        }
        double bidValue = Double.parseDouble(bidAmount);
        if (bidValue <= currentPrice) {
            Toast.makeText(this, "Bid must be higher than current price", Toast.LENGTH_LONG).show();
            return;
        }

        BidRequest bidRequest = new BidRequest(auctionId, bidValue);
        apiService.placeBid("Bearer " + accessToken, bidRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AuctionActivity.this, "Bid placed successfully!", Toast.LENGTH_LONG).show();
                    fetchAuctionDetails();
                } else {
                    Toast.makeText(AuctionActivity.this, "Failed to place bid", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AuctionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

