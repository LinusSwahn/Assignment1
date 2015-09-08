package com.bytesizedgames;

/**
 * Created by pontuspohl on 08/09/15.
 */
public class CharEvaluator {

    //
    public static final int NUMERIC = 0;
    public static final int OPERATOR = 1;
    public static final int LEFTPARENTHESIS = 2;
    public static final int RIGHTPARENTHESIS = 3;
    public static final int ILLEGALTYPE = 4;


    public static int evaluateChar(char ch)
    {
        if( Character.isDigit(ch)) return NUMERIC;
        switch(ch){
            case '*':

            case '/':

            case '+':

            case '-': return OPERATOR;

            case '(': return LEFTPARENTHESIS;

            case ')': return RIGHTPARENTHESIS;

            default: return ILLEGALTYPE;
        }

    }



}
