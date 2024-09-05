package com.example.test;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddAdmin extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addadmin);
        //找到相应的数据
        ImageButton confirm = (ImageButton)findViewById(R.id.confirm);
        EditText ad_account = (EditText) findViewById(R.id.ad_account);
        EditText ad_passwd = (EditText) findViewById(R.id.ad_passwd);
        //添加数据到数据表中
        dbHelper = new MyDatabaseHelper(this,"Student.db",null,2);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //插入数据
                if(ad_account.getText().toString().equals("")
                        &&ad_passwd.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAdmin.this);
                    builder.setTitle("提示");
                    builder.setMessage("数据不能全为空");
                    builder.setPositiveButton("确定",null);
                    builder.create().show();
                }else {
                    //插入数据
                    values.put("ad_address", ad_account.getText().toString());
                    values.put("ad_passwd", ad_passwd.getText().toString());

                    db.insert("Admin", null, values);
                    Toast.makeText(AddAdmin.this, "插入成功", Toast.LENGTH_SHORT).show();
                    values.clear();
                    finish();
                }
            }
        });
    }
}
