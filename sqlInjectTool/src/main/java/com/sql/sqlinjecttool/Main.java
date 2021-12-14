package com.sql.sqlinjecttool;

import com.sql.sqlinjecttool.judge.Judge;
import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.payload.BooleanPayload;
import com.sql.sqlinjecttool.payload.SleepPayload;
import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.HttpSend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

public class Main {

        public static void main(String[] args) throws IOException {

            String input = "http://127.0.0.1/sqli-labs/Less-5/?id=2";
            //设置一个按钮，用于开关检测 0代表检测开启 1代表关闭
            int button = 0;

            UserInput correctInput = new UserInput(input);
            System.out.println("首次收集数据包完毕");
            System.out.println("-----进行boolean注入---");

            ParamOperation paramOperation = new ParamOperation();
            Judge judge = new Judge();
            for(int i =0;i<BooleanPayload.booleanFalesPayload.size();i++){
                String falesPayload = paramOperation.Connect(0,correctInput,BooleanPayload.booleanFalesPayload.get(i).toString());
                String turePayload = paramOperation.Connect(0,correctInput,BooleanPayload.booleanTurePayload.get(i).toString());
                //new 两个对象将结果进行保存 用于今后比对
                //通过传url 完成对象的创建
                UserInput falesPayloadInput =  new UserInput(falesPayload);
                UserInput turePayloadInput =  new UserInput(turePayload);


                if(judge.BooleanJudge(falesPayloadInput,turePayloadInput,button)==1){
                    System.out.println("检测到sql注入，url为"+ URLDecoder.decode(turePayloadInput.getUrl()));
                    button = 1;
                    break;
                }
            }
            if(button!=1){
            System.out.println("布尔盲注未发现结果");
            System.out.println("开始延时注入");}{
                System.out.println("开始跑数据");
            }
            button=0;
            for(int i =0;i< SleepPayload.sleepPayload.size();i++){


                String sleepPayload = paramOperation.Connect(0,correctInput,SleepPayload.sleepPayload.get(i).toString());
                UserInput sleepPayloadInput =  new UserInput(sleepPayload);
                System.out.println(sleepPayloadInput.getStartTimeStamp());
                System.out.println(sleepPayloadInput.getEndTimeStamp());
                int mark= judge.SleepJudge(sleepPayloadInput,button);

                if(mark==1){
                    System.out.println(mark);
                    System.out.println("检测到sql注入，url为"+ URLDecoder.decode(sleepPayloadInput.getUrl()));
                    button = 1;
                    break;
                }
            }
        }
    }



