/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.completesamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.FilesUtil;
import ua.org.smit.amvsampler.util.TextOnFile;

/**
 *
 * @author SmiT
 */
public class SamplesInFolder {

    private static final Logger log = LogManager.getLogger(SamplesInFolder.class);
    private static final String RECOMPRESSED_MARKER_FILE_NAME = "recompressed.txt";

    static ArrayList<Sample> get(String folderName) {
        log.info("Get samples from: " + folderName);
        File folderWithSamples
                = new File(Settings.getBaseOfSamplesFolder()
                        + File.separator + folderName);
        return getAll(folderWithSamples);
    }

    private static ArrayList<Sample> getAll(File folderWithSamples) {
        ArrayList<File> foldersSs = FilesUtil.getFolders(folderWithSamples);
        Iterator<File> iterator = foldersSs.iterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            try {
                Integer.valueOf(file.getName());
            } catch (NumberFormatException ex) {
                iterator.remove();
            }
        }
        return getSamples(foldersSs);
    }

    private static ArrayList<Sample> getSamples(ArrayList<File> foldersSs) {
        ArrayList<Sample> samples = new ArrayList();
        for (File folderSs : foldersSs) {
            ArrayList<File> files = FilesUtil.getFiles(folderSs);
            try {
                samples.add(getSample(folderSs));
            } catch (FileNotFoundException ex) {
                log.warn("Cant find samples files by folder'" + folderSs + "'");
            }
        }
        samples = sortBySs(samples);
        return samples;
    }

    static Sample getSample(File folderSs) throws FileNotFoundException {
        ArrayList<File> files = FilesUtil.getFiles(folderSs);
        Sample sample = new Sample();
        sample.setParentFolder(folderSs.getParentFile());
        sample.setSs(Integer.valueOf(folderSs.getName()));
        sample.setGif(FilesUtil.findByExtension(files, "gif"));
        sample.setMp4(FilesUtil.findByExtension(files, "mp4"));
        sample.setAvgPercent(readAvgPercet(files));
        sample.setTitle(folderSs.getParentFile().getName());
        sample.setReCompressed(isRecompressedMarkerExist(folderSs));
        return sample;
    }

    

    private static int readAvgPercet(ArrayList<File> files) throws FileNotFoundException {
        File percentFile = FilesUtil.findByExtension(files, "txt");
        if (percentFile.getName().contains(Settings.AVG_PERCENT_FILE_NAME)) {
            String percentString = TextOnFile.readByLine(percentFile).get(0);
            return Integer.valueOf(percentString);
        } else {
            log.warn("Cant read avgPercent from '" + percentFile + "'");
            return 0;
        }
    }

    private static ArrayList<Sample> sortBySs(ArrayList<Sample> samples) {
        ArrayList<Sample> sorted = new ArrayList();
        int maxSs = find(samples);

        for (int ss = 0; ss < maxSs; ss += Settings.getSampleLengthInSec()) {
            if (isSsExist(ss, samples)) {
                sorted.add(get(ss, samples));
            }
        }

        return sorted;
    }

    private static int find(ArrayList<Sample> samples) {
        int maxSs = 0;
        for (Sample sample : samples) {
            if (sample.getSs() > maxSs) {
                maxSs = sample.getSs();
            }
        }
        return maxSs;
    }

    private static boolean isSsExist(int ss, ArrayList<Sample> samples) {
        for (Sample sample : samples) {
            if (ss == sample.getSs()) {
                return true;
            }
        }
        return false;
    }

    private static Sample get(int ss, ArrayList<Sample> samples) {
        for (Sample sample : samples) {
            if (ss == sample.getSs()) {
                return sample;
            }
        }
        throw new RuntimeException("Not found sample by ss=" + ss);
    }

    private static boolean isRecompressedMarkerExist(File folderSs) {
        return new File(folderSs + File.separator + RECOMPRESSED_MARKER_FILE_NAME).exists();
    }
    
    public static void createRecompressedMarker(File folderSs){
        FilesUtil.makeEmptyFile(new File(folderSs + File.separator + RECOMPRESSED_MARKER_FILE_NAME));
    }
}
