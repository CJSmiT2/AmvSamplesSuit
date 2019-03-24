/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.encodersamples;

import java.io.File;
import static java.lang.Thread.sleep;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.FilesUtil;

/**
 *
 * @author smit
 */
public class ThreadExportEncodeSamples implements Runnable {
    
    private static final Logger log = LogManager.getLogger(ThreadExportEncodeSamples.class);

    @Override
    public void run() {
        log.info("ThreadExportEncodeSamples is STARTED!");
        
        while (true){
            
//            log.info("Export samples array size = " + ExportEncodeSamplesQueue.samples.size());
            
            if (!ExportEncodeSamplesQueue.samples.isEmpty()){
                
                File sample = ExportEncodeSamplesQueue.samples.get(0);
                
                SamplesEncoder samplesEncoder = new SamplesEncoder();
                File encodedSample = samplesEncoder.encode(sample, false);
                File dest = new File(Settings.getExportFolder() + File.separator + encodedSample.getName());
                if (dest.exists()) {
                    dest.delete();
                }
                FilesUtil.copy(encodedSample, dest);
                encodedSample.delete();

                
                
                if (Settings.isCreateSampleWithoutDublicatesFrames()){
                    SamplesEncoder samplesEncoder2 = new SamplesEncoder();
                    File encodedSample2 = samplesEncoder2.encode(sample, true);
                    File dest2 = new File(Settings.getExportFolder() + File.separator + FilesUtil.getFileNameWithoutExtension(encodedSample2) + "_no_dubl.mp4");
                    if (dest2.exists()) {
                        dest2.delete();
                    }
                    FilesUtil.copy(encodedSample2, dest2);
                    encodedSample2.delete();
                }
                
                ExportEncodeSamplesQueue.samples.remove(sample);
            }
            
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                log.error(ex);
            }
        }
    }
    
}
