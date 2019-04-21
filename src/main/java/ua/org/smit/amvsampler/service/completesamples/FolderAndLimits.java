/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.completesamples;

import java.io.File;

/**
 *
 * @author smit
 */
public class FolderAndLimits {

    private final File folder;
    private final int avgPercentLimit;
    private final int countOfSelectedSamples;
    private final String rage;

    public FolderAndLimits(File folder, int avgPercentLimit, int countOfSelectedSamples, String rage) {
        this.folder = folder;
        this.avgPercentLimit = avgPercentLimit;
        this.countOfSelectedSamples = countOfSelectedSamples;
        this.rage = rage;
    }

    public File getFolder() {
        return folder;
    }

    public int getAvgPercentLimit() {
        return avgPercentLimit;
    }

    public int getCountOfSelectedSamples() {
        return countOfSelectedSamples;
    }

    public String getRage() {
        return rage;
    }

    @Override
    public String toString() {
        return "FolderAndLimit{" + "folder=" + folder + ", avgPercentLimit=" + avgPercentLimit + ", countOfSelectedSamples=" + countOfSelectedSamples + ", rage=" + rage + '}';
    }

}
