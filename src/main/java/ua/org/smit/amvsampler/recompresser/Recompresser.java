package ua.org.smit.amvsampler.recompresser;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.service.encodersamples.SamplesEncoder;
import ua.org.smit.amvsampler.util.FilesUtil;

public class Recompresser {
    private static final Logger log = LogManager.getLogger(Recompresser.class);
    private final SamplesEncoder samplesEncoder = new SamplesEncoder();
    
    public boolean recompress(Sample sample){
        log.info("Start recompress: " + sample.getMp4());
        File recompressedFile = samplesEncoder.encode(sample.getMp4(), false);
        log.info("Recompressed file: " + recompressedFile);
        
        if (recompressedFile.exists() && recompressedFile.length() > 0){
            FilesUtil.removeFile(sample.getMp4());
            FilesUtil.copy(recompressedFile, sample.getMp4());
            FilesUtil.removeFile(recompressedFile);
            return true;
        }
        return false;
    }
}
