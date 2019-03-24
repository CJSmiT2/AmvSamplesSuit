/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.completesamples;

import java.io.File;

/**
 *
 * @author smit
 */
public class Sample {
    
    private File parentFolder; // title of anime, series etc.
    private File gif;
    private File mp4;
    private int avgPercent;
    private int ss;

    public File getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(File parentFolder) {
        this.parentFolder = parentFolder;
    }
    
    public File getSsFolder(){
        return new File(parentFolder + File.separator + ss);
    }

    public File getGif() {
        return gif;
    }

    public void setGif(File gif) {
        this.gif = gif;
    }

    public File getMp4() {
        return mp4;
    }

    public void setMp4(File mp4) {
        this.mp4 = mp4;
    }

    public int getAvgPercent() {
        return avgPercent;
    }

    public void setAvgPercent(int avgPercent) {
        this.avgPercent = avgPercent;
    }

    public int getSs() {
        return ss;
    }

    public void setSs(int ss) {
        this.ss = ss;
    }
    
    public String getTime(){
        int h = ss / 3600;
        int m = ss % 3600 / 60;
        int s = ss % 3600 % 60;
        
        String hours = String.valueOf(h);
        if (h < 10){
            hours = "0" + h;
        }
        String mins = String.valueOf(m);
        if (m < 10){
            mins = "0" + m;
        }
        String sec = String.valueOf(s);
        if (s < 10){
            sec = "0" + s;
        }
        
        return hours + ":" + mins + ":" + sec;
        
    }

    @Override
    public String toString() {
        return "Sample{" + "parentFolder=" + parentFolder + ", gif=" + gif + ", mp4=" + mp4 + ", avgPercent=" + avgPercent + ", ss=" + ss + '}';
    }
    
    
    
    
}
