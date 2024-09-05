package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;

//登陆注册的页面
public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"Admin.db",null,2);

        EditText account = (EditText) findViewById(R.id.text_account);
        EditText passwd = (EditText) findViewById(R.id.text_password);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button register_btn = (Button) findViewById(R.id.register_btn);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int num = 0;
        Cursor c = db.rawQuery("select * from Admin",null);
        num=c.getCount();
        c.close();


        if(num==0){//如果是空表，则插入一条创建super_admin的语句
            ContentValues values = new ContentValues();
            values.put("ad_address",getResources().getString(R.string.super_address) );
            values.put("ad_passwd",getResources().getString(R.string.super_passwd) );
            db.insert("Admin", null, values);
            values.clear();
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int  success = 0; //success表示有没有查询到至少一条的匹配结果，即账号密码输的对不对

                //那么此时管理员表中，至少有了一条记录，要将登录信息与数据库内容相匹配，确认登录者的身份
                //查询管理员表
                Cursor cursor = db.query("Admin", null, null, null,
                        null, null, null);
                if (cursor.moveToFirst()) {
                    do {
//                        @SuppressLint("Range") Integer ad_id = cursor.getInt(cursor.getColumnIndex("ad_id"));
                        @SuppressLint("Range") String ad_address = cursor.getString(cursor.getColumnIndex("ad_address"));
                        @SuppressLint("Range") String ad_passwd = cursor.getString(cursor.getColumnIndex("ad_passwd"));
//                        Toast.makeText(MainActivity.this, "address="+ad_address+"and"+ad_passwd, Toast.LENGTH_SHORT).show();

                        //字符串比较别用== 用equal
                        if (ad_address.equals(account.getText().toString())
                                && ad_passwd.equals(passwd.getText().toString()) ){
                            success = 1;

                            //通过全局变量传递账号名字
                            Myapplication myapplication = (Myapplication)getApplication();
                            myapplication.setTotal_adname(ad_address);



                            //点击跳转至学生管理页面
                            Intent intent = new Intent(MainActivity.this, StudentActivity.class);
//                            intent.putExtra("admin_name", admin.getAd_account());
                            startActivity(intent);
                            break;
                        }else {
                            //接着查
                        }

                    } while (cursor.moveToNext());
                }
                if(success == 0 ){//查遍管理员数据库都没有找到对应的账号的话，就是输错了
                    Toast.makeText(MainActivity.this,"账号或密码错误", Toast.LENGTH_SHORT).show();
                }
                cursor.close();

            }
        });


        //注册联系管理员
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"请联系管理员", Toast.LENGTH_SHORT).show();
            }
        });

    }
}