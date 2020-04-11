package ua.org.smit.amvsampler.service.exportsamples;

import java.io.File;

/**
 *
 * @author smit
 */
public class SampleExport {

    private String sampleName;
    private File mp4;
    private File gif;

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public File getMp4() {
        return mp4;
    }

    public void setMp4(File mp4) {
        this.mp4 = mp4;
    }

    public File getGif() {
        return gif;
    }

    public void setGif(File gif) {
        this.gif = gif;
    }

    public int getSS() {
        String endOfName = mp4.getName().split("_ss_")[1];
        String ss = endOfName.split("\\.")[0];
        return Integer.valueOf(ss);
    }

    public String getTitle() {
        return mp4.getName().split("_ss_")[0];
    }

    @Override
    public String toString() {
        return "SampleExport{" + "sampleName=" + sampleName + ", mp4=" + mp4 + ", gif=" + gif + '}';
    }

}
