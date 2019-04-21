/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.encodersamples;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author smit
 */
public class ExportEncodeSamplesQueue {

    private static ExportEncodeSamplesQueue INSTANCE;
    public static final ArrayList<File> samples = new ArrayList();

    public ExportEncodeSamplesQueue() {
        new Thread(new ThreadExportEncodeSamples()).start();
    }

    public synchronized static ExportEncodeSamplesQueue instance() {
        if (INSTANCE == null) {
            INSTANCE = new ExportEncodeSamplesQueue();
        }
        return INSTANCE;
    }

}
