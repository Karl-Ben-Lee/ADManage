package com.example.admanage.checktools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Base_check {
    //检查输入文本是否包含除,.(英文)之外的符号 除广告内容外所有的文本输入都首先使用该方法检查
    public boolean basechar_check(String string){
        boolean islegal=false;
        String regex="";
        Pattern pattern =Pattern.compile(regex);
        Matcher matcher=pattern.matcher(string);
        islegal=matcher.matches();
        return islegal;
    }

    public boolean check(String regex,String string){
        boolean islegal=false;
        Pattern pattern =Pattern.compile(regex);
        Matcher matcher=pattern.matcher(string);
        islegal=matcher.matches();
        return islegal;
    }
}
