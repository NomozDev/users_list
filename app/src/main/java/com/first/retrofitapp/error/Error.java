package com.first.retrofitapp.error;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.first.retrofitapp.databinding.ActivityErrorBinding;

public class Error extends AppCompatActivity {
    ActivityErrorBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityErrorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}
