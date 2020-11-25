package com.example.websockert.web.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;


import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Author: liulang
 * @Date: 2020/9/23 17:13
 */
@Configuration
public class CommonsMultipartResolverExt extends CommonsMultipartResolver {

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        MyProgressListener listener = new MyProgressListener(request);
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        fileUpload.setProgressListener(listener);
        try {
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            return parseFileItems(fileItems, encoding);
        }
        catch (Exception ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        }
    }
}
