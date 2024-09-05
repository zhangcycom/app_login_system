package com.example.test;


public class Student {
    private boolean checked;
    private Integer id;
    private String stu_no;
    private String name;

    private String dor_no;
    private String password;
    //构造方法
    public Student(boolean checked,Integer id, String stu_no,String name, String dor_no, String password){
        this.id = id;
        this.checked = checked;
        this.stu_no = stu_no;
        this.name = name;

        this.dor_no = dor_no;
        this.password = password;
    }

    //getter and setter
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStu_no() {
        return stu_no;
    }

    public void setStu_no(String stu_no) { this.stu_no = stu_no; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDor_no() {
        return dor_no;
    }

    public void setDor_no(String dor_no) {
        this.dor_no = dor_no;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }
}
