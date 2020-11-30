package com.example.service;

import com.pcloud.sdk.ApiError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface PcloudService {

    public void uploadFile(MultipartFile file,String UserId,int type) throws IOException, ApiError;
}
