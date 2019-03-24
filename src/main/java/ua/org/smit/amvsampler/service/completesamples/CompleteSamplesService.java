/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.completesamples;

import com.google.common.collect.Ordering;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.encodersamples.SamplesEncoder;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.Base64Util;
import ua.org.smit.amvsampler.util.FilesUtil;
import static ua.org.smit.amvsampler.util.FilesUtil.makeEmptyFile;
import ua.org.smit.amvsampler.util.TextOnFile;

/**
 *
 * @author smit
 */
class CompleteSamplesService {

    private static final Logger log = LogManager.getLogger(CompleteSamplesService.class);
    
    static ArrayList<Sample> getSamplesByLimits(String folderName, Limits limits){
        ArrayList<Sample> samples = SamplesInFolder.get(folderName);
        Iterator<Sample> iterator = samples.iterator();
        while (iterator.hasNext()) {
           Sample sample = iterator.next();
           if (sample.getAvgPercent() < limits.getMinAvgPercent() || isOutOfRage(sample, limits)) {
               iterator.remove();
           }
        }
        
        return samples;
    }
    
    static ArrayList<Sample> getSamplesByLimits(String folderName) {
        Limits limits = readLimits(folderName);
        return getSamplesByLimits(folderName, limits);
    }
    
    static int deleteSamplesByLimits(String folderName) {
        ArrayList<Sample> samplesWithMoreThanMinPercentLImit = getSamplesByLimits(folderName);
        int count = 0;
        for (Sample sample : SamplesInFolder.get(folderName)){
            if (!SearchListSamples.isFound(sample, samplesWithMoreThanMinPercentLImit)){
                delete(sample);
                count++;
            }
        }
        return count;
    }

    static ArrayList<File> getFoldersWithSplitedFiles() {
        ArrayList<File> folders = FilesUtil.getFolders(Settings.getBaseOfSamplesFolder());
        Collections.sort(folders, Ordering.usingToString());
        return folders;
    }

    private static int getAvgPercentLimit(File folder) {
        File avgPercentLimit = SamplesFiles.getAvgPercentFile(folder);
        if (avgPercentLimit.exists()) {
            String percentString = TextOnFile.readByLine(avgPercentLimit).get(0);
            return Integer.valueOf(percentString);
        } else {
            return 0;
        }
    }
    
    private static String getRage(File folderWithSample) {
        File rage = SamplesFiles.getRageFile(folderWithSample);
        if (rage.exists()) {
            return TextOnFile.readByLine(rage).get(0);
        } else {
            ArrayList<Sample> samples = SamplesInFolder.get(folderWithSample.getName());
            return "0-" + samples.get(samples.size()-1).getSs();
        }
    }

    static void saveLimits(Limits limits) {
        log.info("Save limits: " + limits);
        saveRage(limits.getFolderName(), limits.getRageByString());
        saveMinAvgPercentLimit(limits.getFolderName(), limits.getMinAvgPercent());
    }
    
    static Limits readLimits(String folderName) {
        File folderWithSample = SamplesFiles.getFolderFromBaseByName(folderName);
        Limits limits = new Limits(folderName, getAvgPercentLimit(folderWithSample), getRage(folderWithSample));
        log.info("Readed limits: " + limits);
        return limits;
    }

    private static void saveRage(String folderName, String rage) {
        File rageFile = SamplesFiles.getRageFile(folderName);
        makeEmptyFile(rageFile);
        
        ArrayList<String> textInLines = new ArrayList();
        textInLines.add(rage);
        TextOnFile.reWriteTextInFile(rageFile, textInLines);
        
        log.info("Save: " + rageFile);
    }

    private static void saveMinAvgPercentLimit(String folderName, int minAvgPercent) {
        File avgPercentFile = SamplesFiles.getAvgPercentFile(folderName);
        makeEmptyFile(avgPercentFile);
        
        ArrayList<String> textInLines = new ArrayList();
        textInLines.add(String.valueOf(minAvgPercent));
        TextOnFile.reWriteTextInFile(avgPercentFile, textInLines);
        
        log.info("Save: " + avgPercentFile);
    }

    private static boolean isOutOfRage(Sample sample, Limits limits) {
        if (sample.getSs() < limits.getSsStart() || sample.getSs() >  limits.getSsEnd()){
            return true;
        }
        return false;
    }

//    static void moveToExportFolder(String folderName, HttpServletRequest request) {
//        ArrayList<Sample> samples = new ArrayList();
//        
//        for (Sample sample : SamplesInFolder.get(folderName)){
//            String pathInbase64 = new Base64Util().encode(sample.getMp4().toString());
//            String name = request.getParameter(pathInbase64);
//            if (name != null){
//                samples.add(sample);
//            }
//        }
//        
//        ArrayList<File> samplesFiles = getFilesFromSamples(samples);
//        encodeAndMove(samplesFiles);
//    }
//    
//    static void moveToExportFolder(ArrayList<File> folders, HttpServletRequest request) {
//        for (File folder : folders){
//            moveToExportFolder(folder.getName(), request);
//        }
//    }
    
