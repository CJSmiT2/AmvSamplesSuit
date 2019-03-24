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
 * @author Администратор
 */
public class TimeCorrectionForFfmpeg {
    
    private static final Logger log = LogManager.getLogger(TimeCorrectionForFfmpeg.class);

    static int determinate(File srcVideoTmp) {
        File tmpFolder = new File(Settings.getBaseOfSamplesFolder() + File.separator + "tmp");
        if (tmpFolder.exists()){
            FilesUtil.deleteFolderWithFiles(tmpFolder);
        }
        
        int adjustedTimeForFfmpeg = 0;
        
        while (true){
            tmpFolder.mkdirs();
            adjustedTimeForFfmpeg++;
            log.info("adjustedTimeForFfmpeg = " + adjustedTimeForFfmpeg);
            
            File singleCutFile = new CutFileUtil().create(srcVideoTmp, tmpFolder, 0, adjustedTimeForFfmpeg);
            ArrayList<File> frames = new FramesUtil().create(singleCutFile, Settings.getResolutionForGifAndAnalyzing(), Settings.getImgExtensionForSpliting());
            if (frames.size() >= Settings.getFramesCountInSample()) {
                break;
            } else {
                log.info(frames.size() + " Need: " + Settings.getFramesCountInSample());
                deleteFrames(frames);
            }
            FilesUtil.deleteFolderWithFiles(tmpFolder);
        }
        
        if (adjustedTimeForFfmpeg != Settings.getSampleLengthInSec()){
            log.info("Samples length adjusted to '" + adjustedTimeForFfmpeg + "sec' "
                    + "(need '" + Settings.getSampleLengthInSec() + "sec')");
        }
        
        return adjustedTimeForFfmpeg;
    }

    private static void deleteFrames(ArrayList<File> frames) {
        for (File file : frames){
            file.delete();
        }
    }
    
}
