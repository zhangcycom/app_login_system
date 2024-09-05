package com.example.test;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class AdminAdapter extends ArrayAdapter{
    private int resourceId;

    public AdminAdapter(Context context, int textViewResourceID, List<Admin> objects) {
        super(context, textViewResourceID, objects);
        resourceId = textViewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Admin admin = (Admin) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView id = (TextView)view.findViewById(R.id.ad_id_book);
        TextView ad_account = (TextView)view.findViewById(R.id.ad_account_book);
        TextView ad_passwd = (TextView) view.findViewById(R.id.ad_passwd_book);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.ad_check_book);

        id.setText(admin.getAd_id().toString());


        //        TextView ad_passwd = (TextView)findViewById(R.id.ad_passwd_book);
//        Myapplication myapplication = (Myapplication) getApplication();
//        if (myapplication.getTotal_adname().equals(getResources().getString(R.string.super_address))) {
//            //超级管理员是可以查看其他管理员密码的
//        }else {
//            //普通管理员不能查看他人密码
//            ad_passwd.setText("*******");
//        }
//        Myapplication myapplication = (Myapplication) getApplication();
//        if (myapplication.getTotal_adname().equals(getResources().getString(R.string.super_address))) {)
        ad_account.setText(admin.getAd_account());
        ad_passwd.setText(admin.getAd_passwd());


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                admin.setChecked(checkBox.isChecked());
            }
        });

        return view;
    }
}

