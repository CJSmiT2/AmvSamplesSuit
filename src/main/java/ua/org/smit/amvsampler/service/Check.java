/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author smit
 */
class Check {

    static boolean unprocessed(File file, ArrayList<String> queueFilesNames, ArrayList<String> completedFilesNames) {
        for (String queueFile : queueFilesNames) {
            if (file.getName().equals(queueFile)) {
                return false;
            }
        }
        for (String completedFile : completedFilesNames) {
            if (file.getName().equals(completedFile)) {
                return false;
            }
        }
        return true;
    }
}
