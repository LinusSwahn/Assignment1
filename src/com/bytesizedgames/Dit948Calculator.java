package com.bytesizedgames;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by Pontus Pohl and Linus Eiderström Swahn 07/09/15.
 */


interface Calculator {

    String[] infixToRPN(String infix);
    boolean validateExpression(String expression);
    String getUserInput();
    double evalRPN(String rpn[]);
    boolean displayUi();

}

// Class that holds the core functionality of this program
public class Dit948Calculator implements Calculator {

    private final int _NUMROWS = 9;
    private Scanner keyboard = new Scanner(System.in);




    public Dit948Calculator(){}

    // Building the UI and checking if user wants to continue
    public boolean displayUi(){

        int titleRow = (int)(0.5*(_NUMROWS));


        // algorithm for building the UI
        for(int i = 0; i < _NUMROWS ; i++) {
            if (i % 2 != 0 && i != titleRow && i != _NUMROWS - 1) {
                System.out.printf("*                                       *\n");
            }
            else if (i == 0) System.out.printf("+***************************************+\n");
            else if (i == titleRow) System.out.printf("*     Welcome to DIT948 Calculator      *\n");
            else if (i == (_NUMROWS - 1)) System.out.printf("+***************************************+\n");
        }




        // Query user for input
        System.out.printf("\n\nPress \"E\" to exit or any other button to continue\n");

        // Creating Scanner obj using Input stream
        Scanner scanner = new Scanner(System.in);

        // store user input in String variable next
        String next = scanner.next();

        // exit program if user inputs 'e' or 'E'
        if(next.equalsIgnoreCase("e")) return false;



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

    private String[] toArray(String str){

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

        arrayToReturn = clearArray(arrayToReturn);



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

                buff = str.charAt(i);
                continue;
            }
            else if(newChar == CharEvaluator.NUMERIC){
                arrayCounter++;

                arrayToReturn[arrayCounter] += str.charAt(i);


                buff = str.charAt(i);
                continue;
            }
            else if( newChar != CharEvaluator.NUMERIC && i == 0 ){
                arrayToReturn[arrayCounter] += str.charAt(i);

                buff=str.charAt(i);
            }
            else{
                arrayCounter++;
                arrayToReturn[arrayCounter] += str.charAt(i);

                buff=str.charAt(i);
            }


        }

