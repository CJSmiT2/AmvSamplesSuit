/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.util;

import ua.org.smit.amvsamplessuit.service.settings.Resolution;
import java.io.File;

/**
 *
 * @author smit
 */
public class Ffmpeg{
    
    public void splitToImages(File file, File folderForImages, Resolution resolution, String imgExtension) {
        String command = "ffmpeg "
                + "-i " + file  + " "
                + "-s " + resolution.width + "x" + resolution.height 
                + " " + folderForImages + File.separator + "%d." + imgExtension;
        Console.exec(command);
    }
    
    public void splitToImages(File file, File folderForImages, String imgExtension) {
        String command = "ffmpeg "
                + "-i " + file  + " "
                + " " + folderForImages + File.separator + "%d." + imgExtension;
        Console.exec(command);
    }

    public void cut(File fileSrc, File completedFolder, String sampleFilename, int startSeconds, int time) {
        String command = "ffmpeg "
                + "-ss " + startSeconds + " "
                + "-t " + time + " "
                + "-i " + fileSrc + " "
                + "-map 0:0 "
                + "-vcodec copy -an " + completedFolder + File.separator + sampleFilename;
        Console.exec(command);
    }
    
    public void makeGif(File srcFolder, File destFile, String imgExtension) {
        String command = "ffmpeg -f image2 -i " + srcFolder + File.separator + "%d." + imgExtension + " " + destFile + "";
        Console.exec(command);
    }
    
    public void makeMp4FromImages(File srcFolder, File destFile, int crf){
        String command = "ffmpeg "
                + "-framerate 24 "
                + "-i " + srcFolder + File.separator + "%d.bmp "
                + "-c:v libx264 "
                + "-preset slower "
                + "-tune animation "
                + "-crf " + crf + " "
                + "-pix_fmt yuv420p "
                + "-profile:v high -level 4.2 " + destFile;
        Console.exec(command);
    }

}
