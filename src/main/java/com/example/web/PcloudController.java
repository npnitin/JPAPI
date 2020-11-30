package com.example.web;

import com.example.constants.PcloudConstants;
import com.example.service.PcloudService;
import com.pcloud.sdk.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/jobportal/docs")
public class PcloudController {

    @Autowired
    PcloudService pcloudService;

    @PostMapping("/resume")
    public String uploadResume(@RequestParam("userId") String userId, @RequestParam("selectedFile") MultipartFile file) throws IOException, MessagingException, ApiError {

        pcloudService.uploadFile(file,userId, PcloudConstants.RESUME_UPLOAD);
        return "successs";
    }
    @PostMapping("/profilePhoto")
    public String uploadProfilePhoto(@RequestParam("userId") String userId, @RequestParam("selectedFile") MultipartFile file) throws IOException, MessagingException, ApiError {

        pcloudService.uploadFile(file,userId,PcloudConstants.PROFILE_PHOTO_UPLOAD);
        return "successs";
    }
}
