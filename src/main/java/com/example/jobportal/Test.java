package com.example.jobportal;

import com.pcloud.sdk.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
public class Test {

    public static void main(String[] args) throws IOException, ApiError {
        ApiClient apiClient = PCloudSdk.newClientBuilder()
                .authenticator(Authenticators.newOAuthAuthenticator("uy2z7ZAPC048V3lekZb58kG7Z3YQEF47DVVVue3EzT8SB5jhfv0kX")).create();


        File localFile = new File("D:\\Projects\\Jobportal\\Bhushan_Gadekar.pdf");
      //  uploadFile(apiClient, localFile);
        upload(apiClient,localFile);



    }

    private static RemoteFile  uploadFile(ApiClient apiClient, File file) throws IOException, ApiError {
        return apiClient.createFile(RemoteFolder.ROOT_FOLDER_ID, file.getName(), DataSource.create(file), new Date(file.lastModified()), new ProgressListener() {
            public void onProgress(long done, long total) {
                System.out.format("\rUploading... %.1f\n", ((double) done / (double) total) * 100d);
            }
        }).execute();
    }

    private static void upload(ApiClient apiClient, File localFile) throws IOException, ApiError {
       RemoteFile uploadedFile = apiClient.createFile(
               7791638340l,
                localFile.getName(),
                DataSource.create(localFile)
                ).execute();
        System.out.println(uploadedFile.createFileLink().urls());

        RemoteFolder folder = apiClient.listFolder(RemoteFolder.ROOT_FOLDER_ID).execute();
        System.out.println(folder.children());

    }
}
