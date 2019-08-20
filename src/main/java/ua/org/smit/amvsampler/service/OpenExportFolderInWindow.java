package ua.org.smit.amvsampler.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.util.Console;

/**
 *
 * @author smit
 */
public class OpenExportFolderInWindow {
    private static final Logger log = LogManager.getLogger(OpenExportFolderInWindow.class);
    
    public OpenExportFolderInWindow(String path){
        log.debug("Try open folder in window: " + path);
        
        if (System.getProperty("os.name").contains("Linux")) {
            Console.exec("nemo " + path);
        } else if (System.getProperty("os.name").contains("Windows")) {
            Console.exec("start " + path);
        } else {
            log.info("Undefined OS System. Browser not started automatically.");
        }
    }
}
