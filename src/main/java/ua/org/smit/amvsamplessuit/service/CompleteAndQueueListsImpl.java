/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author smit
 */
public class CompleteAndQueueListsImpl implements CompleteAndQueueListsInterface{

    @Override
    public ArrayList<File> getUnprocessedVideoFiles() {
        return FileListsManager.getUnprocessedVideoFiles();
    }

    @Override
    public ArrayList<File> getAllowedVideoFilesFromSrcFolder() {
        return FileListsManager.getAllowedVideoFilesFromSrcFolder();
    }

    @Override
    public ArrayList<File> getFilesFromQueue() {
        return FileListsManager.getFilesFromQueue();
    }

    @Override
    public void moveToCompleteList(File srcVideo) {
        FileListsManager.moveToCompleteList(srcVideo);
    }

    @Override
    public File getFirstFileFromQueue() {
        return FileListsManager.getFirstFileFromQueue();
    }

    @Override
    public void addToQueueFile(File videoFile) {
        FileListsManager.addToQueueFile(videoFile);
    }

    @Override
    public boolean isExistInQueueFiles(String videoFileName) {
        return FileListsManager.isExistInQueueFiles(videoFileName);
    }

    @Override
    public void removeFromQueueFile(String videoFileName) {
        FileListsManager.removeFromQueueFile(videoFileName);
    }

    @Override
    public ArrayList<String> getCompletedFiles() {
        return FileListsManager.getCompletedFiles();
    }
    
}
