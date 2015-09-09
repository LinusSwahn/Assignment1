package com.bytesizedgames;

import java.util.Scanner;

/**
 * Created by pontuspohl on 07/09/15.
 */

// Class that holds the core functionality of this program
public class Dit948Calculator {

    private final int _NUMROWS = 9;


   public static void getInput(){



   }

   public Dit948Calculator(){}

    // Building the UI and checking if user wants to continue
    private boolean displayUi(){

        // algorithm for building the UI
        for(int i = 0; i < _NUMROWS ; i++){
            if(i % 2 == 0)
            {
                switch (i){
                    case 0: System.out.printf("+***************************************+\n");
                        break;
                    case 2: System.out.printf("*                                       *\n");
                        break;
                    case 4: System.out.printf("*     Welcome to DIT948 Calculator      *\n");
                        break;
                    case 6: System.out.printf("*                                       *\n");
                        break;
                    case 8: System.out.printf("+***************************************+\n");
                        break;
                }
            }
            else System.out.printf("\n");
        }


        // Query user for input
        System.out.printf("\n\nPress \"E\" to exit or any other button to continue\n");

        // Creating Scanner obj using Input stream
        Scanner scanner = new Scanner(System.in);

        // store user input in String variable next
        String next = scanner.next();

        // exit program if user inputs 'e' or 'E'
        if(next.equalsIgnoreCase("e")) return false;

        // for debugging only
        System.out.println("continuing");

        return true;

    }

    // method to determine the value of an operator
    private int p(char operator){

        // check if argument is valid and return correct value
        switch(operator) {
            case '+':
            case '-':
                return 0;

            case '*':
            case '/':
                return 1;
        }
        // if incorrect value is passed return -1
        return -1;

    }

    // TODO: logic ((3 + 5 ∗ 1)/8) ∗ 14 to RPN |||| and RPN -> result;


    private int computeArrayLength(String str){

        char buff = ' ';
        int count = 0;

        for(int i = 0;i < str.length(); i++)
        {
            int newChar = CharEvaluator.evaluateChar(str.charAt(i));
            int oldChar = CharEvaluator.evaluateChar(buff);
            if(newChar == CharEvaluator.NUMERIC && oldChar == CharEvaluator.NUMERIC ){
                buff = str.charAt(i);
                continue;
            }
            buff = str.charAt(i);
            count++;
        }

            return count;
    }


