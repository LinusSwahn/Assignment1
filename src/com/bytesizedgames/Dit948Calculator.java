package com.bytesizedgames;

import java.util.Scanner;

/**
 * Created by Pontus Pohl and Linus Eiderström Swahnon 07/09/15.
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

    private String[] computeArrayLength(String str){

        char buff = ' ';
        int count = 0;
        int newChar = -1;
        int oldChar = -1;


        for(int i = 0;i < str.length(); i++)
        {
            newChar = CharEvaluator.evaluateChar(str.charAt(i));
            oldChar = CharEvaluator.evaluateChar(buff);
            if(newChar == CharEvaluator.NUMERIC && oldChar == CharEvaluator.NUMERIC ){
                buff = str.charAt(i);
                continue;
            }
            buff = str.charAt(i);
            count++;
        }

        buff = ' ';

        String arrayToReturn[] = new String[count];

        for(int i = 0; i < arrayToReturn.length; i++ ){
            arrayToReturn[i] ="";
        }


        int arrayCounter= 0;
        newChar = -1;
        oldChar = -1;
        buff = ' ';



        for(int i = 0; i < str.length(); i++)
        {
            newChar = CharEvaluator.evaluateChar(str.charAt(i));
            oldChar = CharEvaluator.evaluateChar(buff);
            if(newChar == CharEvaluator.NUMERIC && oldChar == CharEvaluator.NUMERIC
                    || newChar == CharEvaluator.NUMERIC && i == 0)
            {

                arrayToReturn[arrayCounter] += str.charAt(i);
                System.out.println("value of arrayToReturn index: "+arrayCounter+" is: "+arrayToReturn[arrayCounter]);
                buff = str.charAt(i);
                continue;
            }
            else if(newChar == CharEvaluator.NUMERIC){
                arrayCounter++;

                arrayToReturn[arrayCounter] += str.charAt(i);
                System.out.println("value of arrayToReturn index: "+arrayCounter+" is: "+arrayToReturn[arrayCounter]);

                buff = str.charAt(i);
                continue;
            }
            else if( newChar != CharEvaluator.NUMERIC && i == 0 ){
                arrayToReturn[arrayCounter] += str.charAt(i);
                System.out.println("value of arrayToReturn index: "+arrayCounter+" is: "+arrayToReturn[arrayCounter]);
                buff=str.charAt(i);
            }
            else{
                arrayCounter++;
                arrayToReturn[arrayCounter] += str.charAt(i);
                System.out.println("value of arrayToReturn index: "+arrayCounter+" is: "+arrayToReturn[arrayCounter]);
                buff=str.charAt(i);
            }


        }

        return arrayToReturn;
    }


    private String infixToRPN(String infix){

        //Declare two strings, output and tmp.
        String output = "";
        String tmp = "";
        String[] outputArray = computeArrayLength(infix);
        char bufferedChar = ' ';

        //Loops through all chars in the String supplied.
        for(int i = 0; i<outputArray.length; i++) {



            int typeOfChar = CharEvaluator.evaluateChar(bufferedChar);


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


            bufferedChar = infix.charAt(i);

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

    private double evalRPN(String rpn[]){

        //Declare a temporary array to store values.
        String tmp2[] = new String[0];

        //Loop through all elements in rpn[].
        for(int i = 0; i< rpn.length; i++)
        {

            //Evaluate the element we are currently at.
            int n = CharEvaluator.evaluateChar(rpn[i].charAt(0));

            //If it's numerical Push to tmp2.
            if( n == CharEvaluator.NUMERIC )
            {
                //Create a tmpArray to store of values and the push the new value to the top.
                String tmpArray[] = new String[tmp2.length+1];

                //Transfers the values from tmp2.
                for(int j = 1; j<tmpArray.length; j++)
                {
                    tmpArray[j] = tmp2[j-1];
                }

                //Push the new value to the top of the stack.
                tmpArray[0] = rpn[i];

                //Replace tmp2 with the updated array.
                tmp2 = tmpArray;
            }

            //If we are at an operator, apply the operator to the top two elements in tmp2.
            else if(n == CharEvaluator.OPERATOR)
            {
                //Store the operator as a char.
                char c = rpn[i].charAt(0);

                //Create a tmpArray with length one lower than tmp2 since we will pop it.
                String tmpArray[] = new String[tmp2.length-1];

                //Temporary double variable to store the calculated value.
                double tmpDouble = 0;

                //Check which operator it is. Apply the correct calculation depending on operator.
                switch(c) {
                    case '+':

                        tmpDouble = Double.parseDouble(tmp2[1]) + Double.parseDouble(tmp2[0]);

                        break;

                    case '-':

                        tmpDouble = Double.parseDouble(tmp2[1]) - Double.parseDouble(tmp2[0]);

                        break;
                    case '*':

                        tmpDouble = Double.parseDouble(tmp2[1]) * Double.parseDouble(tmp2[0]);

                        break;
                    case '/':

                        tmpDouble = Double.parseDouble(tmp2[1]) / Double.parseDouble(tmp2[0]);

                        break;

                }

                //Push the new value into the stack.
                tmpArray[0] = "" + tmpDouble;

                //Transfer the other elemtns of the stack to the new temporary array.
                for(int j = 2; j<tmp2.length; j++)
                {
                    tmpArray[j-1] = tmp2[j];
                }

                //Replace tmp2 with the new updated array.
                tmp2 = tmpArray;
            }
        }

        //Return the calculated value.
        return Double.parseDouble(tmp2[0]);
    }

    public static void main(String args[]){


        String testArray[] = new String[] {"5", "9", "*", "2", "62", "/", "89", "90", "8", "/", "+", "*", "+"};

        Dit948Calculator calculator = new Dit948Calculator();
        String array[] = calculator.computeArrayLength("((3+5*1*22)/82)*14+(2+23)");
        for(int i=0;i<array.length;i++){
            System.out.printf("index %d holds String: %s \n",i,array[i]);
        }




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
