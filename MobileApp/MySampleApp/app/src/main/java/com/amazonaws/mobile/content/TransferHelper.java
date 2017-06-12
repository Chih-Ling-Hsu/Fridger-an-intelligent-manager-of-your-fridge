//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.17
//
package com.amazonaws.mobile.content;

import java.io.File;

public interface TransferHelper {
    String DIR_DELIMITER = "/" ;

    void download(String filePath, long fileSize, ContentProgressListener listener);
    void upload(File file, String filePath, ContentProgressListener listener);
    void setProgressListener(String filePath, ContentProgressListener listener);
    void clearProgressListeners();
    long getSizeTransferring();
    boolean isTransferring(String filePath);
    boolean isTransferWaiting(String filePath);
    void destroy();
}
