/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.splitanalyzeengine;

import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author smit
 */
public class ThreadSplitAnalyze implements Runnable {

    private final File srcVideo;
    private static final Logger log = LogManager.getLogger(ThreadSplitAnalyze.class);

    ThreadSplitAnalyze(File file) {
        this.srcVideo = file;
    }

    @Override
    public void run() {
        new VideoFile().cutAndAnalyze(srcVideo);
    }

}
