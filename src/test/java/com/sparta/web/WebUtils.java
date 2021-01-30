package com.sparta.web;

import java.util.Date;

public class WebUtils {

    public static String getDate() {
        return new Date().toString();
    }

    public static String getMultiLineEmailBodyWithDate() {
        return "Hello!\n" +
                "Hope you are doing well.\n" +
                "The mail was created on " + getDate() + ".\n" +
                "Best regards";
    }

}
