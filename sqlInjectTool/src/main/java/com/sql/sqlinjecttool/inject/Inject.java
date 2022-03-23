package com.sql.sqlinjecttool.inject;

import com.sql.sqlinjecttool.pojo.UserInput;
import com.sql.sqlinjecttool.util.ResolvingPost;

public class Inject {
    private boolean checkStatus;
    private boolean markStatus;
    private UserInput userInput;
    private String method;
    private ResolvingPost resolvingPost;



   public Inject(UserInput userInput)
   {
        this.userInput = userInput;
        setMethod();
    }

    public Inject(ResolvingPost resolvingPost){

       setResolvingPost(resolvingPost);
    }



    public UserInput getUserInput(){
     return userInput;
    }

    public boolean getCheckStatus(){
        return  checkStatus;
    }


    public boolean isMarkStatus() {
        return markStatus;
    }

    public void setUserInput(UserInput userInput) {
        this.userInput = userInput;
    }
    public void setMethod() {
        this.method = userInput.getMethod();
    }

    public String getMethod() {
        return method;
    }

     ResolvingPost getResolvingPost() {
        return resolvingPost;
    }

    public void setResolvingPost(ResolvingPost resolvingPost) {
        this.resolvingPost = resolvingPost;
    }

}
