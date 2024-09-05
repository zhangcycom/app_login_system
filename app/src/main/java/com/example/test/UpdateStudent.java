package com.example.test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudent extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatestudent);
        dbHelper = new MyDatabaseHelper(this,"Student.db",null,2);
        //获得Intent from Admin
        Intent intent = this.getIntent();

        ImageButton update = (ImageButton)findViewById(R.id.confirm2);
        //找到对应的数据
        EditText stu_no = (EditText)findViewById(R.id.stu_no2);
        stu_no.setText(intent.getStringExtra("stu_no"));

        EditText name = (EditText)findViewById(R.id.name2);
        name.setText(intent.getStringExtra("name"));

        EditText dor_no = (EditText)findViewById(R.id.dor_no2);
        dor_no.setText(intent.getStringExtra("dor_no"));

        EditText password = (EditText)findViewById(R.id.passoword2);
        password.setText(intent.getStringExtra("password"));

        Integer id = intent.getIntExtra("id",0);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //更新数据
                values.put("stu_no",stu_no.getText().toString());
                values.put("name",name.getText().toString());
                values.put("dor_no",dor_no.getText().toString());
                values.put("password",password.getText().toString());
                //执行update操作
                db.update("Student",values,"id = ?",new String[]{String.valueOf(id)});
                finish();

            }
        });
    }
}
