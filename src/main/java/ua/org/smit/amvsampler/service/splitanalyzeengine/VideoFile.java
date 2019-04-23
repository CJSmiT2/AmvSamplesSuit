package ua.org.smit.amvsampler.service.splitanalyzeengine;

import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.CompleteAndQueueListsImpl;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.service.splitanalyzeengine.analyze.Analyze;
import ua.org.smit.amvsampler.service.splitanalyzeengine.util.GifUtil;
import ua.org.smit.amvsampler.util.FilesUtil;
import ua.org.smit.amvsampler.util.StringUtil;
import ua.org.smit.amvsampler.util.TextOnFile;

class VideoFile {

    private static final Logger log = LogManager.getLogger(VideoFile.class);

    void createSeparateFilesAndAnalyzeIt(File srcVideo) {
        long time = System.currentTimeMillis();
        log.info("Start cut and analyze: " + srcVideo);

        File srcVideoTmp = TmpFile.copy(srcVideo);

        execute(srcVideoTmp, createFolderForSplitedFile(srcVideo));

        removeTmpFiles(srcVideoTmp);
        finalization(srcVideo);

        log.info("Complete cut and analyze (time " + ((System.currentTimeMillis() - time) / 1000) + " sec): " + srcVideo);
    }

    private void execute(File srcVideo, File folderForSplitedFile) {
        int ss = 0;
        while (true) {

            if (EngineSplitAnalyze.isCancelSpliting()) {
                log.info("Cut stoped by cancel!");
                break;
            }

            FramesExtractor framesExtractor = new FramesExtractor();
            boolean result = framesExtractor.extract(
                    srcVideo,
                    folderForSplitedFile,
                    ss,
                    Settings.getSampleLengthInSec());

            if (!result) {
                break;
            }

            File singleCutFile = framesExtractor.getSingleCutFile();
            ArrayList<File> frames = framesExtractor.getFrames();

            createAvgPercentOfCutedFile(singleCutFile, frames);
            createGifFile(singleCutFile);

            removeTmpFiles(frames, singleCutFile.getParentFile(), folderForSplitedFile);
            setLastInfo(ss);

            ss += Settings.getSampleLengthInSec();
        }
    }

    private void removeTmpFiles(File srcVideoTmp) {
        srcVideoTmp.delete();
        if (Settings.isUseRamDisk()) {
            FilesUtil.deleteFolderWithFiles(srcVideoTmp.getParentFile());
        }
    }

    private void finalization(File srcVideo) {
        EngineSplitAnalyze.resetStatusValues();
        if (!EngineSplitAnalyze.isCancelSpliting()) {
            new CompleteAndQueueListsImpl().moveToCompleteList(srcVideo);
        }
        EngineSplitAnalyze.setCancelSpliting(false);
    }

    private File createFolderForSplitedFile(File srcVideo) {
        String nameWithoutExtension = FilesUtil.getFileNameWithoutExtension(srcVideo);
        String folderName = StringUtil.getWithAllowedSymbols(nameWithoutExtension);

        File folder = new File(Settings.getBaseOfSamplesFolder() + File.separator + folderName);
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

    private void createAvgPercentOfCutedFile(File singleCutFile, ArrayList<File> frames) {
        int avgPercent = new Analyze().getAvgPercent(frames, Settings.getResolutionForGifAndAnalyzing());
        writeAvgPercentInTxt(singleCutFile, avgPercent);
    }

    private void createGifFile(File singleCutFile) {
        File gifFile = new GifUtil().create(singleCutFile, Settings.getImgExtensionForSpliting());
        EngineSplitAnalyze.setLastGif(gifFile);
    }

    private void removeTmpFiles(ArrayList<File> frames, File parentFile, File folderForSplitedFile) {
        removeUnnecessaryFrames(frames);

        if (Settings.isUseRamDisk()) {
            FilesUtil.moveFilesOnlyFromFolderToFolder(parentFile, folderForSplitedFile);
        }
    }

    private void setLastInfo(int ss) {
        EngineSplitAnalyze.setSs(ss);
        EngineSplitAnalyze.samplesCountPlusOne();
    }

}
