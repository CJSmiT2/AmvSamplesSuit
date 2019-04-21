/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine;

import ua.org.smit.amvsampler.service.splitanalyzeengine.util.GifUtil;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.service.splitanalyzeengine.analyze.Analyze;
import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.CompleteAndQueueListsImpl;
import ua.org.smit.amvsampler.service.CompleteAndQueueListsInterface;
import ua.org.smit.amvsampler.service.statistics.StatisticsInfoImpl;
import ua.org.smit.amvsampler.service.statistics.StatisticsInfoInterface;
import ua.org.smit.amvsampler.util.FilesUtil;
import ua.org.smit.amvsampler.util.StringUtil;
import ua.org.smit.amvsampler.util.TextOnFile;

/**
 *
 * @author smit
 */
public class ThreadSplitAnalyze implements Runnable {

    private final File srcVideo;

    private static final Logger log = LogManager.getLogger(ThreadSplitAnalyze.class);

    ThreadSplitAnalyze(File file) {
        this.srcVideo = file;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        log.info("Start split: " + srcVideo);

        File folderForSplitedFile = createFolderForSplitedFile(srcVideo, Settings.getBaseOfSamplesFolder());

        File srcVideoTmp = TmpFiles.copy(srcVideo);

        cutAndAnalyze(srcVideoTmp, folderForSplitedFile);

        srcVideoTmp.delete();
        if (Settings.isUseRamDisk()) {
            FilesUtil.deleteFolderWithFiles(srcVideoTmp.getParentFile());
        }

        EngineSplitAnalyze.resetStatusValues();

        if (!EngineSplitAnalyze.isCancelSpliting()) {
            CompleteAndQueueListsInterface completeAndQueueLists = new CompleteAndQueueListsImpl();
            completeAndQueueLists.moveToCompleteList(srcVideo);
        }
        EngineSplitAnalyze.setCancelSpliting(false);

        log.info("Complete split (time " + ((System.currentTimeMillis() - time) / 1000) + "sec): " + srcVideo);
    }

    private void cutAndAnalyze(File srcVideo, File folderForSplitedFile) {
        int ss = 0;
        while (true) {

            StatisticsInfoInterface statisticsInfo = new StatisticsInfoImpl();

            if (EngineSplitAnalyze.isCancelSpliting()) {
                log.info("Split stoped by cancel!");
                statisticsInfo.removeFromCreated(EngineSplitAnalyze.getSamplesCount());
                break;
            }

            FramesExtractor framesExtractor = new FramesExtractor();
            framesExtractor.setSrcVideo(srcVideo);
            framesExtractor.setFolderForSpletedFiles(folderForSplitedFile);
            framesExtractor.setSs(ss);
            framesExtractor.setSampleLength(Settings.getSampleLengthInSec());
            if (!framesExtractor.execute()) {
                break;
            }

            File singleCutFile = framesExtractor.getSingleCutFile();
            ArrayList<File> frames = framesExtractor.getFrames();

            int avgPercent = new Analyze().getAvgPercent(frames, Settings.getResolutionForGifAndAnalyzing());
            writeAvgPercentInTxt(singleCutFile, avgPercent);

            File lastGif = new GifUtil().create(singleCutFile, Settings.getImgExtensionForSpliting());
            EngineSplitAnalyze.setLastGif(lastGif);

            removeUnnecessaryFrames(frames);

            if (Settings.isUseRamDisk()) {
                FilesUtil.moveFilesOnlyFromFolderToFolder(singleCutFile.getParentFile(), folderForSplitedFile);
            }

            EngineSplitAnalyze.setSs(ss);
            EngineSplitAnalyze.samplesCountPlusOne();
            ss += Settings.getSampleLengthInSec();

            statisticsInfo.addOneCreated();
        }
    }

    private File createFolderForSplitedFile(File srcVideo, File baseOfSamplesFolder) {
        String nameWithoutExtension = FilesUtil.getFileNameWithoutExtension(srcVideo);
        String folderName = StringUtil.getWithAllowedSymbols(nameWithoutExtension);

        File folder = new File(baseOfSamplesFolder + File.separator + folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    private void writeAvgPercentInTxt(File singleCutFile, int avgPercent) {
        File fileWithAvgPercent = new File(singleCutFile.getParentFile() + File.separator + avgPercent + "-" + Settings.AVG_PERCENT_FILE_NAME);
        TextOnFile.addTextInFile(fileWithAvgPercent, String.valueOf(avgPercent));
    }

    private void removeUnnecessaryFrames(ArrayList<File> framesForDelete) {
        for (File file : framesForDelete) {
            file.delete();
        }
    }

    private boolean isPercentFileExist(File singleCutFile) {
        ArrayList<File> allFiles = FilesUtil.getFiles(singleCutFile);
        for (File file : allFiles) {
            if (file.getName().contains(Settings.AVG_PERCENT_FILE_NAME)) {
                return true;
            }
        }
        return false;
    }

}
