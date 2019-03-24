/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.encodersamples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.settings.Resolution;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.service.splitanalyzeengine.analyze.Analyze;
import ua.org.smit.amvsampler.util.Ffmpeg;
import ua.org.smit.amvsampler.util.FilesUtil;
import ua.org.smit.amvsampler.util.SorfFrames;

/**
 *
 * @author smit
 */
public class SamplesEncoder {
    
    private static final Logger log = LogManager.getLogger(SamplesEncoder.class);

    public ArrayList<File> encode(ArrayList<File> samples, boolean removeDublicates) {
        ArrayList<File> resutlSamples = new ArrayList();
        for (File sample : samples){
            resutlSamples.add(encode(sample, removeDublicates));
        }
        return resutlSamples;
    }

    public File encode(File sample, boolean removeDublicates) {
        log.info("Encode sample '" + sample + "', removeDublicates = '" + removeDublicates + "'");
        
        File tmpFolder = new File("tmp" + File.separator + FilesUtil.getFileNameWithoutExtension(sample));
        if (tmpFolder.exists()) {
            clean(tmpFolder);
        }
        tmpFolder.mkdirs();
        tmpFolder.setWritable(true);
        
        new Ffmpeg().splitToImages(sample, tmpFolder, "bmp");
        
        if (removeDublicates){
            removeDublicateFramesFromSample(tmpFolder);
        }
        
        File mp4 = new Ffmpeg().makeMp4FromImages(
                tmpFolder, 
                new File("tmp" + File.separator + sample.getName()), 
                Settings.getCrf());
        
        clean(tmpFolder);
        
        return mp4;
    }
    
    private Resolution getResolution(File image){
        try {
            BufferedImage bimg = ImageIO.read(image);
            return new Resolution(bimg.getWidth(), bimg.getHeight());
        } catch (IOException ex) {
            log.error(ex);
        }
        throw new RuntimeException("Cannot determine resolution for img: " + image);
    }

    private ArrayList<File> find(ArrayList<File> frames, List<Integer> percents, int avgPercent) {
        ArrayList<File> uniqueFrames = new ArrayList();
        log.info("Min percent for uniqe = " + avgPercent);
        
        for (int i = 1; i < frames.size(); i++){ // first frame always have 0 percent. Need leave him
//            log.info(frames.get(i) + " - " + percents.get(i));
            if (isFrameDublicate(percents.get(i), avgPercent)){
//                log.info("DEL - " + percents.get(i));
                frames.get(i).delete();
            } else {
                uniqueFrames.add(frames.get(i));
            }
        }
        
        return uniqueFrames;
    }

    private void renameFramesBySort(ArrayList<File> uniqueFrames) {
        int frameName = 0;
        
        for (File frame : uniqueFrames){
            frameName++;
            File newFrame = new File(frame.getParentFile()+ File.separator + String.valueOf(frameName) + ".bmp");
//            log.info(frame + " rename to " + newFrame);
            if (!newFrame.getName().equals(frame.getName())){
                frame.renameTo(newFrame);
                frame.delete();
            }
        }
    }

    private void removeDublicateFramesFromSample(File tmpFolder) {
        ArrayList<File> frames = FilesUtil.getFiles(tmpFolder);
        frames = SorfFrames.byIntegerName(frames);
        Resolution resolution = getResolution(frames.get(0));
        
        Analyze analyze = new Analyze();
        int avgPercent = analyze.getAvgPercent(frames, resolution);
        List<Integer> percents = analyze.getPercents();
        
        ArrayList<File> uniqueFrames = find(frames, percents, avgPercent);
        
        renameFramesBySort(uniqueFrames);
    }

    private boolean isFrameDublicate(int framePercent, int avgPercent) {
        return framePercent < ( avgPercent / 2 );
    }

    private void clean(File folder) {
        FilesUtil.deleteFolderWithFiles(folder);
    }
    
}
