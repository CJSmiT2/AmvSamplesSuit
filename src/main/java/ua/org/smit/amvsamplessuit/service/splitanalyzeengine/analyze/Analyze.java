/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.splitanalyzeengine.analyze;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsamplessuit.service.settings.Resolution;

/**
 *
 * @author smit
 */
public class Analyze {
    
    private final List<Integer> percents = new ArrayList();
    
    private static final Logger log = LogManager.getLogger(Analyze.class);

    public int getAvgPercent(ArrayList<File> frames, Resolution resolution) {
        percents.clear();
        for (File oneFrame : frames){
            RgbFrame frame = ImageToNumbers.toFrame(oneFrame, resolution.width, resolution.height);
            int percent = HardAlgoritm.result(frame, resolution.width, resolution.height);
            percents.add(percent);
        }
        int avg = calculateAvgPercent(percents);
        log.info("AVG = " + avg + "%");
        return avg;
    }

    public List<Integer> getPercents() {
        return percents;
    }
    
    
    
    private int calculateAvgPercent(List<Integer> percents) {
        int summ = 0;
        for (Integer percent : percents){
            summ += percent;
        }
        return summ / percents.size();
    }
    
}
