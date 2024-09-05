package com.example.test;

//控制手机学生信息页面显示的内容：姓名、学号、寝室

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter {
    private int resourceId;

    public StudentAdapter(Context context, int textViewResourceID, List<Student> objects) {
        super(context, textViewResourceID, objects);
        resourceId = textViewResourceID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = (Student) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //学生管理页面显示， 姓名  学号 寝室号 密码
//        TextView id = (TextView)view.findViewById(R.id.id_book);
        TextView name = (TextView)view.findViewById(R.id.name_book);
        TextView stu_no = (TextView) view.findViewById(R.id.stu_no_book);
        TextView dor_no = (TextView) view.findViewById(R.id.dor_no_book);
        //TextView password = (TextView) view.findViewById(R.id.password_book);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_book);

//        id.setText(student.getId().toString());
        name.setText(student.getName());
        stu_no.setText(student.getStu_no());
        dor_no.setText(student.getDor_no());
        //password.setText(student.getPassword());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                student.setChecked(checkBox.isChecked());
            }
        });

        return view;
    }
}
