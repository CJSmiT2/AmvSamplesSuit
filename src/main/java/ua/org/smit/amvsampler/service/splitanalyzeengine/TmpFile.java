/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.FilesUtil;
import ua.org.smit.amvsampler.util.StringUtil;

/**
 *
 * @author smit
 */
class TmpFile {

    private static final Logger log = LogManager.getLogger(TmpFile.class);

    static File copy(File srcVideo) {

        File destFile = createDestFile(createTmpFileName(srcVideo), srcVideo);
        copySrcToDest(srcVideo, destFile);

        if (!destFile.exists()) {
            throw new RuntimeException("File not exist! " + destFile);
        }

        return destFile;
    }

    private static String createTmpFileName(File srcVideo) {
        String extension = FilesUtil.getFileExtension(srcVideo);
        String oldFileName = FilesUtil.getFileNameWithoutExtension(srcVideo);
        return StringUtil.getWithAllowedSymbols(oldFileName) + "." + extension;
    }

    private static File createDestFile(String fileName, File srcVideo) {
        if (Settings.isUseRamDisk() && srcVideo.length() <= 4000000000l) { // 4Gb
            log.debug("Use RAM disk");
            return new File(Settings.getRamDisk() + File.separator + fileName);
        } else {
            log.debug("Use HDD disk");
            return new File(Settings.getBaseOfSamplesFolder() + File.separator + fileName);
        }
    }

    private static void copySrcToDest(File srcVideo, File destFile) {
        if (!destFile.exists() || (srcVideo.length() != destFile.length())) {
            log.info("Copy:\nsrc = " + srcVideo + "\ntmp = " + destFile);

            long time = System.currentTimeMillis();
            FilesUtil.copy(srcVideo, destFile);

            log.info("Copy is complete! Time: " + ((System.currentTimeMillis() - time) / 1000) + "sec");
        } else {
            log.info("File not copied, because already exist! " + destFile);
        }
    }

}
