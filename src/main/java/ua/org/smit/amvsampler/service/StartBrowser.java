/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.util.Console;

/**
 *
 * @author smit
 */
public class StartBrowser {
    
    private static final Logger log = LogManager.getLogger(StartBrowser.class);
    
    public StartBrowser(){
        
        String url = "http://127.0.0.1:8080";
        log.info("Open app in prowser. Url: " + url);
        if (System.getProperty("os.name").contains("Linux")){
            Console.exec("xdg-open " + url);
        } else if (System.getProperty("os.name").contains("Windows")){
            Console.exec("start " + url);
        } else {
            log.info("Undefined OS System. Browser not started automatically.");
        }
        
    }
}
