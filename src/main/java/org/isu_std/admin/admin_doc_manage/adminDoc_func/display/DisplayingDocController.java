package org.isu_std.admin.admin_doc_manage.adminDoc_func.display;

import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Document;
import org.isu_std.io.Util;

import java.util.Map;

public final class DisplayingDocController {
    private final DisplayingDocService displayingDocService;
    private final int barangayId;

    private Map<Integer, Document> documentMap;

    public DisplayingDocController(DisplayingDocService displayingDocService, int barangayId){
        this.displayingDocService = displayingDocService;
        this.barangayId = barangayId;
    }

    public boolean isThereExistingDocs(){
        try{
            this.documentMap = displayingDocService.getDocumentMap(
                    this.barangayId
            );

            return true;
        }catch (NotFoundException e){
            Util.printException(e.getMessage());
        }

        return false;
    }

    public void printDocs(){
        Util.printSectionTitle("Existing Documents : Doc ID -> Document Name - Price - Requirements -> Document File");

        documentMap.forEach((key, document) -> {
            Util.printInformation(
                    "%d -> %s".formatted(key, document.toString())
            );
        });
    }
}
