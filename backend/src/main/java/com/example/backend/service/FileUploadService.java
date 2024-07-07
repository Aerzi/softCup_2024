package com.example.backend.service;

import java.io.InputStream;

public interface FileUploadService {
    String uploadImgFile(InputStream inputStream, long size, String extName);
}
