/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine.util;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.util.Ffmpeg;
import ua.org.smit.amvsampler.util.FilesUtil;

/**
 *
 * @author smit
 */
public class CutFileUtil {
    
    private static final Logger log = LogManager.getLogger(CutFileUtil.class);

    public File create(File srcVideo, File folderForSplitedFile, int ss, int sampleLengthInSec) {
        log.info("Cutting " + srcVideo + " -ss " + ss);
        
        File singleCutFolder = new File(folderForSplitedFile + File.separator + ss);
        makeEmptyFolder(singleCutFolder);
        String singleCutedFilename = ss + "-" + FilesUtil.getFileNameWithoutExtension(srcVideo)+ ".mp4";
        
        File cutFile = new Ffmpeg().cut(
                            srcVideo, 
                            singleCutFolder, 
                            singleCutedFilename,
                            ss, 
                            sampleLengthInSec);
        
        return cutFile;
    }
    
    private void makeEmptyFolder(File folderForSplitedFile) {
        if (folderForSplitedFile.exists()) {
            FilesUtil.deleteFolderWithFiles(folderForSplitedFile);
        }
        folderForSplitedFile.mkdirs();
    }

}
