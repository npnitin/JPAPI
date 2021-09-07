package com.example.jobportal;

import com.pcloud.sdk.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test {

    public static void main(String[] args) throws IOException, ApiError {



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
               7791712978l,
                localFile.getName(),
                DataSource.create(localFile)
                ).execute();
        FileLink downloadLink = apiClient.createFileLink(uploadedFile, DownloadOptions.DEFAULT).execute();

    return  uploadedFile;
    }
}
