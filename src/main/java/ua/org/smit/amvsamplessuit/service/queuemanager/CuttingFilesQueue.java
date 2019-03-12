/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.queuemanager;

/**
 *
 * @author smit
 */
public class CuttingFilesQueue {
    
    private static CuttingFilesQueue INSTANCE;
    
    public CuttingFilesQueue(){
        new Thread(new ThreadQueueCuttingFiles()).start();
    }
    
    public synchronized static CuttingFilesQueue instance(){
        if (INSTANCE == null){
            INSTANCE = new CuttingFilesQueue();
        }
        return INSTANCE;
    }
    
}
