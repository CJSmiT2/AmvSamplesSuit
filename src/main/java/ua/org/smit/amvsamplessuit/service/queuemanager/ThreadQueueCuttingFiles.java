/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.queuemanager;

import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.org.smit.amvsamplessuit.service.CompleteAndQueueListsImpl;
import ua.org.smit.amvsamplessuit.service.CompleteAndQueueListsInterface;
import ua.org.smit.amvsamplessuit.service.splitanalyzeengine.EngineSplitAnalyze;

/**
 *
 * @author smit
 */
public class ThreadQueueCuttingFiles implements Runnable {

    @Override
    public void run() {
        while (true){
            
            if (!EngineSplitAnalyze.isInProgress()){
                
                CompleteAndQueueListsInterface completeAndQueueLists = new CompleteAndQueueListsImpl();
                
                if (!completeAndQueueLists.getFilesFromQueue().isEmpty()) {
                    File videoFile = completeAndQueueLists.getFirstFileFromQueue();
                    EngineSplitAnalyze.setCurrentVideoFile(videoFile);
                    EngineSplitAnalyze.instance().splitAndAnalyze(videoFile);
                }
            }
            
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadQueueCuttingFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
