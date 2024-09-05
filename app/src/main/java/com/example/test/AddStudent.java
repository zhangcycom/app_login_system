package com.example.test;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudent extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstudent);


        ImageButton confirm = (ImageButton)findViewById(R.id.confirm);
        //找到对应的数据
        EditText stu_no = (EditText)findViewById(R.id.stu_no);
        EditText name = (EditText)findViewById(R.id.name);

        EditText dor_no = (EditText)findViewById(R.id.dor_no);
        EditText password = (EditText)findViewById(R.id.password);
        dbHelper = new MyDatabaseHelper(this,"Student.db",null,2);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //不要插入全空的数据

                if (stu_no.getText().toString().equals("")
                        &&name.getText().toString().equals("")

                        &&dor_no.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddStudent.this);
                    builder.setTitle("提示");
                    builder.setMessage("数据不能全为空");
                    builder.setPositiveButton("确定",null);
                    builder.create().show();
                }else {
                    //插入数据
                    values.put("stu_no", stu_no.getText().toString());
                    values.put("name", name.getText().toString());

                    values.put("dor_no", dor_no.getText().toString());
                    values.put("password", password.getText().toString());

                    db.insert("Student", null, values);

                    Toast.makeText(AddStudent.this, "插入成功", Toast.LENGTH_SHORT).show();
                    values.clear();
                    finish();
                }

            }
        });

    }
}
