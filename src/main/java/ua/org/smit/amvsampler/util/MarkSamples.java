package ua.org.smit.amvsampler.util;

import java.util.ArrayList;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.service.exportsamples.SampleExport;

public class MarkSamples {

    public void marksamplesThatAreInTheFolder(
            ArrayList<Sample> samples,
            ArrayList<SampleExport> samplesFromExportFolder) {

        for (int i = 0; i < samples.size(); i++) {
            for (int y = 0; y < samplesFromExportFolder.size(); y++) {
                String title1 = samples.get(i).getTitle();
                int ss1 = samples.get(i).getSs();
                
                String title2 = samplesFromExportFolder.get(y).getTitle();
                int ss2 = samplesFromExportFolder.get(y).getSS();
                
                if (title1.equals(title2)){
                    if (ss1 == ss2){
                        samples.get(i).setIxistInExportFolder(true);
                    }
                }
            }
        }

    }

}
