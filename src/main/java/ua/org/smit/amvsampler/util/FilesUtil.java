/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author smit
 */
public class FilesUtil {

    private static final Logger log = LogManager.getLogger(FilesUtil.class);

    public static synchronized ArrayList<File> getFiles(File folder) {
        ArrayList<File> filesList = new ArrayList();
        if (!isFolderExistChecking(folder)) {
            return filesList;
        }

        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                filesList.add(listOfFiles[i]);
            }
        }

        return filesList;
    }

    public static synchronized ArrayList<File> getFolders(File folder) {
        ArrayList<File> foldersList = new ArrayList();
        if (!isFolderExistChecking(folder)) {
            return foldersList;
        }

        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                foldersList.add(listOfFiles[i]);
            }
        }

        return foldersList;
    }

    public static synchronized void move(File src, File dest) {
        log.info("\nmove: " + src + "\n  to: " + dest);
        if (!src.exists()) {
            throw new RuntimeException("Fail to move file. Src file not exist! " + src.getAbsolutePath());
        }

        boolean result = src.renameTo(dest);
        if (result & dest.exists()) {
            src.delete();
        } else {
            throw new RuntimeException("Fail to move file.");
        }
    }

    public static synchronized void copy(File source, File dest) {
        if (!source.exists()) {
            throw new RuntimeException("Fail to copy file. Src file not exist! " + source.getAbsolutePath());
        }
        if (dest.exists()) {
            log.info("WARNING! The file will be rewritten! " + dest.getAbsolutePath());
        }

        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {

            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                sourceChannel.close();
                destChannel.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }

    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            throw new RuntimeException("Extension not found in file name! " + file.getName());
        }
    }

    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            throw new RuntimeException("Extension not found in file name! " + fileName);
        }
    }

    public static String getFileNameWithoutExtension(File file) {
        return file.getName().replaceFirst("[.][^.]+$", "");
    }

    public static synchronized void deleteFolderWithFiles(File file) {
        for (File childFile : file.listFiles()) {
            if (childFile.isDirectory()) {
                deleteFolderWithFiles(childFile);
            } else {
                if (!childFile.delete()) {
                    try {
                        throw new IOException();
                    } catch (IOException ex) {
                        log.error(ex);
                    }
                }
            }
        }

        if (!file.delete()) {
            try {
                throw new IOException();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }

    public static long getFilesSize(ArrayList<File> files) {
        long size = 0;
        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static ArrayList<File> getAllFilesRecurcive(File root) {
        ArrayList<File> files = new ArrayList();

        File[] list = root.listFiles();

        if (list == null) {
            return files;
        }

        for (File file : list) {
            if (file.isDirectory()) {
                ArrayList<File> filesInDir = getAllFilesRecurcive(file);
                files.addAll(filesInDir);
            } else {
                files.add(file);
            }
        }

        return files;
    }

    public static ArrayList<File> getAllFoldersNotRecursive(File folder) {
        ArrayList<File> folders = new ArrayList();

        File[] list = folder.listFiles();

        if (list == null) {
            return folders;
        }

        for (File file : list) {
            if (file.isDirectory()) {
                folders.add(file);
            }
        }

        return folders;
    }

    public static synchronized void makeEmptyFile(File textFile) {
//        removeFile(textFile);
        try {
            textFile.createNewFile();
        } catch (IOException ex) {
            new RuntimeException(ex);
        }
    }

    public static synchronized void removeFile(File file) {
        log.debug("REMOVE: " + file);
        if (file.exists()) {
            file.delete();
        }
    }

    private static synchronized boolean isFolderExistChecking(File folder) {
        if (!folder.exists() || !folder.isDirectory()) {
            return false;
        }
        return true;
    }

    public static synchronized void moveFilesOnlyFromFolderToFolder(File src, File dest) {
        ArrayList<File> files = FilesUtil.getFiles(src);
        for (File file : files) {

            String path = file.getParentFile().getAbsolutePath(); // mnt/ramdisk/BeatriceRaws_Durararax2_Ketsu_01_BDRip_1920x1080_x264_FLAC/0
            String[] splited = path.split(File.separator);
            int ss = Integer.valueOf(splited[splited.length - 1]); // 0

            File destSsFolder = new File(dest + File.separator + ss);
            destSsFolder.mkdir();
            FilesUtil.copy(file, new File(destSsFolder + File.separator + file.getName()));
            file.delete();
        }
    }

}
