package com.example.test;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    DrawerLayout Drawer;
    NavigationView navigationView;

    public List<Student> students = new ArrayList<>();
    private MyDatabaseHelper dbHelper;
    private StudentAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentactivity);



        /*                                          搜索框部分                             */
        ImageButton search_button = findViewById(R.id.search_button);
        EditText search_edit = findViewById(R.id.search_edit);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                String search_txt = search_edit.getText().toString();
                if (search_txt.equals("")){
                    students.clear();
                    init_students();
                    adapter.notifyDataSetChanged();
                }else {
//                    UpdateListView(search_txt);
                    init_students();
                    Iterator<Student> it = students.iterator();
                    while (it.hasNext()) {
                        Student now = it.next();
                        String name = now.getName();
                        String stu_no = now.getStu_no();
                        String dor_no = now.getDor_no();
                        String password = now.getPassword();

                        if (name.indexOf(search_txt)!= -1
                                ||stu_no.indexOf(search_txt)!=-1

                                ||dor_no.indexOf(search_txt)!=-1
                                ||password.indexOf(search_txt)!=-1){

                        }else {
                            it.remove();
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        /*                                          侧滑导航栏部分                             */
        Drawer = findViewById(R.id.home_id);
        navigationView =findViewById(R.id.nav);
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
                switch (menuItem.getItemId()){

                    //点击跳转管理员界面
                    case R.id.nav_admin:{
                        Intent intent = new Intent(StudentActivity.this, AdminActivity.class);
                        startActivity(intent);
                        break;
                    }
                    //点击退出登录，回到登录界面
                    case  R.id.nav_exit:{
                        Intent intent = new Intent(StudentActivity.this,MainActivity.class);
                        startActivity(intent);

                    }break;


                }
//                Toast.makeText(AdminActivity.this,menuItem.getTitle().toString(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        /*                          点击添加按钮，跳转到学生信息页面                             */
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击跳转添加学生信息界面
                Intent intent = new Intent(StudentActivity.this,AddStudent.class);
                startActivity(intent);
            }
        });

        /*                               查询数据库，取出数据                           */
        adapter = new StudentAdapter(StudentActivity.this, R.layout.studentbook, students);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        dbHelper = new MyDatabaseHelper(this, "Student.db", null, 2);
        init_students();
        CheckBox checkBox = (CheckBox) findViewById(R.id.check_book);

        //点击查看详细信息
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = students.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                builder.setTitle("详细信息");
                builder.setMessage("序号："+student.getId().toString()+
                        "\n姓名："+student.getName()+
                        "\n学号："+student.getStu_no()+

                        "\n宿舍："+student.getDor_no()+
                        "\n密码："+student.getPassword()
                );
                builder.setPositiveButton("确定",null);
                builder.create().show();
//                Toast.makeText(Admin.this,"点击查看详细信息哦",Toast.LENGTH_SHORT).show();
            }
        });

        //长按进入修改页面
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                @Override
                                                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    Student student = students.get(i);
                                                    Intent intent = new Intent(StudentActivity.this,UpdateStudent.class);
                                                    intent.putExtra("id", student.getId());//integer
                                                    intent.putExtra("stu_no", student.getStu_no());
                                                    intent.putExtra("name", student.getName());

                                                    intent.putExtra("dor_no", student.getDor_no());
                                                    intent.putExtra("password", student.getPassword());

                                                    startActivity(intent);
                                                    return true;
                                                }
                                            }
        );

        /*                          点击删除按钮，删除学生信息                             */
        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                List<Student> students_delete = new ArrayList<>();

                for (Student student : students) {
                    if (student.isChecked()) {
                        students_delete.add(student);
                    }
                }

                for (Student studentbook : students_delete) {
                    db.delete("Student", "stu_no = ?", new String[]{studentbook.getStu_no()});
                }
                students_delete.clear();
                init_students();

            }
        });
    }

    /*                      外部方法                      */
    private void init_students(){
        students.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Student", null, null, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String stu_no = cursor.getString(cursor.getColumnIndex("stu_no"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));

                @SuppressLint("Range") String dor_no = cursor.getString(cursor.getColumnIndex("dor_no"));
                @SuppressLint("Range") String password= cursor.getString(cursor.getColumnIndex("password"));

                Student student = new Student(false, id, stu_no, name,  dor_no,  password);
                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onResume() {
        super.onResume();
        init_students();
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
