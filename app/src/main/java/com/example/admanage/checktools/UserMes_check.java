package com.example.admanage.checktools;

public class UserMes_check extends Base_check {

    private boolean islegal;
    private String regex;
    
    public boolean uname_check(String string) {
        islegal=false;
        regex="";
        if(basechar_check(string)){
            islegal=check(regex,string);
        }
        else{

        }
        return islegal;
    }

    public boolean upass_check(String string) {
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
