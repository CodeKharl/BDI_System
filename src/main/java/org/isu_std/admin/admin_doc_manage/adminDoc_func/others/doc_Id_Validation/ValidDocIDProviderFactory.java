package org.isu_std.admin.admin_doc_manage.adminDoc_func.others.doc_Id_Validation;

import org.isu_std.dao.DocumentDao;

public class ValidDocIDProviderFactory {
    private final DocumentDao documentDao;

    public ValidDocIDProviderFactory(DocumentDao documentDao){
        this.documentDao = documentDao;
    }

    public ValidDocIDProvider createValidDocIdProvider(int barangayId){
        var validDocIdService = new ValidDocIDService(documentDao);
        var validDocIdController = new ValidDocIDController(validDocIdService, barangayId);

        return new ValidDocIDProvider(validDocIdController);
    }
}
