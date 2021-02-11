package com.first.retrofitapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.first.retrofitapp.adapter.MyRecyclerAdapter;
import com.first.retrofitapp.adapter.OnClick;
import com.first.retrofitapp.databinding.ActivityMainBinding;
import com.first.retrofitapp.retrofits.InterfaceAnnotationUser;
import com.first.retrofitapp.retrofits.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public ArrayList<User> listFrom;
    public ArrayList<User> listToWindow;
    Gson gson;
    ActivityMainBinding binding;
    private String searchString;
    private InterfaceAnnotationUser interfaceAnnotationUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intializeFirst();
//        for (int i = 0; i < 100; i++) {
//            listFrom.add(new User("komil","6665464688","564894869464","sdfvadvfav@hjbfvh"));
//            listFrom.add(new User("Qodir","64848644416","684864946494","fvfsvfsvfsvsfv@fvwrfv"));
//            listFrom.add(new User("Nomoz","5566564544","356846846454","frvsergetg654684r6g6"));
//            listFrom.add(new User("Temur","4184848466","6154164164","gfbvsfvebfb@dbbsf"));
//        }
//
//        binding.etSearch.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    searchString = binding.etSearch.getText().toString();
//                    if (searchString.isEmpty() || searchString == null) {
//                        //Toast.makeText(getApplicationContext(), "searchString=null", Toast.LENGTH_SHORT).show();
//                       // MyAdapter myAdapter = new MyAdapter(getApplicationContext(), listFrom);
//                        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(listFrom);
//                        binding.recyclerv.setAdapter(myRecyclerAdapter);
//                        listToWindow.clear();
//                    } else {
//                        // Toast.makeText(getApplicationContext(), searchString, Toast.LENGTH_SHORT).show();
//                        if (!listToWindow.isEmpty()) {
//                            listToWindow.clear();
//                            for (int i = 0; i < listFrom.size(); i++) {
//                                if ((listFrom.get(i).getUserName().toUpperCase()).startsWith(searchString.toUpperCase())) {
//                                    listToWindow.add(listFrom.get(i));
//                                }
//                            }
//                        } else {
//                            for (int i = 0; i < listFrom.size(); i++) {
//                                if (listFrom.get(i).getUserName().toUpperCase().startsWith(searchString.toUpperCase())) {
//                                    listToWindow.add(listFrom.get(i));
//                                }

//
//
//                            }
//
//                        }
////                        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), listToWindow);
////                        binding.listV.setAdapter(myAdapter);
//                        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(listFrom);
//                        binding.recyclerv.setAdapter(myRecyclerAdapter);
//
//                        // listToWindow.clear();
//
//                    }
//
//                    temp = false;
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//            if (temp) {
//                //MyAdapter myAdapter = new MyAdapter(getApplicationContext(), listFrom);
//                //binding.listV.setAdapter(myAdapter);
//                MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(listFrom);
//                binding.recyclerv.setAdapter(myRecyclerAdapter);
//
//            }
//
//            binding.ivHomeAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity((new Intent(MainActivity.this, Create.class)).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//
//                }
//            });
//
//
//
        getAllUsers();
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchString = binding.etSearch.getText().toString();
                if (searchString.isEmpty() || searchString == null) {
                    renderAllUsers(listFrom);
                    listToWindow.clear();
                } else {
                    if (!listToWindow.isEmpty()) {
                        listToWindow.clear();
                        for (int i = 0; i < listFrom.size(); i++) {
                            if ((listFrom.get(i).getUserName().toUpperCase()).startsWith(searchString.toUpperCase())) {
                                listToWindow.add(listFrom.get(i));
                            }
                        }
                    } else {
                        for (int i = 0; i < listFrom.size(); i++) {
                            if (listFrom.get(i).getUserName().toUpperCase().startsWith(searchString.toUpperCase())) {
                                listToWindow.add(listFrom.get(i));
                            }
                        }
                    }
                    renderAllUsers(listToWindow);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        binding.ivHomeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Create.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        intializeFirst();
        getAllUsers();

    }

    private void intializeFirst() {
        listToWindow = new ArrayList<>();
        listFrom = new ArrayList<>();
        gson = new Gson();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.108:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaceAnnotationUser = retrofit.create(InterfaceAnnotationUser.class);
    }



    private void getAllUsers() {
        Call<Status> call = interfaceAnnotationUser.getUsers();
        call.enqueue(new Callback<Status>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (!response.isSuccessful()) {
                    binding.tvError.setVisibility(View.VISIBLE);
                    binding.tvError.setText(response.code() + "\n" + response.message());
                }

                Status statusR = response.body();
                if (statusR.getStatus() == 200) {
                    for (int i = 0; i < statusR.getData().size(); i++) {
                        listFrom.add(i, statusR.getData().get(statusR.getData().size() - 1 - i));
                    }
                    renderAllUsers(listFrom);
                } else {
                    binding.tvError.setVisibility(View.VISIBLE);
                    binding.tvError.setText(statusR.getStatus() + "\n" + statusR.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                binding.tvError.setVisibility(View.VISIBLE);
                binding.tvError.setText(t.getMessage());
            }
        });

    }

    public void renderAllUsers(ArrayList<User> users) {
        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(users, new OnClick() {
            @Override
            public void onItemClick(int x) {
                /*
                biz bu yerda qilgan barcha ishlarimiz MyRecycler Adapter classni ichida onBindingViewHolder ni ichida chaqirilgan paytda ishlatiladi
                int x--- bu bizga holder.itemview.setOnClickLi... chaiqirilgan paytda onCick ni ichida listenier.onItemClick(position) deb bergan position ya'ni
                bosilgan item indexi hisoblanadi
                listener.onItemClick(position)----- OnClick deb yaratib olingan interface imiz ni ichida bergan attribute ga ega bo'lgan
                - void onItemClick(int a);  methodimiz hisoblanadi
                biz MainActivity ni ichida unga tana yozyabmiz   MyRecyclerAdapter ni ichida chaqirayabmiz -----shu xolos!!!!
                 */

                Intent intent = new Intent(MainActivity.this, UserInfo.class);
                intent.putExtra("users", users.get(x));
                startActivity(intent);

            }
        });
        binding.recyclerv.setAdapter(myRecyclerAdapter);
    }

}