package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.Util;
import org.isu_std.io.exception.OperationFailedException;
import org.isu_std.io.file_setup.DocxFileHandler;
import org.isu_std.io.folder_setup.Folder;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.models.UserPersonal;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class RequestApproveService {
    private final DocumentRequestDao documentRequestDao;

    public RequestApproveService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void requestApprovePerformed(RequestDocumentContext requestDocumentContext) throws IOException, OperationFailedException{
        if (!createOutputDocumentFile(requestDocumentContext)) {
            throw new OperationFailedException("Failed to Create Document Output File! Please try again.");
        }

        String referenceId = requestDocumentContext.documentRequest().referenceId();
        if (!documentRequestDao.setRequestApprove(referenceId)) {
            throw new OperationFailedException("Failed to Approve the Requested Document! Please try again.");
        }
    }

    protected boolean createOutputDocumentFile(RequestDocumentContext requestDocumentContext) throws IOException, OperationFailedException {
        String filePath = FolderConfig.DOC_APPROVE_PATH.getPath();
        Folder.setFileFolder(filePath);

        File outputFile = copyDefaultFile(requestDocumentContext, filePath);
        if (outputFile == null) {
            return false;
        }

        UserPersonal userPersonal = requestDocumentContext.userPersonal();
        Map<String, String> informations = getInformationsMap(userPersonal);
        DocxFileHandler.editDocxPlaceHolders(outputFile, informations);

        return true;
    }

    protected File copyDefaultFile(RequestDocumentContext requestDocumentContext, String filePath){
        File defaultFile = requestDocumentContext.document().documentFile();
        String fileName = requestDocumentContext.documentRequest().referenceId() + defaultFile.getName();

        File newFile = new File("%s_%s".formatted(filePath, fileName));

        try(InputStream inputStream = new FileInputStream(defaultFile);
            OutputStream outputStream = new FileOutputStream(newFile)
        ){
            byte[] bytes = new byte[8192];
            int reader;

            while((reader = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, reader);
            }

            return newFile;
        }catch (IOException e){
            Util.printException(e.getMessage());
        }

        return null;
    }

    private Map<String, String> getInformationsMap(UserPersonal userPersonal){
        Map<String, String> informationsMap = new HashMap<>();

        Field[] keys = UserPersonal.class.getDeclaredFields();
        String[] values = userPersonal.valueToStringArr();

        for(int i = 0; i < keys.length; i++){
            informationsMap.put(keys[i].getName(), values[i]);
        }

        return informationsMap;
    }
}
