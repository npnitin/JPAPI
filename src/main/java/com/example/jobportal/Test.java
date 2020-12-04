package com.example.jobportal;

import com.pcloud.sdk.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test {

    public static void main(String[] args) throws IOException, ApiError {
        Set<String> set=new HashSet<>();
        List<String> list = new ArrayList<>();
        set.contains("");
        ApiClient apiClient = PCloudSdk.newClientBuilder()
                .authenticator(Authenticators.newOAuthAuthenticator("uy2z7ZAPC048V3lekZb58kG7Z3YQEF47DVVVue3EzT8SB5jhfv0kX")).create();


        File localFile = new File("D:\\Projects\\Jobportal\\Bhushan_Gadekar.pdf");
      //  uploadFile(apiClient, localFile);
        RemoteFile file =upload(apiClient,localFile);


    }



    private static RemoteFile  uploadFile(ApiClient apiClient, File file) throws IOException, ApiError {
        return apiClient.createFile(RemoteFolder.ROOT_FOLDER_ID, file.getName(), DataSource.create(file), new Date(file.lastModified()), new ProgressListener() {
            public void onProgress(long done, long total) {
                System.out.format("\rUploading... %.1f\n", ((double) done / (double) total) * 100d);
            }
        }).execute();
    }

    private static RemoteFile upload(ApiClient apiClient, File localFile) throws IOException, ApiError {
       RemoteFile uploadedFile = apiClient.createFile(
               7791638340l,
                localFile.getName(),
                DataSource.create(localFile)
                ).execute();
        FileLink downloadLink = apiClient.createFileLink(uploadedFile, DownloadOptions.DEFAULT).execute();


        RemoteFolder folder = apiClient.listFolder(7791638340l).execute();
        System.out.println(folder.children());
    return  uploadedFile;
    }
}
