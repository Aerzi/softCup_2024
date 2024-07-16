package com.example.backend.service;

import java.io.InputStream;

public interface FileUploadService {
    String uploadFile(InputStream inputStream, long size, String extName);
}
