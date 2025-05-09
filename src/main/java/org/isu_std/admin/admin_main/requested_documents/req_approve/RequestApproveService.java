package org.isu_std.admin.admin_main.requested_documents.req_approve;

import org.isu_std.admin.admin_main.RequestDocumentContext;
import org.isu_std.admin.doc_output_file_provider.DocOutFileProvider;
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

    protected void requestApprovePerformed(RequestDocumentContext requestDocumentContext){
        String referenceId = requestDocumentContext.documentRequest().referenceId();
        if (!documentRequestDao.setRequestApprove(referenceId)) {
            throw new OperationFailedException("Failed to Approve the Requested Document! Please try again.");
        }
    }

    protected void createOutputDocumentFile(RequestDocumentContext requestDocumentContext){
        if(!DocOutFileProvider.createOutputDocumentFile(requestDocumentContext)){
            throw new OperationFailedException("Failed to Create Document Output File! Please try again.");
        }
    }
}
