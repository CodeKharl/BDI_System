package org.isu_std.user.userDocumentRequest.reference_generator;

import org.isu_std.io.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ReferenceGenerator {
    public static String createReferenceId(String prefix, String dateTimeFormat, String randoms, int randomLength){
        String strTime = new SimpleDateFormat(dateTimeFormat).format(new Date());

        StringBuilder randomPart = new StringBuilder();
        Random random = new Random();

        for(int i = 0; i < randomLength; i++){
            int index = random.nextInt(randoms.length());
            randomPart.append(randoms.charAt(index));
        }

        return "%s-%s-%s".formatted(prefix, strTime, randomPart.toString());
    }
}
