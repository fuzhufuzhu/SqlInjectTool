package com.sql.sqlinjecttool.inject;

import com.sql.sqlinjecttool.judge.Judge;
import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.payload.ErrorPayload;
import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.ResolvingPost;

import java.io.IOException;
import java.net.URLDecoder;


public class ErrorInject extends Inject {


    public ErrorInject(UserInput userInput) {
        super(userInput);
        System.out.println("进来了");
    }
    public ErrorInject(ResolvingPost resolvingPost){
        super(resolvingPost);
    }

    public String aInject() throws IOException {

        ParamOperation paramOperation = new ParamOperation();
        Judge judge = new Judge();
       // UserInput errorPayloadInput = null;

        if (isButtonStatus()){
            return null;
        }
        for(int i = 0; i< ErrorPayload.errorPayload.size(); i++){
            UserInput errorPayloadInput =null;
            if (1==1){
                ResolvingPost resolvingPost1 = (ResolvingPost) getResolvingPost().clone();
                if (isMarkStatus()){


                    String markValue = resolvingPost1.getMarkValue();

                    String errorPayload = paramOperation.markConnect(markValue,ErrorPayload.errorPayload.get(i).toString());
                    resolvingPost1.setHashMap(resolvingPost1.getMarkKey(), errorPayload);
                    errorPayloadInput = new UserInput(resolvingPost1,getMethod());
                }
                else {
//                    String temp = correctInput.getFrontPart();
//                    System.out.println();
//                    errorPayloadInput =new UserInput(temp+errorPayload);
                }
                int mark= judge.ErrorJudge(errorPayloadInput,1);

                if(mark==1){
                    System.out.println(mark);
                    System.out.println("检测到sql注入，url为"+ URLDecoder.decode(errorPayloadInput.getUrl()));
                   // button = 1;
                    break;
                }
            }
        }
        return "123";

    }
}
