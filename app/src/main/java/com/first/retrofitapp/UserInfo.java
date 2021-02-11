package com.first.retrofitapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.first.retrofitapp.databinding.ActivityUserInfoBinding;
import com.first.retrofitapp.retrofits.InterfaceAnnotationUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.LENGTH_SHORT;

public class UserInfo extends AppCompatActivity {
    ActivityUserInfoBinding binding;
    Gson gson;
    InterfaceAnnotationUser interfaceAnnotationUser;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intializeFirst();
        initializeInfo();

        binding.btnIUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfo.this, Update.class);
                intent.putExtra("userOld", user);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.btnIDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alter = new AlertDialog.Builder(UserInfo.this);
                alter.setTitle("are you sure to delete this user?");
                alter.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteU();
                    }
                });
                alter.setNegativeButton("cancel", null);

                alter.show();
            }
        });

    }

    private void deleteU() {
        Call<Void> call = interfaceAnnotationUser.deleteUser(user.get_id());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    binding.errorDelete.setVisibility(View.VISIBLE);
                    binding.errorDelete.setText(response.code());
                }
                Toast.makeText(UserInfo.this, "successfully", LENGTH_SHORT).show();
                onBackPressed();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                binding.errorDelete.setVisibility(View.VISIBLE);
                binding.errorDelete.setText(t.getMessage());
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
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("users");
    }

    private void initializeInfo() {
        binding.tvUsername.setText(user.getUserName());
        binding.tvIPhoneNumber.setText(user.getPhone_number());
        binding.tvIEmail.setText(user.getEmail());
        binding.tvIPassword.setText(user.getPassword());
    }
}
