/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine;

import java.io.File;

/**
 *
 * @author smit
 */
public class EngineSplitAnalyze {

    private static EngineSplitAnalyze INSTANCE;
    private static boolean inProgress;
    private static File currentVideoFile;
    private static byte[] lastGif;
    private static int samplesCount;
    private static int ss;

    private static boolean cancelSpliting;

    public synchronized static EngineSplitAnalyze instance() {
        if (INSTANCE == null) {
            INSTANCE = new EngineSplitAnalyze();
        }
        return INSTANCE;
    }

    public void splitAndAnalyze(File videoFile) {
        if (!inProgress) {
            currentVideoFile = videoFile;
            inProgress = true;

            new Thread(new ThreadSplitAnalyze(videoFile)).start();
        }
    }

    public static void cancel() {
        cancelSpliting = true;
    }

    public static void resetStatusValues() {
        EngineSplitAnalyze.setInProgress(false);
        EngineSplitAnalyze.setCurrentVideoFile(null);
        EngineSplitAnalyze.setLastGif(null);
        EngineSplitAnalyze.setSamplesCount(0);
        EngineSplitAnalyze.setSs(0);
    }

    public static void samplesCountPlusOne() {
        samplesCount += 1;
    }

    public synchronized static boolean isInProgress() {
        return inProgress;
    }

    public synchronized static void setInProgress(boolean inProgress) {
        EngineSplitAnalyze.inProgress = inProgress;
    }

    public synchronized static File getCurrentVideoFile() {
        return currentVideoFile;
    }

    public synchronized static void setCurrentVideoFile(File currentVideoFile) {
        EngineSplitAnalyze.currentVideoFile = currentVideoFile;
    }

    public synchronized static byte[] getLastGif() {
        return lastGif;
    }

    public synchronized static void setLastGif(byte[] lastGif) {
        EngineSplitAnalyze.lastGif = lastGif;
    }

    public static boolean isCancelSpliting() {
        return cancelSpliting;
    }

    public static void setCancelSpliting(boolean cancelSpliting) {
        EngineSplitAnalyze.cancelSpliting = cancelSpliting;
    }

    public static int getSamplesCount() {
        return samplesCount;
    }

    public static void setSamplesCount(int samplesCount) {
        EngineSplitAnalyze.samplesCount = samplesCount;
    }

    public static int getSs() {
        return ss;
    }

    public static void setSs(int ss) {
        EngineSplitAnalyze.ss = ss;
    }

}
