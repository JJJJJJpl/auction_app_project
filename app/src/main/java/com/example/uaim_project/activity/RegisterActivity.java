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
import com.example.uaim_project.model.MessageResponse;
import com.example.uaim_project.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        apiService = ApiClient.getClient().create(ApiService.class);

        findViewById(R.id.registerButton).setOnClickListener(view -> {
            String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
            String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
            String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
            String password2 = ((EditText) findViewById(R.id.passwordEditText2)).getText().toString();

            if( email.isEmpty() || name.isEmpty() || password.isEmpty() || password2.isEmpty() ){
                Toast.makeText(RegisterActivity.this, "Empty field", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!password2.contentEquals(password)){
                Toast.makeText(RegisterActivity.this, "Not matching password", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterRequest request = new RegisterRequest(email,name, password);
            apiService.register(request).enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    Log.e("API_ERROR", "Nie udało się połączyć: " + t.getMessage());
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            });
        });

        findViewById(R.id.backButton).setOnClickListener(view -> finish());
    }

}
