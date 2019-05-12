/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.completesamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import ua.org.smit.amvsampler.service.settings.Settings;

/**
 *
 * @author smit
 */
public class CompleteSamplesImpl implements CompleteSamplesInterface {

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

    @Override
    public ArrayList<Sample> getAllSamples() {
        ArrayList<Sample> samples = new ArrayList();
        for (File titleFolder : getFoldersWithSplitedFiles()){
            ArrayList<File> folders = new ArrayList();
            folders.add(titleFolder);
            ArrayList<Sample> samplesFromFolder = getSamples(folders);
            samples.addAll(samplesFromFolder);
        }
        return samples;
    }

    @Override
    public Sample getSample(String titleFolder, int ss) {
        File folderSs = new File(Settings.getBaseOfSamplesFolder() + File.separator + titleFolder + File.separator + ss);
        return getSample(folderSs);
    }

    @Override
    public List<Sample> getNotRecompressedSamples() {
        return getAllSamples().stream()
                .filter(sample -> sample.isReCompressed() == false)
                .collect(Collectors.toList());
    }

    @Override
    public void markSampleAsRecompressed(Sample sample) {
        SamplesInFolder.createRecompressedMarker(sample.getSsFolder());
    }

}
