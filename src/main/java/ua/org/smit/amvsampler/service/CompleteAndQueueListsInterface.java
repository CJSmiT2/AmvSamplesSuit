/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author smit
 */
public interface CompleteAndQueueListsInterface {
    
    ArrayList<File> getUnprocessedVideoFiles();
    ArrayList<File> getAllowedVideoFilesFromSrcFolder();
    ArrayList<File> getFilesFromQueue();
    void moveToCompleteList(File srcVideo);
    File getFirstFileFromQueue();
    void addToQueueFile(File videoFile);
    boolean isExistInQueueFiles(String videoFileName);
    void removeFromQueueFile(String videoFileName);
    ArrayList<String> getCompletedFiles();
    
}
