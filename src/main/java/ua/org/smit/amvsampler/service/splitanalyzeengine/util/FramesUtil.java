/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine.util;

import java.io.File;
import java.util.ArrayList;
import ua.org.smit.amvsampler.service.settings.Resolution;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.Ffmpeg;
import ua.org.smit.amvsampler.util.FilesUtil;

/**
 *
 * @author smit
 */
public class FramesUtil {

    public ArrayList<File> create(File singleCutFile, Resolution resolution, String imgExtensionForSpliting) {

        File framesFolder = new File(singleCutFile.getParentFile() + File.separator + "frames");
        framesFolder.mkdir();

        ArrayList<File> frames = FilesUtil.getFiles(framesFolder);
        if (frames.size() < Settings.getFramesCountInSample() - 1) {

            removeFrames(frames);

            return new Ffmpeg().splitToImages(
                    singleCutFile,
                    framesFolder,
                    resolution,
                    imgExtensionForSpliting);
        } else {
            return frames;
        }
    }

    private void removeFrames(ArrayList<File> frames) {
        for (File frame : frames) {
            frame.delete();
        }
    }
}
