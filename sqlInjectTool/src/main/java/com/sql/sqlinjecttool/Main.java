package com.sql.sqlinjecttool;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.sql.sqlinjecttool.judge.Judge;
import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.payload.BooleanPayload;
import com.sql.sqlinjecttool.payload.SleepPayload;
import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.HttpSend;
import com.sql.sqlinjecttool.util.ResolvingPost;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

public class Main {

        public static void main(String[] args) throws IOException {


            //设置一个按钮，用于开关检测 0代表检测开启 1代表关闭
            int button = 0;
            String input = "";
            UserInput correctInput =null;
            String method ;
            ResolvingPost resolvingPostFirst = null;
            ResolvingPost resolvingPost = null;

            //收集用户输入
            Scanner scanner = new Scanner(System.in);
            System.out.println("是否为Post型注入,输入Y/N");
           String in= scanner.nextLine();

           if(in.equals("y")||in.equals("Y")){
               method ="POST";
               System.out.println("输入数据包路径");
               String path = scanner.nextLine();
               resolvingPostFirst = new ResolvingPost();
               resolvingPostFirst.CollectPost(path);

               resolvingPost = new ResolvingPost();
               resolvingPost.CollectPost(path);

               //构造ReslovingPost对象 对文件中post数据包中的内容进行解析并存储
               correctInput = new UserInput(resolvingPostFirst,method);
               //System.out.println(path);

               //构建对象的时候发起的请求
               //现在的目标是能够和联合起来 想一想
               //设置post body 之后
           }else if(in.equals("n")||in.equals("N")){
               method = "GET";
                System.out.println("输入要注入的url");
                input = scanner.nextLine();
                //创建get型正确请求作为之后判断依据
               correctInput = new UserInput(input);
            }else {
               System.out.println("非法输入");
               return;
           }
            System.out.println("完成正确数据包的请求与保存");

            System.out.println("-----进行boolean注入---");

            ParamOperation paramOperation = new ParamOperation();
            Judge judge = new Judge();
            UserInput falesPayloadInput =null;
            UserInput turePayloadInput =null;

            for(int i =0;i<BooleanPayload.booleanFalesPayload.size();i++){
                //开关
                if(button==0){
                    System.out.println("跳过布尔型注入");
                    break;}

                //目前拿到了修改后的payload
                String falesPayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(BooleanPayload.booleanFalesPayload.get(i).toString()));
                String turePayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(BooleanPayload.booleanTurePayload.get(i).toString()));

                System.out.println(falesPayload);
                if (correctInput.getType().equals("POST")){
                 ResolvingPost resolvingPost1 = (ResolvingPost) resolvingPost.clone();
                 ResolvingPost resolvingPost2 = (ResolvingPost) resolvingPost.clone();

                paramOperation.addRequest(falesPayload,resolvingPost1);
                paramOperation.addRequest(turePayload,resolvingPost2);
                 falesPayloadInput =  new UserInput(resolvingPost1,method);
                 turePayloadInput =  new UserInput(resolvingPost2,method);}
                else
                {
                    String temp = correctInput.getFrontPart();

                    System.out.println();
                    falesPayloadInput =new UserInput(temp+falesPayload);
                     turePayloadInput = new UserInput(temp+turePayload);
                }

                System.out.println("Ture型Payload返回包大小为"+falesPayloadInput.getHtmlLength());

                System.out.println("False型Payload返回包大小为"+turePayloadInput.getHtmlLength());
                if(judge.BooleanJudge(falesPayloadInput,turePayloadInput,button)==1){
                    System.out.println("检测到sql注入，payload为"+falesPayloadInput.getUrl());
                    button = 1;
                    break;
                }
            }
            if(button!=1){
            System.out.println("布尔盲注未发现结果");

            System.out.println("开始延时注入");}{
                System.out.println("开始跑数据");
            }

            for(int i =0;i< SleepPayload.sleepPayload.size();i++){
                if(button==1){
                    System.out.println("跳过延时注入");
                    break;
                }
                UserInput sleepPayloadInput =null;
                String sleepPayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(SleepPayload.sleepPayload.get(i).toString()));
                if (correctInput.getType().equals("POST")){
                ResolvingPost resolvingPost1 = (ResolvingPost) resolvingPost.clone();

                paramOperation.addRequest(sleepPayload,resolvingPost1);
                sleepPayloadInput =  new UserInput(resolvingPost1,method);}
                else {
                    String temp = correctInput.getFrontPart();
                    System.out.println();
                    sleepPayloadInput =new UserInput(temp+sleepPayload);
                }

                System.out.println(sleepPayloadInput.getStartTimeStamp());
                System.out.println(sleepPayloadInput.getEndTimeStamp());
                int mark= judge.SleepJudge(sleepPayloadInput,button);

                if(mark==1){
                    System.out.println(mark);
                    System.out.println("检测到sql注入，url为"+ (sleepPayloadInput.getUrl()));
                    button = 1;
                    break;
                }
            }}
        }




