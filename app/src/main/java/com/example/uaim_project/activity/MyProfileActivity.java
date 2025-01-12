package com.example.uaim_project.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaim_project.R;
import com.example.uaim_project.adapter.BidAdapter;
import com.example.uaim_project.adapter.TransactionsAdapter;
import com.example.uaim_project.api.ApiClient;
import com.example.uaim_project.api.ApiService;
import com.example.uaim_project.model.Bid;
import com.example.uaim_project.model.Transaction;
import com.example.uaim_project.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyProfileActivity extends AppCompatActivity {
    private TextView usernameTextView, emailTextView, roleTextView;
    private RecyclerView bidsRecyclerView, transactionsRecyclerView;
    private ApiService apiService;
    private String accessToken;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Inicjalizacja widoków
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        roleTextView = findViewById(R.id.roleTextView);
        bidsRecyclerView = findViewById(R.id.bidsRecyclerView);
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView);
        back = findViewById(R.id.backButton);

        // Konfiguracja RecyclerView
        bidsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pobierz token z SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", "");

        // Inicjalizacja API
        apiService = ApiClient.getClient().create(ApiService.class);

        back.setOnClickListener(v -> finish());

        // Pobierz dane użytkownika
        fetchUserData();
        fetchUserBids();
        fetchUserTransactions();
    }

    private void fetchUserData() {
        // Pobieranie ID użytkownika z JWT (opcjonalnie, zmień jeśli ID masz lokalnie)
        int userId = Integer.parseInt(getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("User_id", "0"));

        apiService.getUser(userId, "Bearer " + accessToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        usernameTextView.setText(user.getUsername());
                        emailTextView.setText(user.getEmail());
                        roleTextView.setText(user.getRole());
                    }
                } else {
                    Toast.makeText(MyProfileActivity.this, "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserBids() {
        int userId = Integer.parseInt(getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("User_id", "0"));

        apiService.getUserBids(userId, "Bearer " + accessToken).enqueue(new Callback<List<Bid>>() {
            @Override
            public void onResponse(Call<List<Bid>> call, Response<List<Bid>> response) {
                if (response.isSuccessful()) {
                    List<Bid> bids = response.body();
                    bidsRecyclerView.setAdapter(new BidAdapter(bids));
                } else {
                    Toast.makeText(MyProfileActivity.this, "Failed to load bids", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bid>> call, Throwable t) {
                Toast.makeText(MyProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserTransactions() {
        int userId = Integer.parseInt(getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("User_id", "0"));

        apiService.getUserTransactions(userId, "Bearer " + accessToken).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful()) {
                    List<Transaction> transactions = response.body();
                    transactionsRecyclerView.setAdapter(new TransactionsAdapter(transactions));
                } else {
                    Toast.makeText(MyProfileActivity.this, "Failed to load transactions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(MyProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
