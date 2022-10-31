package com.lulobank;

public class Utilities {

    public static String getAppId() {
        String appId = "635ef27ac606a25734734e5f"; // It dependes for each environment 
        return appId;
        
    }

    public static String createBody(String firstName, String lastName, String email) {

        return "{\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\",\"email\":\""+email+"\"}";
        
    }

    public static String modifyBody(String firstName, String lastName) {
        return "{\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\"}";
    }
    
}
