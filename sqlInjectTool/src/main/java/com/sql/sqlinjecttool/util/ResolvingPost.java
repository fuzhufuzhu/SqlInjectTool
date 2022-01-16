package com.sql.sqlinjecttool.util;

import com.sql.sqlinjecttool.Test;
import com.sql.sqlinjecttool.pojo.UserInput;
import org.springframework.boot.origin.Origin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class  ResolvingPost implements Cloneable{
    private  HashMap hashMap;
    private String url;
    private String body;
    private Boolean markStatus;
    private String markValue;
    private String markKey;

    public void CollectPost(String path) throws FileNotFoundException, MalformedURLException {
        //读取文件内容
        File file = new File(path);
        System.out.println("文件路径为"+path);
        BufferedReader reader = null;
        int mark =0;
        try{
            String tempchar;//保存每一行读取的结果
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            HashMap<String,String>hashMap = new HashMap<>();
           // StringBuffer stringBuffer = new StringBuffer();
            String temp="";
            int line=0;//读取的行数
            while ((tempchar=reader.readLine())!=null){
                //因为第一行格式特殊 需要特殊处理
                if(line==0){
                    String[] url=tempchar.split(" ");
                    temp=url[1];
                    line++;
                    continue;
                }

                //该处是想对body内容进行处理，连续读到两次空值，则说明已经进入body读取部分
                if(tempchar.equals("")){
                    mark+=1;
                    continue;
                }
                if(mark==1){
                    this.body = tempchar;
                    break;
                }mark=0;
                String[] header =tempchar.split(":",2);
                if(header[1].contains("$$")){
                    System.out.println("监测的用户标记的记号");
                    System.out.println("键为"+header[0]);
                    //header[1]=header[1].replace("$$","");
                    setMarkKey(header[0]);
                    setMarkValue(header[1]);
                    setMarkStatus(true);

                    System.out.println("值为"+header[1]);
                }
                hashMap.put(header[0],header[1]);
                }
            this.hashMap=hashMap;
            String aa="http://"+hashMap.get("Host")+temp;
            this.url=aa.replace(" ","");
        } catch (IOException e) {
            e.printStackTrace();
        }
            return ;

    }
    @Override
    public Object clone(){
        ResolvingPost resolvingPost = null;
        try {
            resolvingPost=(ResolvingPost)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return resolvingPost;}



    public HashMap getHashMap(){
        return hashMap;
    }
    public void setHashMap(String key,String value){
        this.hashMap.put(key,value);

    }
    public String getUrl(){
        return url;
    }

    public static void main(String[] args) throws FileNotFoundException, MalformedURLException
    {
        ResolvingPost test = new ResolvingPost();
        test.CollectPost("C:/Users/Test/Desktop/List.txt");
        //System.out.println(test.hashMap.get("Host"));



    }

    public String getBody() {
        return body;
    }
    public void setBody(String body){
         this.body=body;
    }

    public Boolean getMarkStatus() {
        return markStatus;
    }

    public void setMarkStatus(Boolean markStatus) {
        this.markStatus = markStatus;
    }

    public String getMarkValue() {
        return markValue;
    }

    public void setMarkValue(String markValue) {
        this.markValue = markValue;
    }

    public String getMarkKey() {
        return markKey;
    }

    public void setMarkKey(String markKey) {
        this.markKey = markKey;
    }
}
