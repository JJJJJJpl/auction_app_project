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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaim_project.R;
import com.example.uaim_project.adapter.AuctionAdapter;
import com.example.uaim_project.api.ApiClient;
import com.example.uaim_project.api.ApiService;
import com.example.uaim_project.model.Auction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ApiService apiService;
    private String accessToken = "";
    private String username = "";
    private RecyclerView recyclerView;
    private Button loginButton, registerButton, logoutButton, meButton;
    private TextView usernameText;
    public static int MY_PERMISSION_REQUEST = 1;
    private static final int LOGIN_REQUEST_CODE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        apiService = ApiClient.getClient().create(ApiService.class);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        logoutButton = findViewById(R.id.logoutButton);
        recyclerView = findViewById(R.id.recyclerView);
        usernameText = findViewById(R.id.usernameText);
        meButton = findViewById(R.id.meButton);

        // Button actions
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            accessToken = ""; // Usunięcie tokena
            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("AccessToken");
            editor.apply();

            updateButtonsVisibility(); // Odświeżenie widoku
            Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
        });

        meButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
            startActivity(intent);
        });



        //load in token
        loadAccessToken();

        //update visibility
        updateButtonsVisibility();

        // api call
        loadAuctions();

    }

    private void loadAuctions() {
        apiService.getAuctions().enqueue(new Callback<List<Auction>>() {
            @Override
            public void onResponse(Call<List<Auction>> call, Response<List<Auction>> response) {
                if (response.isSuccessful()) {
                    List<Auction> auctions = response.body();
                    recyclerView.setAdapter(new AuctionAdapter(MainActivity.this, auctions));
                    Toast.makeText(MainActivity.this, "Loaded auctions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Auction>> call, Throwable t) {
                Log.e("API_ERROR", "Nie udało się połączyć: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to load auctions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateButtonsVisibility() {
        Log.i("log", "token: " + accessToken);
        if (accessToken.isEmpty()) { // Brak tokena - pokazuje logowanie i rejestrację
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
            usernameText.setVisibility(View.GONE);
            meButton.setVisibility(View.GONE);
        } else { // Zalogowany użytkownik
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            usernameText.setText(username);
            usernameText.setVisibility(View.VISIBLE);
            meButton.setVisibility(View.VISIBLE);
        }
    }

    private void loadAccessToken() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        accessToken = prefs.getString("AccessToken", "");
        username = prefs.getString("Username", "");
    }

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSION_REQUEST);
        }
    }
}