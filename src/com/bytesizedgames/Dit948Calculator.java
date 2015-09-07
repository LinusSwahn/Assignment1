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

        System.out.println("Press \"E\" to exit or any other button to continue");
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        if(next.equalsIgnoreCase("e")) System.exit(0);
        System.out.println("continuing");
        return true;
    }
    public static void main(String args[]){

        Dit948Calculator calculator = new Dit948Calculator();

    }
}
