/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine;

import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.service.splitanalyzeengine.util.CutFileUtil;
import ua.org.smit.amvsampler.service.splitanalyzeengine.util.FramesUtil;
import ua.org.smit.amvsampler.util.FilesUtil;

/**
 *
 * @author smit
 */
class FramesExtractor {

    private File srcVideo;
    private File folderForSplitedFile;
    private int ss;
    private int sampleLengthInSec;
    private ArrayList<File> frames;
    private File singleCutFile;

    private static final Logger log = LogManager.getLogger(FramesExtractor.class);

    boolean execute() {

        if (Settings.isUseRamDisk()) {
            singleCutFile = new CutFileUtil().create(
                    srcVideo,
                    new File(Settings.getRamDisk() + File.separator + FilesUtil.getFileNameWithoutExtension(srcVideo)),
                    ss,
                    Settings.getSampleLengthInSec());
        } else {
            singleCutFile = new CutFileUtil().create(srcVideo, folderForSplitedFile, ss, Settings.getSampleLengthInSec());
        }

        if (!singleCutFile.exists()) {
            log.info("Cant cut file '" + singleCutFile.getName() + "', ss=" + ss + "! BREAK!");
            return false;
        }

        frames = new FramesUtil().create(singleCutFile, Settings.getResolutionForGifAndAnalyzing(), Settings.getImgExtensionForSpliting());
        if (frames.size() == 0) {
            log.info("Small size of frames '" + frames.size() + "'! BREAK!");

            FilesUtil.deleteFolderWithFiles(singleCutFile.getParentFile());

            return false;
        }

        return true;
    }

    ArrayList<File> getFrames() {
        return frames;
    }

    File getSingleCutFile() {
        return singleCutFile;
    }

    void setSrcVideo(File srcVideo) {
        this.srcVideo = srcVideo;
    }

    void setFolderForSpletedFiles(File folderForSplitedFile) {
        this.folderForSplitedFile = folderForSplitedFile;
    }

    void setSs(int ss) {
        this.ss = ss;
    }

    void setSampleLength(int sampleLengthInSec) {
        this.sampleLengthInSec = sampleLengthInSec;
    }

}
