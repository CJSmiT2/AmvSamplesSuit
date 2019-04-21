/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.statistics;

import java.io.File;
import java.util.ArrayList;
import ua.org.smit.amvsampler.service.TxtFileService;

/**
 *
 * @author smit
 */
public class StatisticsInfo {

    private static final File STATISTICS_FILE = new File("statistics.txt");

    private int createdSamples;
    private int removeSamples;
    private int samplesInGroup;
    private int processedFilesCount;

    public StatisticsInfo() {
        read();
    }

    public int getUnprocessedSamplesCount() {
        return createdSamples - removeSamples - samplesInGroup;
    }

    void addOneCreated() {
        createdSamples++;
    }

    void addOneRemoved() {
        removeSamples++;
    }

    void addOneProcessed() {
        samplesInGroup++;
    }

    void removeOneFromCreated() {
        createdSamples--;
    }

    void removeOneFromRemoved() {
        removeSamples--;
    }

    void removeOneFromProcessed() {
        samplesInGroup--;
    }

    void addCreated(int samplesCount) {
        samplesInGroup = samplesInGroup + samplesCount;
    }

    void removeFromCreated(int samplesCount) {
        createdSamples = createdSamples - samplesCount;
    }

    void addToRemoved(int count) {
        removeSamples = removeSamples + count;
    }

    public int getCreatedSamplesCount() {
        return createdSamples;
    }

    public int getRemoveSamplesCount() {
        return removeSamples;
    }

    public int getSamplesInGroupCount() {
        return samplesInGroup;
    }

    public int getProcessedFilesCount() {
        return processedFilesCount;
    }

    public void setProcessedFilesCount(int processedFilesCount) {
        this.processedFilesCount = processedFilesCount;
    }

    private void read() {
        try {
            ArrayList<String> lines = TxtFileService.getAll(STATISTICS_FILE);
            createdSamples = Integer.valueOf(lines.get(0));
            removeSamples = Integer.valueOf(lines.get(1));
            samplesInGroup = Integer.valueOf(lines.get(2));
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(STATISTICS_FILE + " is empty!");
        }
    }

    void save() {
        ArrayList<String> lines = new ArrayList();
        lines.add(String.valueOf(createdSamples));
        lines.add(String.valueOf(removeSamples));
        lines.add(String.valueOf(samplesInGroup));
        TxtFileService.reWrite(STATISTICS_FILE, lines);
    }

}
