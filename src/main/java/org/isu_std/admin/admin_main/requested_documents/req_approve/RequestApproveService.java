package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.dao.DocumentRequestDao;
import org.isu_std.io.Util;
import org.isu_std.io.custom_exception.OperationFailedException;
import org.isu_std.io.file_setup.DocxFileManager;
import org.isu_std.io.folder_setup.FolderManager;
import org.isu_std.io.folder_setup.FolderConfig;
import org.isu_std.models.UserPersonal;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

public class RequestApproveService {
    private final DocumentRequestDao documentRequestDao;

    public RequestApproveService(DocumentRequestDao documentRequestDao){
        this.documentRequestDao = documentRequestDao;
    }

    protected void requestApprovePerformed(RequestDocumentContext requestDocumentContext)
            throws OperationFailedException{
        String referenceId = requestDocumentContext.documentRequest().referenceId();
        if (!documentRequestDao.setRequestApprove(referenceId)) {
            throw new OperationFailedException("Failed to Approve the Requested Document! Please try again.");
        }
    }

    protected void createOutputDocumentFile(RequestDocumentContext requestDocumentContext)
            throws IOException, OperationFailedException {
        String filePath = FolderConfig.DOC_APPROVE_PATH.getPath();
        FolderManager.setFileFolder(filePath);

        File outputFile = copyDefaultFile(requestDocumentContext, filePath);
        if (outputFile != null) {
            UserPersonal userPersonal = requestDocumentContext.userPersonal();
            Map<String, String> informations = getInformationsMap(userPersonal);
            DocxFileManager.editDocxPlaceHolders(outputFile, informations);
            return;
        }

        throw new OperationFailedException(
                "Failed to Create Document Output File! Please try again."
        );
    }

    protected File copyDefaultFile(RequestDocumentContext requestDocumentContext, String filePath){
        File defaultFile = requestDocumentContext.document().documentFile();
        String referenceId = requestDocumentContext.documentRequest().referenceId();
        String fileName = "%s_%s".formatted(referenceId, defaultFile.getName());

        File newFile = new File(filePath + File.separator + fileName);

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

        putUserPerInformationOnMap(informationsMap, userPersonal);

        return informationsMap;
    }

    private void putUserPerInformationOnMap(Map<String, String> informationMap, UserPersonal userPersonal){
        RecordComponent[] keys = UserPersonal.class.getRecordComponents();
        String[] values = userPersonal.valueToStringArr();

        for(int i = 0; i < keys.length; i++){
            informationMap.put(keys[i].getName(), values[i]);
        }
    }
}
