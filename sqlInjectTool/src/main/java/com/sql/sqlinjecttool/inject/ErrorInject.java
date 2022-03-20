package com.sql.sqlinjecttool.inject;

import com.sql.sqlinjecttool.judge.Judge;
import com.sql.sqlinjecttool.paramOperation.ParamOperation;
import com.sql.sqlinjecttool.payload.ErrorPayload;
import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.ResolvingPost;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import static com.sql.sqlinjecttool.payload.ErrorPayload.errorPayload;


public class ErrorInject extends Inject {

static {
    System.out.println("开始报错注入");

}

    public ErrorInject(UserInput userInput) {
        super(userInput);
    }

    public ErrorInject(ResolvingPost resolvingPost) {
        super(resolvingPost);
          }

    public String aInject(ResolvingPost resolvingPost,UserInput correctInput) throws IOException {

        ParamOperation paramOperation = new ParamOperation();
        Judge judge = new Judge();

        for (int i = 0; i < errorPayload.size(); i++) {
            UserInput errorPayloadInput = null;
            String errorPayload = paramOperation.Connect(1, correctInput, URLEncoder.encode(ErrorPayload.errorPayload.get(i).toString()));

            if (correctInput.getMethod().equals("POST")) {
                ResolvingPost resolvingPost1 = (ResolvingPost) resolvingPost.clone();

                if (resolvingPost.getMarkStatus()) {
                    String markValue = resolvingPost1.getMarkValue();
                    errorPayload = paramOperation.markConnect(markValue, ErrorPayload.errorPayload.get(i).toString());
                    resolvingPost1.setHashMap(resolvingPost1.getMarkKey(), errorPayload);
                    errorPayloadInput = new UserInput(resolvingPost1);
                } else {
                    paramOperation.addRequest(errorPayload, resolvingPost1);
                    errorPayloadInput = new UserInput(resolvingPost1);

//                    String temp = correctInput.getFrontPart();
//                    System.out.println();
//                    errorPayloadInput =new UserInput(temp+errorPayload);
                }
            } else {

                String temp = correctInput.getFrontPart();
                errorPayloadInput = new UserInput(temp + errorPayload);
            }
            int mark = judge.ErrorJudge(errorPayloadInput);

            if (mark == 1) {
                System.out.println(mark);
                System.out.println("检测到sql注入，url为" + URLDecoder.decode(errorPayloadInput.getUrl()));
                break;
            }
        }

    return "1";}
}
