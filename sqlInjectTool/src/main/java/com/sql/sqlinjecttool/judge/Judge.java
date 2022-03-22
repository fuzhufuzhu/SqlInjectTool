package com.sql.sqlinjecttool.judge;

import com.sql.sqlinjecttool.pojo.UserInput;

import java.util.ArrayList;
import java.util.Iterator;

public class Judge {
    public int SleepJudge(UserInput userInput,int button){
        System.out.println("button的值为"+button);
        if (button==1){
            return 1;}

        long aa=userInput.getEndTimeStamp()-userInput.getStartTimeStamp();
        System.out.println("此时的时间差为"+aa);
        if (aa>3000){
            return 1;
        }else
            return 0;
    }

    public int BooleanJudge(UserInput userInputTure,UserInput userInputFasle,int button){
        System.out.println();
        if (button==1){
            return 1;}
        if(userInputFasle.getHtmlLength()!=userInputTure.getHtmlLength()){
            System.out.println("发现注入");
            return 1;}else{
            return 0;
    }
    }
    public int ErrorJudge(UserInput userInput){
//        if (button==1){
//            return 1;
//        }
        StringBuffer temp = userInput.getHtml();
        System.out.println(temp.toString());
        if(temp.toString().contains("XPATH syntax error")){
            return 1;
        }return 0;

    }
    public int UnionJudge(UserInput userInput, ArrayList list){
        StringBuffer temp = userInput.getHtml();

        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            if (temp.toString().contains(iterator.next().toString())){
                System.out.println("检测到了联合注入输出点为"+iterator.next().toString());
                return 1;
            }
        }
        return 0;
    }





}

