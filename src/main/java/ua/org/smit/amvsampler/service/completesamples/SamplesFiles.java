/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.completesamples;

import java.io.File;
import ua.org.smit.amvsampler.service.settings.Settings;

/**
 *
 * @author smit
 */
class SamplesFiles {
    
    static File getFolderFromBaseByName(String folderName){
        return new File(Settings.getBaseOfSamplesFolder() + File.separator + folderName);
    }
    
    static File getAvgPercentFile(String folderName){
        return new File(getFolderFromBaseByName(folderName) + File.separator + Settings.AVG_PERCENT_LIMIT_FILE_NAME);
    }
    
    static File getAvgPercentFile(File folderWithSample){
        return new File(folderWithSample + File.separator + Settings.AVG_PERCENT_LIMIT_FILE_NAME);
    }
    
    static File getRageFile(String folderName){
        return new File(getFolderFromBaseByName(folderName) + File.separator + Settings.RAGE_FILE_NAME);
    }
    
    static File getRageFile(File folderWithSample){
        return new File(folderWithSample + File.separator + Settings.RAGE_FILE_NAME);
    }

    
}
