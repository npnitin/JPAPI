package com.example.service;

import com.example.constants.PcloudConstants;
import com.example.models.User;
import com.example.repository.UserRepository;
import com.pcloud.sdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class PcloudServiceImpl implements  PcloudService {

    @Value("${pcloud.token}")
    private String token;

    @Value("${pcloud.resumes.folderId}")
    private Long resumeFolderId;

    @Value("${pcloud.profilephots.folderId}")
    private Long profilePhotFolderId;

    @Autowired
    UserRepository userRepository;

    @Override
    public void uploadFile(MultipartFile file,String userId,int type) throws IOException, ApiError {
        User user = userRepository.findById(userId).get();
        if(user == null){
            return;
        }

        RemoteFile uploadedFile = getApiClient().createFile(
                getFolderId(type),
                file.getName(),
                DataSource.create(file.getBytes())
        ).execute();
        if(type == PcloudConstants.RESUME_UPLOAD){
            user.setResumeUrl(uploadedFile.createFileLink().urls().get(1).toString());
        }else if(type == PcloudConstants.PROFILE_PHOTO_UPLOAD){
            user.setProfilePhotoUrl(uploadedFile.createFileLink().urls().get(1).toString());
        }
        userRepository.save(user);
    }

    private long getFolderId(int type) {
        if(type == PcloudConstants.RESUME_UPLOAD){
            return resumeFolderId;
        }else if(type == PcloudConstants.PROFILE_PHOTO_UPLOAD){
            return profilePhotFolderId;
        }
        return 0;
    }

    private ApiClient getApiClient(){
        ApiClient apiClient = PCloudSdk.newClientBuilder()
                .authenticator(Authenticators.newOAuthAuthenticator(token))
                .create();
        return apiClient;
    }
}