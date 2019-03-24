/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.util;

import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author smit
 */
public class SorfFrames {
    
    private static final Logger log = LogManager.getLogger(SorfFrames.class);
    
    public static ArrayList<File> byIntegerName(ArrayList<File> frames){
        ArrayList<File> sorted = new ArrayList();
        if (!frames.isEmpty()){
            int frameName = 0;
            while(!frames.isEmpty()){
                frameName++;
                File frame = getByName(frameName, frames);
                sorted.add(frame);
                frames.remove(frame);
            }
            
        } else {
            log.error("Frames array for sorting is empty! Return empty list!");
        }
        return sorted;
    }

    private static File getByName(int fileSearch, ArrayList<File> frames) {
        for (File frame : frames){
            String fileName = FilesUtil.getFileNameWithoutExtension(frame);
            int fileNameInt = Integer.valueOf(fileName);
            if (fileNameInt == fileSearch){
                return frame;
            }
        }
        throw new RuntimeException("Cannot fount frame by name '" + fileSearch + ".bmp'");
    }
}
