/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.completesamples;

/**
 *
 * @author smit
 */
public class Limits {
    private String folderName;
    private int minAvgPercent;
    private int ssStart;
    private int ssEnd;

    public Limits(String folderName, int minAvgPercent, String rage) {
        this.folderName = folderName;
        this.minAvgPercent = minAvgPercent;
        ssStart = Integer.valueOf(rage.split("-")[0]);
        ssEnd = Integer.valueOf(rage.split("-")[1]);
    }

    public int getSsStart() {
        return ssStart;
    }

    public void setSsStart(int ssStart) {
        this.ssStart = ssStart;
    }

    public int getSsEnd() {
        return ssEnd;
    }

    public void setSsEnd(int ssEnd) {
        this.ssEnd = ssEnd;
    }
    
    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getMinAvgPercent() {
        return minAvgPercent;
    }

    public void setMinAvgPercent(int minAvgPercent) {
        this.minAvgPercent = minAvgPercent;
    }
    
    public String getRageByString() {
        return ssStart + "-" + ssEnd;
    }

    @Override
    public String toString() {
        return "Limits{" + "folderName=" + folderName + ", minAvgPercent=" + minAvgPercent + ", ssStart=" + ssStart + ", ssEnd=" + ssEnd + '}';
    }

    

    
}
