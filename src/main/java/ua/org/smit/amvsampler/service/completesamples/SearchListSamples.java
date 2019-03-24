/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.completesamples;

import java.util.ArrayList;

/**
 *
 * @author smit
 */
class SearchListSamples {

    static boolean isFound(Sample needFound, ArrayList<Sample> samplesLIst) {
        for (Sample sample : samplesLIst){
            if (sample.getSs() == needFound.getSs()){
                return true;
            }
        }
        return false;
    }
    
}
