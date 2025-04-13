package org.isu_std;

import org.isu_std.io.SystemInput;
import org.isu_std.io.Symbols;
import org.isu_std.io.Util;

import java.util.Scanner;

public class TermsAndConditions {
    private TermsAndConditions(){}

    private static void show() {

    }

    public static boolean isAccept(Scanner scan){
        show();

        do{
            System.out.printf("\n%sDo you agree to the following conditions? (Yes or No)\n", Symbols.QUESTION.getType());

            String decision = SystemInput.getStringInput(Symbols.INPUT.getType());

            if(decision.equalsIgnoreCase("yes")) return true;
            else if(decision.equalsIgnoreCase("no")) return false;
            else Util.printInputInvalidMessage("Input");
        }while(true);
    }
}