    private String infixToRPN(String infix){

        //Declare two strings, output and tmp.
        String output = "";
        String tmp = "";
        char bufferedChar = ' ';






        //Loops through all chars in the String supplied.
        for(int i = 0; i<infix.length(); i++) {



            int typeOfChar = CharEvaluator.evaluateChar(bufferedChar);

            //System.out.println(tmp);
            //Stores the value of the current char in the loop to c.
            char c = infix.charAt(i);
            bufferedChar = infix.charAt(i);

            //Evaluates c to see which kind it is of.
            int n = CharEvaluator.evaluateChar(c);

            //If the char is numeric push to the output string.
            if (n == CharEvaluator.NUMERIC)
            {
                output += c;
                continue;
            }

            //If the char is a left paranthesis, push it to tmp
            if (n == CharEvaluator.LEFTPARENTHESIS)
            {
                tmp = c+tmp;
                continue;
            }

            //If the char is a right paranthesis, loop through all chars in tmp until a left paranthesis is met.
            if (n == CharEvaluator.RIGHTPARENTHESIS)
            {
                for(int z = 0; z < tmp.length() ; z++)
                {
                    //If we encounter a left paranthesis. Pop it from tmp and break out from the loop.
                    if(tmp.charAt(z)=='(')
                    {
                        //as long as the length of tmp is >1 replace tmp with a new substring of tmp with the first element popped.
                        if(tmp.length() > 1) tmp = tmp.substring(1);
                        //Else just set the tmp string to "".
                        else tmp = "";

                       // System.out.println(tmp);
                        break;
                    }

                    //Transfer the char to output.
                    output+=tmp.charAt(z);

                    //as long as the length of tmp is >1 replace tmp with a new substring of tmp with the first element popped.
                    if(tmp.length() > 1) tmp = tmp.substring(1);
                    //Else just set the tmp string to "".
                    else tmp = "";


                    //Reset z so it will point at index 0 next itereation.
                    z = -1;
                }
            }

            //If the char is an operator. Loop through tmp until on of the three conditions is met. If met, push to tmp.
            if (n == CharEvaluator.OPERATOR)
            {
                for(int z = 0; z < tmp.length() ; z++)
                {
                    //Condition 1: We encounter a left paranthesis.
                    if(tmp.charAt(z)=='(')
                    {
                        tmp = c+tmp;
                        break;
                    }


                    //Extra security check to see if current character in tmp is an operator.
                    int x = CharEvaluator.evaluateChar(tmp.charAt(z));

                    if(x == CharEvaluator.OPERATOR)
                    {

                        //If p(t)>=p(c) we should transfer character t from tmp to output and pop it.
                        if(p(tmp.charAt(z)) >= p(c)) {

                            output+=tmp.charAt(z);

                            //as long as the length of tmp is >1 replace tmp with a new substring of tmp with the first element popped.
                            if(tmp.length() > 1) tmp = tmp.substring(1);
                            //Else just set the tmp string to "".
                            else tmp = "";

                            //Reset z so it will point at index 0 next iteration.
                            z = -1;

                        }
                        //Condition 2: p(t)<p(c)
                        else {
                            tmp = c+tmp;
                            break;
                        }

                    }
                }
                //Condition 3: tmp is empty.
                if(tmp.length() == 0) {
                    tmp = c+tmp;
                }
            }
        }

        //When we have looped through all the chars in the input. Transfer and pop the last elements from tmp to output.
        for(int z = 0; z < tmp.length() ; z++)
        {
            output+=tmp.charAt(z);
            //as long as the length of tmp is >1 replace tmp with a new substring of tmp with the first element popped.
            if(tmp.length() > 1) tmp = tmp.substring(1);
            //Else just set the tmp string to "".
            else tmp = "";

            //Reset z so it will point at index 0 next iteration.
            z = -1;
        }

        //Return the output, now correctly formatted to RPN.
        return output;
    }

    private double evalRPN(String rpn){

        String tmp2 = "";

        for(int i = 0; i< rpn.length(); i++)
        {
        int n = CharEvaluator.evaluateChar(rpn.charAt(i));
        if( n == CharEvaluator.NUMERIC ) tmp2 += rpn.charAt(i);
        else if(n == CharEvaluator.OPERATOR) ;


        }

    return 0;
    }







    /*
    for i=1 to m
if c_i is an operand: Transfer c_i to output.
if c_i is a left parentheses: Push c_i to tmp.
if c_i is a right parentheses: Pop elements from tmp and transfer
them to output until a left-parentheses
is met. Pop left-parentheses.
if c_i is an operator: Let the top tmp element be t. Pop and
transfer elements from tmp to output
until:
p(t) < p(c_i) or
t is a left-parentheses or
tmp is empty.
Push c_i to tmp.
Transfer the remaining elements in tmp to output.
     */



    public static void main(String args[]){



        Dit948Calculator calculator = new Dit948Calculator();
        int x = calculator.computeArrayLength("2*1*3*2*5");
        System.out.println(x);


        /*
        // creating a new Calculator object for user to interact with
        Dit948Calculator calculator = new Dit948Calculator();
        // displayUi returns false if user chooses not to continue
        if(!calculator.displayUi()) System.exit(0);
        // query user for arithmetic expression
        System.out.printf("\n\nPlease enter an arithmetic expression to evaluate:\n");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(input);
        String result = calculator.infixToRPN(input);
        System.out.println(result);

        Double newResult = calculator.evalRPN(result);
        */





    }
}
