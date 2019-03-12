/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.completesamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author smit
 */
public class CompleteSamplesImpl implements CompleteSamplesInterface{

    @Override
    public ArrayList<Sample> getSamplesByLimits(String folderName, Limits limits) {
        return CompleteSamplesService.getSamplesByLimits(folderName, limits);
    }

    @Override
    public ArrayList<Sample> getSamplesByLimits(String folderName) {
        return CompleteSamplesService.getSamplesByLimits(folderName);
    }

    @Override
    public ArrayList<Sample> getSamples(ArrayList<File> foldersWithSplitedFiles) {
        return CompleteSamplesService.getSamples(foldersWithSplitedFiles);
    }

    @Override
    public ArrayList<Sample> getSamplesByPaths(ArrayList<String> samplesSsPath) {
        return CompleteSamplesService.getSamplesByPaths(samplesSsPath);
    }

    @Override
    public ArrayList<String> getSelectedSamplesPaths(String folderName, HttpServletRequest request) {
        return CompleteSamplesService.getSelectedSamplesPaths(folderName, request);
    }

    @Override
    public int deleteSamplesByLimits(String folderName) {
        return CompleteSamplesService.deleteSamplesByLimits(folderName);
    }

    @Override
    public ArrayList<File> getFoldersWithSplitedFiles() {
        return CompleteSamplesService.getFoldersWithSplitedFiles();
    }

    @Override
    public void saveLimits(Limits limits) {
        CompleteSamplesService.saveLimits(limits);
    }

    @Override
    public Limits readLimits(String folderName) {
        return CompleteSamplesService.readLimits(folderName);
    }

//    @Override
//    public void moveToExportFolder(String folderName, HttpServletRequest request) {
//        CompleteSamplesService.moveToExportFolder(folderName, request);
//    }
//
//    @Override
//    public void moveToExportFolder(ArrayList<File> folders, HttpServletRequest request) {
//        CompleteSamplesService.moveToExportFolder(folders, request);
//    }

    @Override
    public int deleteSelectedSamples(ArrayList<File> foldersFromGroup, HttpServletRequest request) {
        return CompleteSamplesService.deleteSelectedSamples(foldersFromGroup, request);
    }

    @Override
    public int deleteNotSelectedSamples(String folderName, HttpServletRequest request) {
        return CompleteSamplesService.deleteNotSelectedSamples(folderName, request);
    }

    @Override
    public int deleteFolder(String folderName) {
        return CompleteSamplesService.deleteFolder(folderName);
    }

    @Override
    public ArrayList<Sample> get(String folderName) {
        return SamplesInFolder.get(folderName);
    }

    @Override
    public Sample getSample(File folderSs) {
        try {
            return SamplesInFolder.getSample(folderSs);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Sample not found! Path: " + folderSs + "\n" + ex);
        }
    }

    
    
}
