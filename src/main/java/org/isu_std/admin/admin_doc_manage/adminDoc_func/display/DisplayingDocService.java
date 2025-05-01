package org.isu_std.admin.admin_doc_manage.adminDoc_func.display;

import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.models.Document;
import org.isu_std.dao.DocumentDao;

import java.util.Map;

public class DisplayingDocService {
    private final DocumentDao documentDao;

    public DisplayingDocService(DocumentDao documentDao){
        this.documentDao = documentDao;
    }

    public Map<Integer, Document> getDocumentMap(int barangayId) {
        Map<Integer, Document> documentHashMap = documentDao.getDocumentMap(barangayId);

        if(documentHashMap.isEmpty()){
            throw new NotFoundException("Theres no existing documents!");
        }

        return documentHashMap;
    }
}