    static int deleteSelectedSamples(ArrayList<File> foldersFromGroup, HttpServletRequest request) {
        int count = 0;
        
        for (File folder : foldersFromGroup){
            for (Sample sample : SamplesInFolder.get(folder.getName())){
                String pathInbase64 = new Base64Util().encode(sample.getMp4().toString());
                String name = request.getParameter(pathInbase64);
                if (name != null){
                    delete(sample);
                    count++;
                }
            }
        }
        
        return count;
    }
    
    static int deleteNotSelectedSamples(String folderName, HttpServletRequest request) {
        ArrayList<Sample> notForDelete = new ArrayList();
        ArrayList<Sample> samplesAll = SamplesInFolder.get(folderName);
        for (Sample sample : samplesAll){
            String pathInbase64 = new Base64Util().encode(sample.getMp4().toString());
            String name = request.getParameter(pathInbase64);
            if (name != null){
                notForDelete.add(sample);
            }
        }
        
        int deletedCount = 0;
        for (Sample sample : samplesAll){
            if (!SearchListSamples.isFound(sample, notForDelete)){
                delete(sample);
                deletedCount++;
            }
        }
        
        return deletedCount;
    }

    static int deleteFolder(String folderName) {
        File folder = SamplesFiles.getFolderFromBaseByName(folderName);
        File[] folders = folder.listFiles();
        FilesUtil.deleteFolderWithFiles(folder);
        return folders.length - 2;
    }

    private static void delete(Sample sample) {
        try{
            FilesUtil.deleteFolderWithFiles(sample.getSsFolder());
        } catch (NullPointerException ex){
            log.warn("Cant delete sample folder: " + sample.getSsFolder());
        }
    }

    static ArrayList<Sample> getSamples(ArrayList<File> foldersWithSplitedFiles) {
        ArrayList<Sample> samples = new ArrayList();
        for (File folders : foldersWithSplitedFiles){
            samples.addAll(SamplesInFolder.get(folders.getName()));
        }
        return samples;
    }
    
    static ArrayList<Sample> getSamplesByPaths(ArrayList<String> samplesSsPath) {
        ArrayList<Sample> samples = new ArrayList();
        
        for (String sampleSsPath : samplesSsPath){
            try {
                samples.add(SamplesInFolder.getSample(new File(sampleSsPath)));
            } catch (FileNotFoundException ex) {
                log.warn("Cant find sample folder by path '" + sampleSsPath + "' " + ex);
            }
        }
        
        return samples;
    }

    static ArrayList<String> getSelectedSamplesPaths(String folderName, HttpServletRequest request) {
        ArrayList<String> samplesPath = new ArrayList();
        
        for (Sample sample : SamplesInFolder.get(folderName)){
            String pathInbase64 = new Base64Util().encode(sample.getMp4().toString());
            String name = request.getParameter(pathInbase64);
            if (name != null){
                samplesPath.add(sample.getSsFolder().getAbsolutePath());
            }
        }
        
        return samplesPath;
    }

    private static ArrayList<File> getFilesFromSamples(ArrayList<Sample> samples) {
        ArrayList<File> files = new ArrayList();
        for (Sample sample : samples){
            files.add(sample.getMp4());
        }
        return files;
    }

//    private static void encodeAndMove(ArrayList<File> samplesFiles) {
//        SamplesEncoder samplesEncoder = new SamplesEncoder();
//        ArrayList<File> encodedSamples = samplesEncoder.encode(samplesFiles, false);
//
//        for (File file : encodedSamples){
//            File dest = new File(Settings.getExportFolder() + File.separator + file.getName());
//            FilesUtil.copy(file, dest);
//            file.delete();
//        }
//        
//        if (true){
//            ArrayList<File> samplesWithoutDublicates = samplesEncoder.encode(samplesFiles, true);
//
//            for (File file : samplesWithoutDublicates){
//                File dest = new File(Settings.getExportFolder() + File.separator + FilesUtil.getFileNameWithoutExtension(file) + "_no_dubl.mp4");
//                FilesUtil.copy(file, dest);
//                file.delete();
//            }
//        }
//    }

    
}
