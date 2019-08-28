package ua.org.smit.amvsampler;

import ua.org.smit.amvsampler.recompresser.Recompresser;
import ua.org.smit.amvsampler.service.completesamples.CompleteSamplesImpl;
import ua.org.smit.amvsampler.service.completesamples.CompleteSamplesInterface;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.service.settings.Settings;

public class RecompresserSamples {
    
    public static void main(String[] args) {
        Settings settings = new Settings();
        CompleteSamplesInterface completeSamples = new CompleteSamplesImpl();
        Recompresser recompresser = new Recompresser();
       
       for (Sample sample : completeSamples.getNotRecompressedSamples()){
            boolean result = recompresser.recompress(sample);
            if (!result){
                throw new RuntimeException("Err of recompression: " + sample);
            }
            completeSamples.markSampleAsRecompressed(sample);
       }
    }
    
}
