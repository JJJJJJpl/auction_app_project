package com.example.uaim_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uaim_project.R;
import com.example.uaim_project.api.ApiClient;
import com.example.uaim_project.api.ApiService;
import com.example.uaim_project.model.LoginRequest;
import com.example.uaim_project.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = ApiClient.getClient().create(ApiService.class);

        findViewById(R.id.loginButton).setOnClickListener(view -> {
            String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

            LoginRequest request = new LoginRequest(email, password);
            apiService.login(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        saveAccessToken(response.body().getAccessToken(), response.body().getUsername(), response.body().getUser_id() );
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("API_ERROR", "Nie udało się połączyć: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        });

        findViewById(R.id.backButton).setOnClickListener(view -> finish());
    }

    private void saveAccessToken(String token, String username, String user_id) {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("AccessToken", token);
        editor.putString("Username", username);
        editor.putString("User_id", user_id);
        editor.apply();
    }
}
