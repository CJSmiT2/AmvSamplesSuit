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
import ua.org.smit.amvsampler.service.settings.Resolution;

/**
 *
 * @author smit
 */
public class Ffmpeg {

    private static final Logger log = LogManager.getLogger(Ffmpeg.class);

    public ArrayList<File> splitToImages(File srcFileForSplit, File folderForImages, Resolution resolution, String imgExtension) {
        log.info("Start split To Images");
        validFile(srcFileForSplit);
        validFolder(folderForImages);
        validImgExtension(imgExtension);
        cleanIfNotEmpty(folderForImages);

        String command = "ffmpeg "
                + "-i " + srcFileForSplit + " "
                + "-s " + resolution.width + "x" + resolution.height
                + " " + folderForImages + File.separator + "%d." + imgExtension;
        Console.exec(command);
        return FilesUtil.getFiles(folderForImages);
    }

    public ArrayList<File> splitToImages(File srcFileForSplit, File folderForImages, String imgExtension) {
        log.info("Start split To Images 2");
        validFile(srcFileForSplit);
        validFolder(folderForImages);
        validImgExtension(imgExtension);
        cleanIfNotEmpty(folderForImages);

        String command = "ffmpeg "
                + "-i " + srcFileForSplit + " "
                + " " + folderForImages + File.separator + "%d." + imgExtension;
        Console.exec(command);
        return FilesUtil.getFiles(folderForImages);
    }

    public File cut(File fileSrc, File completedFolder, String sampleFilename, int startSeconds, int time) {
        log.info("Start cut file");
        validFile(fileSrc);
        validFolder(completedFolder);

        File partOfFile = new File(completedFolder + File.separator + sampleFilename);
        deleteIfExist(partOfFile);

        for (int tryStep = 0; tryStep < 10; tryStep++) {
            Console.exec("ffmpeg "
                    + "-ss " + startSeconds + " "
                    + "-t " + time + " "
                    + "-i " + fileSrc + " "
                    + "-map 0:" + tryStep + " "
                    + "-vcodec copy -an " + partOfFile);
            if (partOfFile.exists()) {
                break;
            }
        }

        validFile(partOfFile);

        return partOfFile;
    }

    public File makeGif(File srcFolder, File destFile, String imgExtension) {
        log.info("Start make gif");
        validFolder(srcFolder);
        validImgExtension(imgExtension);
        deleteIfExist(destFile);

        String command = "ffmpeg -f image2 -i " + srcFolder + File.separator + "%d." + imgExtension + " " + destFile + "";
        Console.exec(command);

        validFile(destFile);
        return destFile;
    }

    public File makeMp4FromImages(File srcFolder, File destFile, int crf) {
        log.info("Start make mp4");
        validFolder(srcFolder);
        deleteIfExist(destFile);

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

        validFile(destFile);

        return destFile;
    }

    private void validFile(File file) {
        if (!file.exists()) {
            throw new RuntimeException("Not found: " + file);
        }
    }

    private void validFolder(File folder) {
        if (!folder.exists()) {
            throw new RuntimeException("Not found: " + folder);
        }
        if (!folder.isDirectory()) {
            throw new RuntimeException("Is not folder: " + folder);
        }
    }

    private void validImgExtension(String imgExtension) {
        if (imgExtension.equalsIgnoreCase("bmp")
                || imgExtension.equalsIgnoreCase("png")
                || imgExtension.equalsIgnoreCase("jpg")) {
            return;
        }
        throw new RuntimeException("Is extension not valid: " + imgExtension);
    }

    private void deleteIfExist(File destFile) {
        if (destFile.exists()) {
            destFile.delete();
        }
    }

    private void cleanIfNotEmpty(File folderForImages) {
        for (File file : FilesUtil.getFiles(folderForImages)) {
            file.delete();
        }
    }
}
