/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.completesamples;

import java.io.File;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author smit
 */
public interface CompleteSamplesInterface {
    
    ArrayList<Sample> getSamplesByLimits(String folderName, Limits limits);
    ArrayList<Sample> getSamplesByLimits(String folderName);
    ArrayList<Sample> getSamples(ArrayList<File> foldersWithSplitedFiles);
    ArrayList<Sample> getSamplesByPaths(ArrayList<String> samplesSsPath);
    ArrayList<String> getSelectedSamplesPaths(String folderName, HttpServletRequest request);
    int deleteSamplesByLimits(String folderName);
    ArrayList<File> getFoldersWithSplitedFiles();
    void saveLimits(Limits limits);
    Limits readLimits(String folderName);
//    void moveToExportFolder(String folderName, HttpServletRequest request);
//    void moveToExportFolder(ArrayList<File> folders, HttpServletRequest request);
    int deleteSelectedSamples(ArrayList<File> foldersFromGroup, HttpServletRequest request);
    int deleteNotSelectedSamples(String folderName, HttpServletRequest request);
    int deleteFolder(String folderName);
    ArrayList<Sample> get(String folderName);
    Sample getSample(File folderSs);
}
