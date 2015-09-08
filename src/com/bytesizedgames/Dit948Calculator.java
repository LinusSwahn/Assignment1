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

    private String infixToRPN(String infix){

        //Declare two strings, output and tmp.
        String output = "";
        String tmp = "";


        //Loops through all chars in the String supplied.
        for(int i = 0; i<infix.length(); i++) {

            //Stores the value of the current char in the loop to c.
            char c = infix.charAt(i);


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
                tmp += c;
                continue;
            }

            if (n == CharEvaluator.RIGHTPARENTHESIS)
            {
                for(int z = tmp.length()-1; z>=0; z--)
                {
                    if(tmp.charAt(z)=='(')
                    {
                        tmp = tmp.substring(0, z);
                        break;
                    }

                    output+=tmp.charAt(z);

                    tmp = tmp.substring(0, z);
                }

            }

            if (n == CharEvaluator.OPERATOR)
            {
                if(tmp.length() == 0) {
                    tmp += c;
                    continue;
                }

                for(int z = tmp.length()-1; z >= 0; z--)
                {
                    if(tmp.charAt(z)=='(')
                    {
                        tmp += c;
                        break;
                    }


                    int x = CharEvaluator.evaluateChar(tmp.charAt(z));

                    if(x == CharEvaluator.OPERATOR) {

                        if(p(tmp.charAt(z)) >= p(c)) {

                            output+=tmp.charAt(z);

                            tmp = tmp.substring(0, z);
                        }
                        else {
                            tmp += c;
                            break;
                        }

                    }


                    output+=tmp.charAt(z);

                    tmp = tmp.substring(0, z);
                }
            }


        }

        for(int z = tmp.length()-1; z>=0; z--)
        {


            output+=tmp.charAt(z);

            tmp = tmp.substring(0, z);
        }



        return output;
    }

    private double evalRPN(String rpn){

        String tmp2 = "";

        for(int i = 0; i< rpn.length(); i++)
        {
        int n = CharEvaluator.evaluateChar(rpn.charAt(i));
        if( n == CharEvaluator.NUMERIC ) tmp2 += rpn.charAt(i);
        else if(n == CharEvaluator.OPERATOR)


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






    }
}
