package com.sql.sqlinjecttool.pojo;

import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.util.HttpSend;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserInput {
    private String url ;
    private int htmlLength;
    private  StringBuffer html;
    private List parm;
    private int parmNum;


    public UserInput(String input) throws IOException {
        URL url = new URL(input);
        this.setUrl(input);
        String param=url.getQuery();
        //获取url中的参数 以list save
        ParamOperation paramOperation = new ParamOperation();
        List<List>paramList =paramOperation.Collect(param);
        this.setParm(paramList);
        this.setParmNum(paramList.size());

        //存储返回包
        HttpURLConnection connection=HttpSend.sendHttpRequest(input,"GET");

        //获取响应包并保存
        InputStream inputStream=(InputStream)connection.getContent();
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
}
