package com.sql.sqlinjecttool.paramOperation;

import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.ResolvingPost;
import sun.misc.BASE64Encoder;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//该类用于操作参数
public class ParamOperation {

    //提取用户输入的参数
    public List Collect(String param){
        List<String> ab=new ArrayList<>();
       String[]temp =param.split("&");
       for(String a:temp){
           ab.add(a);
       }
        return ab;
    }


    //提供参数位置，输入内容对象，要插入的payload
    public String Connect(int i, UserInput input,String payload){
        String temp="";
        String result="";


        for(int a=0;a<input.getParm().size();a++) {


            //在指定位置插入payload
            if (a==i) {
                temp=temp+"&"+input.getParm().get(a).toString()+payload;
                continue;
                //以下功能用于取代先前参数 替换为payload
//            int b = input.getParm().get(a).toString().indexOf("=");
//               String x= input.getParm().get(a).toString().substring(0,b+1)+ab;
//                temp =temp+"&"+x;
//                continue;
           }
            temp=temp+"&"+input.getParm().get(a);
        }
        //去除前面的&
        int c =temp.indexOf("&");
        result = temp.substring(0,c)+temp.substring(c+1);
        return result;
    }


    public ResolvingPost addRequest(String resultPayload, ResolvingPost resolvingPost){
        resolvingPost.setBody(resultPayload);
        return resolvingPost;
    }


}
