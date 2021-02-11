package com.first.retrofitapp.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.first.retrofitapp.R;
import com.first.retrofitapp.User;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    ArrayList<User> users;
    OnClick listener;

    public MyRecyclerAdapter(ArrayList<User> users) {
        this.users = users;
    }

    public MyRecyclerAdapter(ArrayList<User> users, OnClick listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false));
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvname.setText(users.get(position).getUserName());
        holder.tvPhone.setText(users.get(position).getPhone_number());
        Date date = Date.from(Instant.parse(users.get(position).getCreateAt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm / dd.MM.yyyy");
        String date1 = simpleDateFormat.format(date);
        holder.tvCreateAt.setText(date1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvname;
        TextView tvPhone;
        TextView tvCreateAt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.tv_username);
            tvPhone = itemView.findViewById(R.id.tv_phone_number);
            tvCreateAt = itemView.findViewById(R.id.tv_createAt);
        }
    }
}
