package com.bytesizedgames;

import java.util.Scanner;

/**
 * Created by pontuspohl on 07/09/15.
 */
public class Dit948Calculator {

   public Dit948Calculator(){

       displayUi();


   }

    private boolean displayUi(){

        for(int i = 0; i < 9; i++){
            if(i % 2 == 0)

            {
                switch (i){
                    case 0: System.out.printf("+***************************************+\n");
                        break;
                    case 2 :
                        System.out.printf("*                                       *\n");
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

        System.out.printf("\n\nPress \"E\" to exit or any other button to continue");
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        if(next.equalsIgnoreCase("e")) System.exit(0);
        System.out.println("continuing");
        return true;
    }

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
        System.out.println(calculator.p('*'));
    }
}
