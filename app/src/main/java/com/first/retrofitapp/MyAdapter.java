package com.first.retrofitapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> users;

    public MyAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        }
        TextView tv_username = view.findViewById(R.id.tv_username);
        TextView tv_phone_number = view.findViewById(R.id.tv_phone_number);
        TextView tv_createAt = view.findViewById(R.id.tv_createAt);
        tv_username.setText(users.get(position).getUserName());
        tv_phone_number.setText(users.get(position).getPhone_number());


        tv_createAt.setText("date 21.23.1212");


//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UserInfo.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//so'rab tushinib ollllllll
//                context.startActivity(intent);
//            }
//        });

        return view;
    }
}
