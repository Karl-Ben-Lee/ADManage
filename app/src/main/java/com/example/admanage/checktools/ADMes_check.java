package com.example.admanage.checktools;

public class ADMes_check extends Base_check {

    private boolean islegal;
    private String regex;

    //禁止输入<>
    public boolean context_check(String string) {
        islegal=false;
        regex="";

        return islegal;
    }

    //允许输入数字和-
    public boolean phonenum_check(String string) {
        islegal=false;
        regex="^(0?1[358]\\d{9})|((0(10|2[1-3]|[3-9]\\d{2}))?[1-9]\\d{6,7})$";
        islegal=check(regex,string);
        return islegal;
    }

    //允许输入数字和*
    public boolean size_check(String string) {
        islegal=false;
        regex="";

        return islegal;
    }

    //用于经纬度、金额
    public boolean bumformat_check(String string) {
        islegal=false;
        regex="";
        if(basechar_check(string)){
            islegal=check(regex,string);
        }
        else{

        }
        return islegal;
    }

    //用于仅输入汉字和英文的
    public boolean fontonly_check(String string) {
        islegal=false;
        regex="";
        if(basechar_check(string)){
            islegal=check(regex,string);
        }
        else{

        }
        return islegal;
    }

    public boolean _check(String string) {
        islegal=false;
        regex="";
        if(basechar_check(string)){
            islegal=check(regex,string);
        }
        else{

        }
        return islegal;
    }

}
