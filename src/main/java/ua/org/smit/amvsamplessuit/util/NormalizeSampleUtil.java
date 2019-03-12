/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.util;

import java.util.ArrayList;
import ua.org.smit.amvsamplessuit.service.completesamples.Sample;

/**
 *
 * @author smit
 */
public class NormalizeSampleUtil {

    public static void setMaxAvgPercentLimit(ArrayList<Sample> samples, int limit) {
        for (int i = 0 ; i < samples.size(); i++){
            if (samples.get(i).getAvgPercent() < 0){
                int avgPercent = samples.get(i).getAvgPercent();
                samples.get(i).setAvgPercent(avgPercent * (-1));
            }
            if (samples.get(i).getAvgPercent() > limit){
                samples.get(i).setAvgPercent(limit);
            }
        }
    }
    
}
