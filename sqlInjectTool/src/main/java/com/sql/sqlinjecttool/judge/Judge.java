package com.sql.sqlinjecttool.judge;

import com.sql.sqlinjecttool.pojo.UserInput;

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
    public int ErrorJudge(UserInput userInput,int button){
        StringBuffer temp = userInput.getHtml();
        if(temp.toString().contains(">XPATH syntax error")){
            System.out.println("存在");
            return 1;
        }return 0;

    }
}

