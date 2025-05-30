package org.isu_std.admin.admin_main.admin_doc_manage.adminDoc_func.display;

import org.isu_std.io.SystemLogger;
import org.isu_std.io.custom_exception.DataAccessException;
import org.isu_std.io.custom_exception.NotFoundException;
import org.isu_std.io.custom_exception.ServiceException;
import org.isu_std.models.Document;
import org.isu_std.dao.DocumentDao;

import java.util.Map;

public class DisplayingDocService {
    private final DocumentDao documentDao;

    public DisplayingDocService(DocumentDao documentDao){
        this.documentDao = documentDao;
    }

    public Map<Integer, Document> getDocumentMap(int barangayId) {
        try {
            Map<Integer, Document> documentHashMap = documentDao.getDocumentMap(barangayId);

            if (documentHashMap.isEmpty()) {
                throw new NotFoundException("Theres no existing documents!");
            }

            return documentHashMap;
        }catch (DataAccessException e){
            SystemLogger.log(e.getMessage(), e);

            throw new ServiceException("Failed to fetch document map with barangay ID : " + barangayId);
        }
    }
}
