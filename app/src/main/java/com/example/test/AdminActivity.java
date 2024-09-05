package com.example.test;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    DrawerLayout Drawer;
    NavigationView navigationView;
    public List<Admin> adminList = new ArrayList<>();
    private AdminAdapter adapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminactivity);

        /*                                          侧滑导航栏部分                             */
        Drawer = findViewById(R.id.home_id);
        navigationView = findViewById(R.id.nav);
        //设置item图标正常显示
        navigationView.setItemIconTintList(null);
        //设置top_main头像下的管理员名称
        View headView = navigationView.getHeaderView(0);
        TextView admin_name = headView.findViewById(R.id.admin_name);
        //通过接收全局变量账号名字
        Myapplication myapplication = (Myapplication)getApplication();
        admin_name.setText("欢迎您，管理员@"+myapplication.getTotal_adname());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // 为item设置逐个点击事件
                switch (menuItem.getItemId()) {
                    //点击跳转到学生管理界面
                    case R.id.nav_student: {
                        Intent intent = new Intent(AdminActivity.this, StudentActivity.class);
                        startActivity(intent);
                        break;

                    }



                    //exit
                    case R.id.nav_exit: {
                        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                    break;


                }
//                Toast.makeText(DormiActivity.this,menuItem.getTitle().toString(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        /*                         如果是super_admin点击添加按钮，添加管理员，else 弹出无权限提示                     */

        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Myapplication myapplication = (Myapplication)getApplication();
                if (myapplication.getTotal_adname().equals(getResources().getString(R.string.super_address))) {
                    Toast.makeText(AdminActivity.this, "超级管理员访问", Toast.LENGTH_SHORT).show();
                    //点击跳转添加管理员信息界面
                    Intent intent = new Intent(AdminActivity.this, AddAdmin.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AdminActivity.this, "您是普通管理员，您没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        });




        /*                               查询数据库，取出数据                           */
        adapter = new AdminAdapter(AdminActivity.this, R.layout.adminbook, adminList);

        listView = (ListView) findViewById(R.id.ad_list);
        listView.setAdapter(adapter);
        dbHelper = new MyDatabaseHelper(this, "Student.db", null, 2);
        init_adminlist();
        CheckBox checkBox = (CheckBox) findViewById(R.id.ad_check_book);



        /*                          点击删除按钮，删除访客信息                             */
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Myapplication myapplication = (Myapplication) getApplication();
                if (myapplication.getTotal_adname().equals(getResources().getString(R.string.super_address))) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    List<Admin> adminList_delete = new ArrayList<>();

                    for (Admin admin : adminList) {
                        if (admin.isChecked()) {
                            adminList_delete.add(admin);
                        }
                    }

                    for (Admin admintbook : adminList_delete) {
                        db.delete("Admin", "ad_id = ?", new String[]{String.valueOf(admintbook.getAd_id())});
                    }
                    adminList_delete.clear();
                    init_adminlist();
                }else {
                    Toast.makeText(AdminActivity.this, "您是普通管理员，没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*                      外部方法                      */
    private void init_adminlist(){
        int enable ;
        Myapplication myapplication = (Myapplication) getApplication();
        if (myapplication.getTotal_adname().equals(getResources().getString(R.string.super_address))) {
            //超级管理员是可以查看其他管理员密码的
            enable = 1;
        }else {
            //普通管理员不能查看他人密码
            enable = 0;
        }
        adminList.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Admin", null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Integer ad_id = cursor.getInt(cursor.getColumnIndex("ad_id"));
                @SuppressLint("Range") String ad_account = cursor.getString(cursor.getColumnIndex("ad_address"));
                @SuppressLint("Range") String ad_passwd = cursor.getString(cursor.getColumnIndex("ad_passwd"));

                if (enable == 0 ){
                    ad_passwd = "*******";
                }

                Admin admin  = new Admin(false,ad_id,ad_account,ad_passwd);

                adminList.add(admin);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        init_adminlist();
    }



    @Override
    public void onBackPressed() {
        if (Drawer.isDrawerOpen(GravityCompat.START)) {
            Drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}


