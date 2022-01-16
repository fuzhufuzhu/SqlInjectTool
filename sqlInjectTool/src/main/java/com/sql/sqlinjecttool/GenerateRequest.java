//package com.sql.sqlinjecttool;
//
//import com.sql.sqlinjecttool.paramOperation.ParamOperation;
//import com.sql.sqlinjecttool.payload.ErrorPayload;
//import com.sql.sqlinjecttool.pojo.UserInput;
//import com.sql.sqlinjecttool.util.ResolvingPost;
//
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//
//public class GenerateRequest {
//    private boolean buttonStatus ;//
//    ParamOperation paramOperation = new ParamOperation();
//    UserInput correctInput;
//
//    public void ErrorInject(){
//        if (isButtonStatus()){
//            System.out.println("跳过报错注入");
//            return;
//        }
//        for(int i = 0; i< ErrorPayload.errorPayload.size(); i++){
//            UserInput errorPayloadInput =null;
//            String errorPayload = paramOperation.Connect(0,correctInput, URLEncoder.encode(ErrorPayload.errorPayload.get(i).toString()));
//            if (correctInput.getMethod().equals("POST")){
//                ResolvingPost resolvingPost1 = (ResolvingPost)resolvingPost.clone();
//                paramOperation.addRequest(errorPayload,resolvingPost1);
//                errorPayloadInput =  new UserInput(resolvingPost1,method);}
//            else {
//                String temp = correctInput.getFrontPart();
//                errorPayloadInput =new UserInput(temp+errorPayload);
//            }
//            int mark= judge.ErrorJudge(errorPayloadInput,button);
//
//            if(mark==1){
//                System.out.println(mark);
//                System.out.println("检测到sql注入，url为"+ URLDecoder.decode(errorPayloadInput.getUrl()));
//                button = 1;
//                break;
//            }
//        }
//
//
//
//
//
//
//
//    }
//
//
//
//    public boolean isButtonStatus(){
//        return buttonStatus;
//    }
//
//
//
//
//
//
//
//        }
//
