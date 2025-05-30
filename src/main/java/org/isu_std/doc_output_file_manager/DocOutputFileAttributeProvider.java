package org.isu_std.doc_output_file_manager;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.DateTime;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.models.UserPersonal;

import java.lang.reflect.RecordComponent;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DocOutputFileAttributeProvider {
    private DocOutputFileAttributeProvider(){}

    protected static Set<String> getPlaceHolderSet(){
        Set<String> placeHolderSet = getUserPersonalPlaceHolders();
        placeHolderSet.add(getDatePlaceHolder());

        return placeHolderSet;
    }

    private static Set<String> getUserPersonalPlaceHolders(){
        var placeHolderSet = new LinkedHashSet<String>();

        // Main information for the output document file. E.g. name, age, sex, etc.
        RecordComponent[] recordComponents = UserPersonal.class.getRecordComponents();

        for(RecordComponent recordComponent : recordComponents){
            String placeHolder = DocxFileManager.convertToPlaceholder(
                    recordComponent.getName()
            );

            placeHolderSet.add(placeHolder);
        }

        return placeHolderSet;
    }

    private static String getDatePlaceHolder(){
        return DocxFileManager.convertToPlaceholder("date");
    }

    protected static Map<String, String> getValuesWithPlaceHolderMap(RequestDocumentContext requestDocumentContext){
         var valuesMap = new HashMap<String, String>();

         putUserValuesWithHolders(valuesMap, requestDocumentContext.userPersonal());
         putDateWithHolder(valuesMap);

         return valuesMap;
    }

    private static void putUserValuesWithHolders(Map<String, String> valuesMap, UserPersonal userPersonal){
        Object[] values = userPersonal.getValues();
        Set<String> placeHolder = getUserPersonalPlaceHolders();

        var count = new AtomicInteger();
        placeHolder.forEach((holder) -> {
            String strValue = values[count.getAndIncrement()].toString();

            valuesMap.put(holder, strValue);
        });
    }

    private static void putDateWithHolder(Map<String, String> valuesMap){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM, dd, yyyy");
        String date = DateTime.localDateTimeStr(dateTimeFormatter);

        valuesMap.put(getDatePlaceHolder(), date);
    }
}
