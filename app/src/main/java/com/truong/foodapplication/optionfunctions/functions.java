package com.truong.foodapplication.optionfunctions;
import java.util.Random;
import android.util.Patterns;

public class functions {
    public static int checkValid(String username, String password, String repassword, String displayName){
        if (checkDisplayNameValid(displayName)){
            if (Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                if (checkPasswordValid(password, repassword)){
                    return 0;
                }else {
                    return 1;
                }
            }else {
                return 2;
            }
        }else {
            return 3;
        }
    }
    public static boolean checkDisplayNameValid(String displayName){
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
        if (displayName.length() > 5 && displayName.length() < 18){
            for (int i = 0; i < displayName.length(); i++){
                char c = displayName.charAt(i);
                if (allowedChars.indexOf(c) == -1){
                    return false;
                }
            }
        }else {
            return false;
        }
        return true;
    }
    public static boolean checkPasswordValid(String password, String repassword){
        if (password.length() > 5 && repassword.equals(password)){
            return true;
        }
        return false;
    }
    public static String generateDisplayName(){
        int length = 10;
        String randomString = generateRandomString(length);
        return randomString;
    }
    public static String generateRandomString(int length) {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = allowedChars.charAt(random.nextInt(allowedChars.length()));
            randomString.append(randomChar);
        }
        return randomString.toString();
    }
}
