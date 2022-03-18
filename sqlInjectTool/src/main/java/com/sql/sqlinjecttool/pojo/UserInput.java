package com.sql.sqlinjecttool.pojo;

import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.util.HttpSend;
import com.sql.sqlinjecttool.util.ResolvingPost;
import javafx.geometry.Pos;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UserInput implements Cloneable{
    private String url ;
    private int htmlLength;
    private  StringBuffer html;
    private List parm;
    private int parmNum;
    private long startTimeStamp;
    private long endTimeStamp;

    private String frontPart;
    private String method;

    //GET型对象
    public UserInput(String input) throws IOException {
        URL url = new URL(input);
        this.setUrl(input);
        this.setMethod("GET");

        this.frontPart = url.getProtocol()+"://"+url.getHost()+url.getPath()+"?";
        String param=url.getQuery();
        //获取url中的参数 以list save
        ParamOperation paramOperation = new ParamOperation();
        List<List>paramList =paramOperation.Collect(param);
        this.setParm(paramList);
        this.setParmNum(paramList.size());
        System.out.println("尝试请求"+ URLDecoder.decode(input));

        //存储返回包
        this.setStartTimeStamp(System.currentTimeMillis());
        HttpURLConnection connection=HttpSend.sendHttpRequest(input,"GET");

        //获取响应包并保存
        InputStream inputStream=(InputStream)connection.getContent();
        this.setEndTimeStamp(System.currentTimeMillis());

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        StringBuffer stringBuffer =new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String temp = null;
        while ((temp=bufferedReader.readLine())!=null){
            stringBuffer.append(temp);
        }
        inputStream.close();
        inputStreamReader.close();
        bufferedReader.close();
        this.setHtml(stringBuffer);
        this.setHtmlLength(connection.getContentLength());
    }

        //在该出设置一个读取好的数据包 post型的构造方法就是读取已经解析过的数据包
        //
    //post型对象
    public UserInput (ResolvingPost resolvingPost,String method) throws IOException {

       HashMap postHashMap=resolvingPost.getHashMap();
        HttpURLConnection connection = HttpSend.sendHttpRequest(resolvingPost.getUrl(),"POST");
        connection.setDoOutput(true);
        Iterator iterator = postHashMap.keySet().iterator();
        while (iterator.hasNext()){
            Object key = iterator.next();
            Object value = postHashMap.get(key);
            connection.setRequestProperty((String) key,(String) value);
        }
        connection.connect();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
        writer.write(resolvingPost.getBody());
        writer.flush();
       // System.out.println("尝试payload:"+URLDecoder.decode(resolvingPost.getBody()));
        this.setStartTimeStamp(System.currentTimeMillis());

        InputStream inputStream=connection.getInputStream();
        this.setEndTimeStamp(System.currentTimeMillis());

        //获取body中的数组
        ParamOperation paramOperation = new ParamOperation();
        List<List>paramList =paramOperation.Collect(resolvingPost.getBody());
        this.setParm(paramList);
        this.setParmNum(paramList.size());

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
        StringBuffer stringBuffer =new StringBuffer();

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println("buffer"+bufferedReader.readLine());
        String temp = null;
        while ((temp=bufferedReader.readLine())!=null){
            stringBuffer.append(temp);
        }
        inputStream.close();
        inputStreamReader.close();

     //   System.out.println(this.getParm());

        bufferedReader.close();
        this.setHtml(stringBuffer);
       // System.out.println("页面内容为"+this.html.toString());

        this.setHtmlLength(connection.getContentLength());
        System.out.println(getHtmlLength());
        this.method="POST";
    }
    @Override
    public Object clone(){
        UserInput userInput = null;
        try {
            userInput=(UserInput)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return userInput;

    }

    public static void main(String[] args) throws IOException {
//        UserInput userInput = new UserInput();
//        System.out.println(userInput.getHtmlLength());

    }
    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public int getHtmlLength() {
        return htmlLength;
    }

    private void setHtmlLength(int htmlLength) {
        this.htmlLength = htmlLength;
    }

    public StringBuffer getHtml() {
        return html;
    }

    private void setHtml(StringBuffer html) {
        this.html = html;
    }


    public List getParm() {
        return parm;
    }

    private void setParm(List parm) {
        this.parm = parm;
    }

    public int getParmNum() {
        return parmNum;
    }

    private void setParmNum(int parmNum) {
        this.parmNum = parmNum;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }


    public String getFrontPart() {
        return frontPart;
    }

    public void setFrontPart(String frontPart) {
        this.frontPart = frontPart;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
