/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.org.smit.amvsampler.util.TextOnFile;

/**
 *
 * @author smit
 */
public class TxtFileService {

    public static ArrayList<String> getAll(File txtFile) {
        if (!txtFile.exists()) {
            try {
                txtFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(TxtFileService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return TextOnFile.readByLine(txtFile);
    }

    public static void add(File txtFile, File videoFile) {
        TextOnFile.addTextInFile(txtFile, videoFile.getName());
    }

    public static void reWrite(File txtFile, ArrayList<String> lines) {
        TextOnFile.reWriteTextInFile(txtFile, lines);
    }

    public static boolean exist(File txtFile, String videoFileName) {
        for (String fileNameFromQueue : getAll(txtFile)) {
            if (videoFileName.equals(fileNameFromQueue)) {
                return true;
            }
        }
        return false;
    }

    public static void remove(File txtFile, String videoFileName) {
        TextOnFile.removeTextLineFromFile(txtFile, videoFileName);
    }

    public static boolean isEmpty(File txtFile) {
        return getAll(txtFile).isEmpty();
    }

    public static String getFirst(File txtFile) {
        return getAll(txtFile).get(0);
    }

}
