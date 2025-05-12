package org.isu_std.doc_output_file_provider;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.io.Util;

import java.util.Map;
import java.util.Set;

public final class DocOutFileContext {
    private static Set<String> placeHolderSet;

    public static Set<String> getPlaceHolderSet(){
        if(placeHolderSet == null){
            placeHolderSet = DocOutputFileAttributeProvider.getPlaceHolderSet();
        }

        return placeHolderSet;
    }

    public static Map<String, String> getValuesWithPlaceHolderMap(RequestDocumentContext requestDocumentContext){
        return DocOutputFileAttributeProvider.getValuesWithPlaceHolderMap(requestDocumentContext);
    }

    public static void printAvailableTextPlaceHolders(Set<String> placeHolderSet){
        Util.printSubSectionTitle("Available Document Text Place-Holders");
        Util.printMessage("This place holders can only be use on paragraphs!");
        placeHolderSet.forEach(Util::printChoice);
    }
}
