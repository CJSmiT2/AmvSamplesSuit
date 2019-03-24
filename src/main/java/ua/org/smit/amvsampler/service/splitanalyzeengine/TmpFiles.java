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
class TmpFiles {
    
    private static final Logger log = LogManager.getLogger(TmpFiles.class);

    static File copy(File srcVideo) {
        
        String extension = FilesUtil.getFileExtension(srcVideo);
        String oldFileName = FilesUtil.getFileNameWithoutExtension(srcVideo);
        String fileName = StringUtil.getWithAllowedSymbols(oldFileName) + "." + extension;
        
        File destFile = new File(Settings.getBaseOfSamplesFolder() + File.separator + fileName);
        if (!destFile.exists()){
            log.info("Copy:\nsrc = " + srcVideo + "\ntmp = " + destFile);
            FilesUtil.copy(srcVideo, destFile);
            log.info("Copy is complete!");
        }
        
        if (!destFile.exists()) {
            throw new RuntimeException(destFile + " NOT EXIST!");
        }
        
        return destFile;
    }
    
}
