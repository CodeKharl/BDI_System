package org.isu_std.admin.admin_doc_manage.adminDoc_func.others;

import org.isu_std.io.*;

import java.util.Optional;

// Functional Paradigm for this class. Structure are not necessary.

public class RequirementHandler{
    private final static String CONFIRM_VALUE = "SET";
    private final static String CANCEL_VALUE = "ESC";
    private final static int MIN_REQUIREMENT_LENGTH = 3;

    public static Optional<String> getRequirements(){
        Util.printSubSectionTitle("Set Document Requirements :");
        Util.printMessage(
                "Input Guide : ('Enter' key == Next req.) : (%s == Done), (%s == Cancel)".formatted(
                        CONFIRM_VALUE, CANCEL_VALUE
                )
        );

        return getRequirementsBuilder(new StringBuilder());
    }

    private static Optional<String> getRequirementsBuilder(StringBuilder requirementBuilder){
        while(true){
            String input = SystemInput.getStringInput(Symbols.INPUT.getType());

            if(input.equals(CANCEL_VALUE)){
                return Optional.empty();
            }

            if(!input.equals(CONFIRM_VALUE)){
                // Append the input to the requirementBuilder
                appendingStringBuilder(input, requirementBuilder);
                continue;
            }

            // Condition that check the requirement builder contains value.
            if(requirementBuilder.toString().isBlank()){
                Util.printMessage("You are not allowed to proceed! Please enter a document requirement.");
                continue;
            }

            return Optional.of(requirementBuilder.toString());
        }
    }

    private static void appendingStringBuilder(String input, StringBuilder stringBuilder){
        if(Validation.isInputLengthAccepted(MIN_REQUIREMENT_LENGTH, "requirement", input)){
            stringBuilder.append(input).append(" : ");
        }
    }
}
