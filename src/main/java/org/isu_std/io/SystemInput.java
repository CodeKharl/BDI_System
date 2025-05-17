package org.isu_std.io;

import org.isu_std.io.collections_enum.InputMessageCollection;

import java.util.Scanner;

public class SystemInput {
    private static Scanner scan;

    private SystemInput(){}

    public static void setScan(Scanner scan){
        if(SystemInput.scan == null){
            SystemInput.scan = scan;
        }
    }

    public static int getIntInput(String prompt){
        while(true){
            Util.printQuestion(prompt);

            try{
                return Integer.parseInt(scan.nextLine().trim());
            }catch (NumberFormatException e){
                Util.printException(
                        InputMessageCollection.INPUT_NUM_ONLY.getFormattedMessage("input")
                );
            }
        }

    }

    public static String getStringInput(String prompt){
        while(true){
            Util.printQuestion(prompt);

            String input = scan.nextLine().trim();
            if(!input.isBlank()){
                return input;
            }

            Util.printException("Need to Fill-up!");
        }
    }

    public static char getCharInput(String prompt){
        return getStringInput(prompt).charAt(0); // Return the first letter of the input.
    }

    public static int getIntChoice(String prompt, int choicesLength){
        while(true){
            int choice = getIntInput(prompt);

            if(choice > 0 && choice <= choicesLength){
                return choice;
            }

            Util.printException(
                    InputMessageCollection.INPUT_INVALID.getFormattedMessage("choice")
            );
        }
    }

    public static boolean isPerformConfirmed(String message, char choice1, char choice2){
        Util.printChoice(
                "%s : (TYPE -> '%c' == Continue, '%c' == Cancel)"
                        .formatted(message, choice1, choice2)
        );

        while (true) {
            char choice = getCharInput(Symbols.INPUT.getType());
            if(choice == choice1) {
                return true;
            } else if (choice == choice2) {
                return false;
            }

            Util.printException(
                    InputMessageCollection.INPUT_INVALID.getFormattedMessage("input")
            );
        }
    }

    public static void scanClose(){
        scan.close();
    }
}
