package com.sql.sqlinjecttool.util;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpSend {
    public static HttpURLConnection sendHttpRequest(String temp,String method) throws MalformedURLException {
        HttpURLConnection con = null;
        try{
        URL url = new URL(temp);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
       return con;
    }catch (Exception e){
            e.printStackTrace();
        }
        return con;

        }
}
