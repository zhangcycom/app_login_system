package com.example.test;

import android.app.Application;

public class Myapplication extends Application {
    public String getTotal_adname() {
        return Total_adname;
    }

    public void setTotal_adname(String total_adname) {
        Total_adname = total_adname;
    }

    private String Total_adname ;

}
