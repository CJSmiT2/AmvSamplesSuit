/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.groups;

import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.settings.Settings;

/**
 *
 * @author smit
 */
class PathUtil {

    private static final Logger log = LogManager.getLogger(PathUtil.class);

    static void convertToDirect(ArrayList<String> samplesPaths) {
        for (int i = 0; i < samplesPaths.size(); i++) {
            String relativePath = samplesPaths.get(i);
            String direct = convertToDirect(relativePath);
            samplesPaths.set(i, direct);
        }
    }

    static void convertToRelative(ArrayList<String> samplesPaths) {
        for (int i = 0; i < samplesPaths.size(); i++) {
            String direct = samplesPaths.get(i);
            String relativePath = convertToRelative(direct);
            samplesPaths.set(i, relativePath);
        }
    }

    static String convertToRelative(String sampleFile) {
        String baseOfSamples = Settings.getBaseOfSamplesFolder().getAbsolutePath();
        String path = sampleFile.substring(baseOfSamples.length() + 1);
//        log.info(sampleFile + " to " + path);
        return path;
    }

    static String convertToDirect(String relativePath) {
        String path = Settings.getBaseOfSamplesFolder() + File.separator + relativePath;
//        log.info(relativePath + " to " + path);
        return path;
    }

}
