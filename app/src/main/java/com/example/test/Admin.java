package com.example.test;



public class Admin {


    private boolean checked;
    private Integer ad_id;
    private String ad_account;
    private String ad_passwd;

    public Admin(boolean checked, Integer ad_id, String ad_account, String ad_passwd){
        this.checked = checked;
        this.ad_id = ad_id;
        this.ad_account = ad_account;
        this.ad_passwd = ad_passwd;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getAd_id() {
        return ad_id;
    }

    public void setAd_id(Integer ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_account() {
        return ad_account;
    }

    public void setAd_account(String ad_account) {
        this.ad_account = ad_account;
    }

    public String getAd_passwd() {
        return ad_passwd;
    }

    public void setAd_passwd(String ad_passwd) {
        this.ad_passwd = ad_passwd;
    }

}
