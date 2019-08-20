package ua.org.smit.amvsampler.service.splitanalyzeengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
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
        log.info("--------------------------------------------------------------"
                + "\n=== Start cut and analyze: " + srcVideo);

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
            File gifFile = new GifUtil().create(singleCutFile, Settings.getImgExtensionForSpliting());
            setGifToRam(gifFile);

            removeTmpFiles(frames, singleCutFile.getParentFile(), folderForSplitedFile);
            setLastInfo(ss);

            ss += Settings.getSampleLengthInSec();
        }
    }

    private void removeTmpFiles(File srcVideoTmp) {
        log.debug("Remove tmp files.");
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
        log.debug("Creating folder for splited file.");
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
        log.debug("Write avg percent. " + avgPercent +"% File - " + fileWithAvgPercent);
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

    private void setGifToRam(File gifFile) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(gifFile);
            byte[] fileBytes = IOUtils.toByteArray(inputStream);
            EngineSplitAnalyze.setLastGif(fileBytes);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
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
