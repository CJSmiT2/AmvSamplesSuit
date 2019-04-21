/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine.util;

import ua.org.smit.amvsampler.util.Ffmpeg;
import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.util.FilesUtil;

/**
 *
 * @author smit
 */
public class GifUtil {

    private static final Logger log = LogManager.getLogger(GifUtil.class);

    public File create(File singleCutFile, String imgExtensionForSpliting) {
        log.info("Make gif: " + singleCutFile);

        File framesFolder = new File(singleCutFile.getParentFile() + File.separator + "frames");
        String gifFileName = FilesUtil.getFileNameWithoutExtension(singleCutFile) + ".gif";

        return new Ffmpeg().makeGif(
                framesFolder,
                new File(singleCutFile.getParentFile() + File.separator + gifFileName),
                imgExtensionForSpliting);

    }
}
