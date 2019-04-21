/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service;

import ua.org.smit.amvsampler.service.settings.Settings;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import ua.org.smit.amvsampler.util.FilesUtil;

/**
 *
 * @author smit
 */
class FileListsManager {

    private static final File COMPLETED_FILES_LIST = new File("completed_files_list.txt");
    private static final File QUEUE_FILES_LIST = new File("queue_files_list.txt");

    static ArrayList<File> getUnprocessedVideoFiles() {
        ArrayList<File> allFiles = getAllowedVideoFilesFromSrcFolder();
        ArrayList<String> queueFilesNames = TxtFileService.getAll(QUEUE_FILES_LIST);
        ArrayList<String> completedFilesNames = TxtFileService.getAll(COMPLETED_FILES_LIST);

        Iterator<File> iterator = allFiles.iterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (!Check.unprocessed(file, queueFilesNames, completedFilesNames)) {
                iterator.remove();
            }
        }

        return allFiles;
    }

    static ArrayList<File> getAllowedVideoFilesFromSrcFolder() {
        ArrayList<File> files = FilesUtil.getAllFilesRecurcive(Settings.getSrcFolder());
        Iterator<File> iterator = files.iterator();
        while (iterator.hasNext()) {
            File file = iterator.next();
            if (!FilesUtil.getFileExtension(file).equalsIgnoreCase("mp4")
                    && !FilesUtil.getFileExtension(file).equalsIgnoreCase("mkv")) {
                iterator.remove();
            }
        }
        return files;
    }

    static File getByName(String fileName) {
        for (File file : getAllowedVideoFilesFromSrcFolder()) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        throw new RuntimeException("Can't find file '" + fileName + "'!");
    }

    static ArrayList<File> getFilesFromQueue() {
        ArrayList<File> filesInQueue = new ArrayList();
        for (String videoFileName : TxtFileService.getAll(QUEUE_FILES_LIST)) {
            filesInQueue.add(getByName(videoFileName));
        }
        return filesInQueue;
    }

    static void moveToCompleteList(File srcVideo) {
        TxtFileService.add(COMPLETED_FILES_LIST, srcVideo);
        TxtFileService.remove(QUEUE_FILES_LIST, srcVideo.getName());
    }

    static File getFirstFileFromQueue() {
        return getFilesFromQueue().get(0);
    }

    static void addToQueueFile(File videoFile) {
        TxtFileService.add(QUEUE_FILES_LIST, videoFile);
    }

    static boolean isExistInQueueFiles(String videoFileName) {
        return TxtFileService.exist(QUEUE_FILES_LIST, videoFileName);
    }

    static void removeFromQueueFile(String videoFileName) {
        TxtFileService.remove(QUEUE_FILES_LIST, videoFileName);
    }

    static ArrayList<String> getCompletedFiles() {
        return TxtFileService.getAll(COMPLETED_FILES_LIST);
    }

}
