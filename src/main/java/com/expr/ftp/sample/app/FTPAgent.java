package com.expr.ftp.sample.app;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;


public class FTPAgent {

    private static final String HOSTNAME = "127.0.0.1";
    private static final String USERNAME = "local";
    private static final String PASSWORD = "password";
    private static final String PORT = "2121";
    private static final String UPLOAD_PATH = "/ftp-files/";

    private static final String BINARY_FILE_1
            = "<file-location>/FTP-Sample/src/test/files/binary/binary.tar.gz";

    private static final String BINARY_FILE_2
            = "<file-location>/FTP-Sample/src/test/files/binary/ant-1.6.5.jar";

    private static final String TEXT_FILE_1
            = "<file-location>/FTP-Sample/src/test/files/text/HelloWorld.txt";

    private static final String TEXT_FILE_2
            = "<file-location>/FTP-Sample/src/test/files/text/Welcome.txt";

    public static void main( String[] args ) {

        // Create Text Files
        File textFile1 = new File(TEXT_FILE_1);
        File textFile2 = new File(TEXT_FILE_2);

        List<File> textFileList  = new ArrayList<File>();
        textFileList.add(textFile1);
        textFileList.add(textFile2);

        // Create Binary Files
        File binaryFile1 = new File(BINARY_FILE_1);
        File binaryFile2 = new File(BINARY_FILE_2);

        List<File> binaryFileList  = new ArrayList<File>();
        binaryFileList.add(binaryFile1);
        binaryFileList.add(binaryFile2);

        // FTP Text Files.
        System.out.println( "FTP Text Files" );
        boolean isFTPTextFilesSuccess = ftpFiles(false, textFileList);
        System.out.println( "FTP Text Files status >> " + isFTPTextFilesSuccess);

        // FTP Binary Files.
        System.out.println( "FTP Binary Files" );
        boolean isFTPBinaryFilesSuccess = ftpFiles(true, binaryFileList);
        System.out.println( "FTP Binary Files Status >> " + isFTPBinaryFilesSuccess);

    }

    public static boolean ftpFiles(boolean isBinary, List<File> fileList) {
        System.out.println("Process upload files " + fileList);

        FTPClient ftpClient = new FTPClient();
        FileInputStream fileInputStream = null;

        try {
            if (fileList.size() > 0) {
                System.out.println("Connecting FTP  Hostname [" + HOSTNAME + "]  " +
                        "Username [" + USERNAME + "]  Password [" + PASSWORD + "]");
                ftpClient.connect(HOSTNAME, Integer.parseInt(PORT));
                boolean isLoggedIn = ftpClient.login(USERNAME, PASSWORD);

                if (isBinary)  {
                    System.out.println("Setting FTP File Transfer mode - BINARY_FILE_TYPE");
                    try {
                        ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    } catch (IOException e) {
                        System.out.println("Error occurred while setting file transfer mode to : BINARY_FILE_TYPE [{}] " +  e);
                    }
                }
                System.out.println("FTP Logged in status : " +  isLoggedIn);

                if(isLoggedIn) {
                    for (File fileEntry : fileList) {
                        System.out.println("Uploading file [" + fileEntry.getName() + "]");
                        fileInputStream = new FileInputStream(fileEntry);

                        boolean isSuccessfullyUploaded = ftpClient.storeFile(UPLOAD_PATH + fileEntry.getName(), fileInputStream);
                        if (isSuccessfullyUploaded) {
                            System.out.println("File uploaded successfully to [" + UPLOAD_PATH + "]");
                        } else {
                            System.out.println("File uploaded failed for FTP location [" + UPLOAD_PATH + "]");
                            return false;
                        }
                    }
                } else {
                    System.out.println("Login Failed for FTP Location.");
                    return false;
                }
                ftpClient.logout();
            } else {
                System.out.println("Received empty filtered file list. Skip file uploading.");
            }
        } catch (ConnectException ex) {
            System.out.println("Error Occurred Due to " + ex);
            return false;

        } catch (Exception ex) {
            System.out.println("Error Occurred Due to " + ex);
            return false;

        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                ftpClient.disconnect();
            } catch (IOException ex) {
                System.out.println("Error Occurred Due to" + ex);
            }
        }
        return true;
    }

}
