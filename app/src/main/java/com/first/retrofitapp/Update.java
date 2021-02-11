package com.first.retrofitapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.first.retrofitapp.databinding.ActivityUpdateBinding;
import com.first.retrofitapp.retrofits.InterfaceAnnotationUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update extends AppCompatActivity {
    ActivityUpdateBinding binding;
    Gson gson;
    InterfaceAnnotationUser interfaceAnnotationUser;
    private User user;
    private User userOld;
    private Intent i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intializeFirst();
        initializeOldInfo();
        binding.btnUUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndUser() == null) {
                    Toast.makeText(Update.this, "You must change anything!!!", Toast.LENGTH_SHORT).show();
                } else {
                    user = checkAndUser();
                    updateUser(user, userOld.get_id());
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
        i = getIntent();
        userOld = (User) i.getSerializableExtra("userOld");
    }

    private void initializeOldInfo() {
        binding.etCUsername.setText(userOld.getUserName());
        binding.etCPhoneNumber.setText(userOld.getPhone_number());
        binding.etCEmail.setText(userOld.getEmail());
        binding.etCPassword.setText(userOld.getPassword());

    }

    private User checkAndUser() {
        boolean temp = true;

        if (binding.etCUsername.getText().toString().isEmpty()) {
            binding.etCUsername.setError("Can't be empty");
            temp = false;
        } else if (binding.etCUsername.getText().toString().length() < 2) {
            binding.etCUsername.setError("can't be less than 2");
            temp = false;
        } else if (binding.etCUsername.getText().toString().length() > 30) {
            binding.etCUsername.setError("can't be more than 30");
        } else {
            binding.etCUsername.setError(null);
        }


        if (binding.etCPhoneNumber.getText().toString().isEmpty()) {
            binding.etCPhoneNumber.setError("Can't be empty");
            temp = false;
        } else if (binding.etCPhoneNumber.getText().toString().length() < 10) {
            temp = false;
            binding.etCPhoneNumber.setError("can't be less than 9");
        } else if (binding.etCPhoneNumber.getText().toString().length() > 15) {
            binding.etCPhoneNumber.setError("can't be too long");
            temp = false;
        } else {
            binding.etCPhoneNumber.setError(null);

        }


        if (binding.etCEmail.getText().toString() == null) {
            binding.etCEmail.setError("Can't be empty");
            temp = false;
        } else if (!binding.etCEmail.getText().toString().contains("@")) {
            binding.etCEmail.setError("must be exist @");
        } else if (!isEmailValid(binding.etCEmail.getText().toString())) {
            binding.etCEmail.setError("invalid etCEmail address");
            temp = false;
        } else {
            binding.etCEmail.setError(null);
        }

        if (binding.etCPassword.getText().toString().isEmpty()) {
            binding.etCPassword.setError("Can't be empty");
            temp = false;
        } else if (binding.etCPassword.getText().toString().length() < 6) {
            binding.etCPassword.setError("can't be less than 6");
            temp = false;
        } else if (binding.etCPassword.getText().toString().length() > 15) {
            binding.etCPassword.setError("can't be more than 15");
        } else {
            binding.etCPassword.setError(null);
        }


        if (temp) {
            if ((!binding.etCUsername.getText().toString().equalsIgnoreCase(userOld.getUserName())) ||
                    (!binding.etCPhoneNumber.getText().toString().equalsIgnoreCase(userOld.getPhone_number())) ||
                    (!binding.etCEmail.getText().toString().equalsIgnoreCase(userOld.getEmail())) ||
                    (!binding.etCPassword.getText().toString().equalsIgnoreCase(userOld.getPassword()))) {
                user = new User(
                        binding.etCUsername.getText().toString(),
                        binding.etCPhoneNumber.getText().toString(),
                        binding.etCEmail.getText().toString(),
                        binding.etCPassword.getText().toString());
                return user;
            }
        }
        return null;
    }

    private void updateUser(User user, String id) {
        Call<User> call = interfaceAnnotationUser.updateByUser(id, user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    binding.errorUpdate.setText(response.code());
                }

                Intent intent = new Intent(Update.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                binding.errorUpdate.setVisibility(View.VISIBLE);
                binding.errorUpdate.setText(t.getMessage());
            }
        });
    }

    public boolean isEmailValid(CharSequence email) {
        if (email == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }
}