        return arrayToReturn;
    }

    public String[] infixToRPN(String infix){

        //Declare two strings, output and tmp.
        String output[] = new String[0];
        String tmp = "";
        String[] outputArray = toArray(infix);
        //char bufferedChar = ' ';

        //Loops through all chars in the String supplied.
        for(int i = 0; i<outputArray.length; i++) {



            String tmpString = outputArray[i];



            //Stores the value of the current char in the loop to c.
            char c = tmpString.charAt(0);

            //Evaluates c to see which kind it is of.
            int n = CharEvaluator.evaluateChar(c);

            //If the char is numeric push to the output string.
            if (n == CharEvaluator.NUMERIC)
            {
                String tmpArray[] = new String[output.length+1];
                tmpArray = clearArray(tmpArray);
                for(int j = 0; j<output.length; j++)
                {
                    tmpArray[j] = output[j];
                }
                tmpArray[tmpArray.length-1] = tmpString;
                output = tmpArray;
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
                    String tmpArray[] = new String[output.length+1];
                    tmpArray = clearArray(tmpArray);
                    for(int j = 0; j<output.length; j++)
                    {
                        tmpArray[j] = output[j];
                    }
                    tmpArray[tmpArray.length-1] += tmp.charAt(z);
                    output = tmpArray;
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

                            //Transfer the char to output.
                            String tmpArray[] = new String[output.length+1];
                            tmpArray = clearArray(tmpArray);
                            for(int j = 0; j<output.length; j++)
                            {
                                tmpArray[j] = output[j];
                            }
                            tmpArray[tmpArray.length-1] += tmp.charAt(z);
                            output = tmpArray;

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
            //Transfer the char to output.
            String tmpArray[] = new String[output.length+1];
            tmpArray = clearArray(tmpArray);
            for(int j = 0; j<output.length; j++)
            {
                tmpArray[j] = output[j];
            }
            tmpArray[tmpArray.length-1] += tmp.charAt(z);
            output = tmpArray;
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

    public double evalRPN(String rpn[]){

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

                //Transfer the other elements of the stack to the new temporary array.
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

    private String[] clearArray(String array[]){



        for(int i = 0; i < array.length; i++ ){
            array[i] ="";
        }
        return array;
    }

    public boolean validateExpression(String expression)
    {

        try{
            if(expression.length() == 0) throw new Exception("\nYou have to Supply at least one char" +
                    "(Preferably more than one though).\n\nif you did enter" +
                    " > 0 chars, then we have an IOException... \n\nTry again or press e to exit!\n");

            else if(expression.length() == 1 && expression.equalsIgnoreCase("e")) System.exit(0);

            //Variables needed to check for illegal input strings.
            char previousChar = ' ';
            int leftParenthesis = 0;
            int rightParenthesis = 0;

            //loops through all chars in the input string and checks if they are illegal characters or has invalid formatting.
            for(int i = 0; i<expression.length(); i++)
            {

                int n = CharEvaluator.evaluateChar(expression.charAt(i));

                //Starts counting the brackets. If they don't match, give an exception at the end of the loop.
                if(n == CharEvaluator.LEFTPARENTHESIS) leftParenthesis++;
                if(n == CharEvaluator.RIGHTPARENTHESIS)
                {
                    rightParenthesis++;

                    if(leftParenthesis==0)
                    {
                        throw new Exception("\nYou entered an invalid Arithmetic Expression.\n" +
                                "A closing paranthesis can't come before an opening one!\n" +
                                "Please try again or press e to exit!");
                    }

                    if(rightParenthesis>leftParenthesis)
                    {
                        throw new Exception("\nYou entered an invalid Arithmetic Expression.\n" +
                                "You can't have more right parenthesis than left ones!\n" +
                                "Please try again or press e to exit!");
                    }
                }

                if(n == CharEvaluator.ILLEGALTYPE )
                {
                    throw new Exception("\nYou entered an invalid Arithmetic Expression," +
                            " Please try again or press e to exit!\n");
                }

                if(i>0)
                {
                    int m = CharEvaluator.evaluateChar(previousChar);

                    if(n == CharEvaluator.OPERATOR && (m == CharEvaluator.OPERATOR||m==CharEvaluator.LEFTPARENTHESIS))
                    {
                        throw new Exception("\nYou entered an invalid Arithmetic Expression.\n" +
                        "Operators can't follow another operator or a left parenthesis!\n" +
                        "Please try again or press e to exit!");
                    }

                    if(n == CharEvaluator.NUMERIC&&m==CharEvaluator.RIGHTPARENTHESIS)
                    {
                        throw new Exception("\nYou entered an invalid Arithmetic Expression.\n" +
                                "Operands can't follow directly after a closing paranthesis!\n" +
                                "Please try again or press e to exit!");
                    }
                }
                else
                {
                    if(n==CharEvaluator.OPERATOR)
                    {
                        throw new Exception("\nYou entered an invalid Arithmetic Expression.\n" +
                                "Can't start statement with an operator!\n" +
                                "Please try again or press e to exit!");
                    }

                }

                if(i==expression.length())
                {
                    if(n!=CharEvaluator.NUMERIC||n!=CharEvaluator.RIGHTPARENTHESIS)
                    {
                        System.out.println(i);
                        throw new Exception("\nYou entered an invalid Arithmetic Expression.\n" +
                                "You have to end statement with a closing paranthesis or and operand!\n" +
                                "Please try again or press e to exit!");
                    }
                }

                previousChar = expression.charAt(i);
            }

            if(leftParenthesis!=rightParenthesis)
            {
                throw new Exception("\nYou entered an invalid Arithmetic Expression.\n" +
                        "You have to have an equal amount of opening and closing parenthesis!\n" +
                        "Please try again or press e to exit!");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());


            return false;
        }
        return true;
    }

    public String getUserInput(){

        try{
            System.out.printf("\nPlease enter an arithmetic expression to evaluate:\n\n");

            String input = keyboard.nextLine();

            return input;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String args[]){
        // self-explanatory variables...
        boolean hasValidInput;
        boolean keepGoing = true;
        String input = "";
        Scanner keyboard = new Scanner(System.in);





            // create a new Calculator object for user to interact with
            // using Interface keeps private fields/methods private
            Calculator calculator = new Dit948Calculator();

            // displayUi returns false if user chooses not to continue
            if (!calculator.displayUi()) System.exit(0);


        while(keepGoing) {

            // we assume user won't perform more than one conversion
            keepGoing = false;

            // query user for arithmetic expression until a correct expression is supplied.
            do {
                input = calculator.getUserInput();
                hasValidInput = calculator.validateExpression(input);
            } while (!hasValidInput);

            // perform the conversion from String input to an array that holds the expression in RPN
            String array[] = calculator.infixToRPN(input);

            System.out.printf("\n");
            System.out.print("RPN : ");
            // iterate through the array and print out the expression in RPN format
            for (int i = 0; i < array.length; i++) {
                System.out.printf("%s", array[i]);
            }
            // finally create a new line after RPN is printed
            System.out.printf("\n");

            // calculate the result of RPN expression
            Double result = calculator.evalRPN(array);

            // print out the result
            System.out.println("\nResult is : " + result+"\n");

            System.out.println("perform another conversion: y or n ?  \n");
            if(keyboard.next().equalsIgnoreCase("y")) keepGoing = true;

        }

    }
}
