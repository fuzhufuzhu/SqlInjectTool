package com.sql.sqlinjecttool;

import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.payload.BooleanPayload;
import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.HttpSend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Main {

        public static void main(String[] args) throws IOException {

            String input = "http://127.0.0.1/sqli-labs/Less-1/?id=1&cid=2&op=1a";

            UserInput correctInput = new UserInput(input);
            System.out.println("首次收集数据包完毕");
            System.out.println("-----进行boolean注入---");

            ParamOperation paramOperation = new ParamOperation();
            //
            for(int i =0;i<BooleanPayload.booleanFalesPayload.size();i++){

                String falesPayload = paramOperation.Connect(0,correctInput,BooleanPayload.booleanFalesPayload.get(i).toString());
                String turePayload = paramOperation.Connect(0,correctInput,BooleanPayload.booleanTurePayload.get(i).toString());
                //new 两个对象将结果进行保存 用于今后比对
                //通过传url 完成对象的创建
                 UserInput falesPayloadInput =  new UserInput(falesPayload);
                UserInput turePayloadInput =  new UserInput(turePayload);

                System.out.println("此时false长度为"+turePayloadInput.getHtmlLength());
                System.out.println("此时ture长度为"+turePayloadInput.getHtmlLength());


                //创建Judege工具类 实现传入实现有的对象 完成判别

               // System.out.println("该处存在异常");
            }


            //请求该页面






        }
    }



