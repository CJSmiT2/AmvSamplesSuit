/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author smit
 */
public class Console {
   
    private static final Logger log = LogManager.getLogger(Console.class);
   
   public static void exec(String cmd) {
       log.info("START exec cmd: " + cmd);
       try {
            Process process;
            if (System.getProperty("os.name").contains("Windows")){
                String[] cmds = {"cmd.exe", "/c", cmd};
                process = Runtime.getRuntime().exec(cmds);
            } else {
                process = Runtime.getRuntime().exec(cmd);
            }

            String s;
            BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
            while ((s = br.readLine()) != null)
                log.info("line: " + s);
            process.waitFor();
            if (process.exitValue() == 1){
                log.error("ERROR of execute command!");
            }
            process.destroy();
        
        } catch (Exception ex) {
            log.error(ex);
        }
       log.info("END exec cmd.");
    }
}
