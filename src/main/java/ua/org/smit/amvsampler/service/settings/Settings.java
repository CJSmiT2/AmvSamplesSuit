/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.util.Base64Util;
import ua.org.smit.amvsampler.util.TextOnFile;

/**
 *
 * @author smit
 */
public class Settings {

    private static final File SETTINGS_FILE = new File("config.properties");

    private static String nickName;
    private static String uuid;

    private static String imgExtensionForSpliting = "bmp";
    private static int fps = 24;
    private static int sampleLengthInSec = 5;
    private static int framesCountInSample = fps * sampleLengthInSec;
    private static int crf = 10;
    private static Resolution resolutionForGifAndAnalyzing = new Resolution(128, 72);
    private static File srcFolder;
    private static File baseOfSamplesFolder;
    private static File exportFolder;
    private static File ramDisk;
    private static boolean localhostOnly = true;
    private static boolean needCorrectionsOfSampleLength = true;
    private static boolean createSampleWithoutDublicatesFrames = false;
    private static boolean useRamDisk = false;

    public static final String AVG_PERCENT_FILE_NAME = "avgPercent.txt";
    public static final String RAGE_FILE_NAME = "rage.txt";
    public static final String AVG_PERCENT_LIMIT_FILE_NAME = "avgPercentLimitForSamplesView.txt";

    public static final File GROUPS_BY_TITLES_FOLDER = new File("groups_by_titles");
    public static final File GROUPS_BY_SAMPLES_FOLDER = new File("groups_by_samples");

    private static final Logger log = LogManager.getLogger(Settings.class);

    public Settings() {
        if (isSettingsFileExist()) {
            loadSettings();
        }
    }

    public static boolean isSettingsFileExist() {
        return SETTINGS_FILE.exists();
    }

    public static boolean isValid() {
        Set<File> samplesSet = new HashSet();
        samplesSet.add(Settings.getSrcFolder());
        samplesSet.add(Settings.getBaseOfSamplesFolder());
        samplesSet.add(Settings.getExportFolder());

        if (samplesSet.size() == 3) {
            return true;
        }
        return false;
    }

    private static void loadSettings() {
        log.info("Load setings...");
        try {
            Properties property = new Properties();
            property.load(new FileInputStream(SETTINGS_FILE));

            nickName = property.getProperty("nickName");
            uuid = property.getProperty("uuid");

            imgExtensionForSpliting = property.getProperty("imgExtensionForSpliting");
            fps = Integer.valueOf(property.getProperty("fps"));
            sampleLengthInSec = Integer.valueOf(property.getProperty("sampleLengthInSec"));
            crf = Integer.valueOf(property.getProperty("crf"));

            int width = Integer.valueOf(property.getProperty("resolution.width"));
            int height = Integer.valueOf(property.getProperty("resolution.height"));
            resolutionForGifAndAnalyzing = new Resolution(width, height);

            Base64Util base64 = new Base64Util();
            srcFolder = new File(base64.decode(property.getProperty("srcFolder")));
            baseOfSamplesFolder = new File(base64.decode(property.getProperty("baseOfSamplesFolder")));
            exportFolder = new File(base64.decode(property.getProperty("exportFolder")));
            ramDisk = new File(base64.decode(property.getProperty("ramDisk")));

            localhostOnly = Boolean.valueOf(property.getProperty("localhostOnly"));
            needCorrectionsOfSampleLength = Boolean.valueOf(property.getProperty("needCorrectionsOfSampleLength"));
            createSampleWithoutDublicatesFrames = Boolean.valueOf(property.getProperty("createSampleWithoutDublicatesFrames"));
            useRamDisk = Boolean.valueOf(property.getProperty("useRamDisk"));

        } catch (IOException ex) {
            log.error("Error load '" + SETTINGS_FILE + "'!" + ex);
        }
    }

