package com.first.retrofitapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.first.retrofitapp.databinding.ActivityCreateBinding;
import com.first.retrofitapp.retrofits.InterfaceAnnotationUser;
import com.first.retrofitapp.retrofits.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Create extends AppCompatActivity {
    ActivityCreateBinding binding;
    InterfaceAnnotationUser interfaceAnnotationUser;
    User user;
    Gson gson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intializeFirst();
        binding.btnCCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (checkAndUser()!=null){
                   createUser(checkAndUser());
               }
            }
        });

    }
    private void intializeFirst() {
        user = new User();
        gson = new Gson();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.108:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaceAnnotationUser = retrofit.create(InterfaceAnnotationUser.class);
    }

    private void createUser(User user) {
        Call<Status> call = interfaceAnnotationUser.createuser(user);
        call.enqueue(new Callback<Status>() {

            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Status status = response.body();

                if (status.getStatus() == 200) {
                    Toast.makeText(Create.this, "successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                binding.createError.setText(t.getMessage());
            }
        });
    }

    private User checkAndUser() {
        boolean temp = true;

        if (binding.username.getEditText().getText().toString().isEmpty()) {
            binding.username.setError("Can't be empty");
            temp = false;
        } else if (binding.username.getEditText().getText().toString().length() < 2) {
            binding.username.setError("can't be less than 2");
            temp = false;
        } else if (binding.username.getEditText().getText().toString().length() > 30) {
            binding.username.setError("can't be more than 30");
        } else {
            binding.username.setError(null);
        }


        if (binding.phoneNumber.getEditText().getText().toString().isEmpty()) {
            binding.phoneNumber.setError("Can't be empty");
            temp = false;
        } else if (binding.phoneNumber.getEditText().getText().toString().length() < 10) {
            temp = false;
            binding.phoneNumber.setError("can't be less than 9");
        } else if (binding.phoneNumber.getEditText().getText().toString().length() > 15) {
            binding.phoneNumber.setError("can't be too long");
            temp = false;
        } else {
            binding.phoneNumber.setError(null);

        }


        if (binding.email.getEditText().getText().toString() == null) {
            binding.email.setError("Can't be empty");
            temp = false;
        } else if (!binding.email.getEditText().getText().toString().contains("@")) {
            binding.email.setError("must be exist @");
            temp=false;
        } else if (!isEmailValid(binding.email.getEditText().getText().toString())) {
            binding.email.setError("invalid email address");
            temp = false;
        } else {
            binding.email.setError(null);
        }

        if (binding.password.getEditText().getText().toString().isEmpty()) {
            binding.password.setError("Can't be empty");
            temp = false;
        } else if (binding.password.getEditText().getText().toString().length() < 6) {
            binding.password.setError("can't be less than 6");
            temp = false;
        } else if (binding.password.getEditText().getText().toString().length() > 15) {
            binding.password.setError("can't be more than 15");
        } else {
            binding.password.setError(null);
        }

        if (temp) {
            user = new User(
                    binding.username.getEditText().getText().toString(),
                    binding.phoneNumber.getEditText().getText().toString(),
                    binding.email.getEditText().getText().toString(),
                    binding.password.getEditText().getText().toString());
            createUser(user);
        }

        return user;
    }

    public boolean isEmailValid(CharSequence email) {
        if (email == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }
}
