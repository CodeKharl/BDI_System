package org.isu_std.admin.admin_main;

import org.isu_std.dao.DocumentDao;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.dao.UserPersonalDao;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.models.Document;
import org.isu_std.models.DocumentRequest;
import org.isu_std.models.UserPersonal;

import java.util.Optional;

public class ReqDocsManagerFactory {
    private final DocumentDao documentDao;
    private final UserPersonalDao userPersonalDao;

    public ReqDocsManagerFactory(DocumentDao documentDao, UserPersonalDao userPersonalDao){
        this.documentDao = documentDao;
        this.userPersonalDao = userPersonalDao;
    }

    public ReqDocsManager createReDocsManager(DocumentRequest documentRequest) throws OperationFailedException {
        UserPersonal userPersonal = getUserPersonal(documentRequest.userId());
        Document document = getDocument(documentRequest.documentId());

        return new ReqDocsManager(documentRequest, userPersonal, document);
    }

    private UserPersonal getUserPersonal(int userId){
        Optional<UserPersonal> optionalUserPersonal = userPersonalDao.getOptionalUserPersonal(userId);
        return optionalUserPersonal.orElseThrow(
                () -> new OperationFailedException("Failed to get the informations of the user.")
        );
    }

    private Document getDocument(int docId){
        Optional<Document> optionalDocument = documentDao.getOptionalDocument(docId);
        return optionalDocument.orElseThrow(
                () -> new OperationFailedException("Failed to get the document.")
        );
    }
}
