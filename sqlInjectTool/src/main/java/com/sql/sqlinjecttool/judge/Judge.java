package com.sql.sqlinjecttool.judge;

import com.sql.sqlinjecttool.pojo.UserInput;

public class Judge {
    public int SleepJudge(UserInput userInput,int button){
        System.out.println("button的值为"+button);
        if (button==1){
            return 1;}

        long aa=userInput.getEndTimeStamp()-userInput.getStartTimeStamp();
        System.out.println("此时的时间差为"+aa);
        if (aa>10){
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
}