    public static void saveSettingsInFile() {
        log.info("Save setings in file...");

        if (!SETTINGS_FILE.exists()) {
            TextOnFile.makeEmptyFile(SETTINGS_FILE);
        }

        ArrayList<String> params = new ArrayList();
        params.add("nickName = " + nickName);
        params.add("uuid = " + uuid);
        params.add("imgExtensionForSpliting = " + imgExtensionForSpliting);
        params.add("fps = " + fps);
        params.add("sampleLengthInSec = " + sampleLengthInSec);
        params.add("crf = " + crf);
        params.add("resolution.width = " + resolutionForGifAndAnalyzing.width);
        params.add("resolution.height = " + resolutionForGifAndAnalyzing.height);
        params.add("localhostOnly = " + localhostOnly);
        params.add("needCorrectionsOfSampleLength = " + needCorrectionsOfSampleLength);
        params.add("createSampleWithoutDublicatesFrames = " + createSampleWithoutDublicatesFrames);
        params.add("useRamDisk = " + useRamDisk);

        Base64Util base64 = new Base64Util();

        if (srcFolder != null) {
            params.add("srcFolder = " + base64.encode(srcFolder.getAbsolutePath()));
        } else {
            params.add("srcFolder = ");
        }

        if (baseOfSamplesFolder != null) {
            params.add("baseOfSamplesFolder = " + base64.encode(baseOfSamplesFolder.getAbsolutePath()));
        } else {
            params.add("baseOfSamplesFolder = ");
        }

        if (exportFolder != null) {
            params.add("exportFolder = " + base64.encode(exportFolder.getAbsolutePath()));
        } else {
            params.add("exportFolder = ");
        }

        if (ramDisk != null) {
            params.add("ramDisk = " + base64.encode(ramDisk.getAbsolutePath()));
        } else {
            params.add("ramDisk = ");
        }

        log.info("Save in config file: " + params);

        TextOnFile.reWriteTextInFile(SETTINGS_FILE, params);
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        Settings.nickName = nickName;
    }

    public static String getUuid() {
        return uuid;
    }

    public static void setUuid(String uuid) {
        Settings.uuid = uuid;
    }

    public static int getSampleLengthInSec() {
        return sampleLengthInSec;
    }

    public static void setSampleLengthInSec(int sampleLengthInSec) {
        Settings.sampleLengthInSec = sampleLengthInSec;
    }

    public static Resolution getResolutionForGifAndAnalyzing() {
        return resolutionForGifAndAnalyzing;
    }

    public static void setResolutionForGifAndAnalyzing(Resolution resolutionForGifAndAnalyzing) {
        Settings.resolutionForGifAndAnalyzing = resolutionForGifAndAnalyzing;
    }

    public static File getSrcFolder() {
        return srcFolder;
    }

    public static void setSrcFolder(File srcFolder) {
        Settings.srcFolder = srcFolder;
    }

    public static File getBaseOfSamplesFolder() {
        return baseOfSamplesFolder;
    }

    public static void setBaseOfSamplesFolder(File baseOfSamplesFolder) {
        Settings.baseOfSamplesFolder = baseOfSamplesFolder;
    }

    public static File getExportFolder() {
        return exportFolder;
    }

    public static void setExportFolder(File exportFolder) {
        Settings.exportFolder = exportFolder;
    }

    public static boolean isCreateSampleWithoutDublicatesFrames() {
        return createSampleWithoutDublicatesFrames;
    }

    public static boolean isSrcFolderExist() {
        return srcFolder != null && srcFolder.exists() && srcFolder.isDirectory();
    }

    public static boolean isBaseOfSamplesFolderExist() {
        return baseOfSamplesFolder != null && baseOfSamplesFolder.exists() && baseOfSamplesFolder.isDirectory();
    }

    public static boolean isSexportFolderExist() {
        return exportFolder != null && exportFolder.exists() && exportFolder.isDirectory();
    }

    public static boolean isLocalhostOnly() {
        return localhostOnly;
    }

    public static void setLocalhostOnly(boolean localhostOnly) {
        Settings.localhostOnly = localhostOnly;
    }

    public static String getImgExtensionForSpliting() {
        return imgExtensionForSpliting;
    }

    public static int getFramesCountInSample() {
        return framesCountInSample;
    }

    public static boolean isNeedCorrectionOfSampleLength() {
        return needCorrectionsOfSampleLength;
    }

    public static int getCrf() {
        return crf;
    }

    public static File getRamDisk() {
        return ramDisk;
    }

    public static boolean isUseRamDisk() {
        return useRamDisk;
    }

}
