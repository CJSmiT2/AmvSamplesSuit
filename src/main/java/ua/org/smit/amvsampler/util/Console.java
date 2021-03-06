/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author smit
 */
public class Console {

    private static final Logger log = LogManager.getLogger(Console.class);

    private static final int sleep = 0;

    public static void exec(String cmd) {
        log.debug("+++ START exec cmd: " + cmd);
        try {
            Process process;
            if (System.getProperty("os.name").contains("Windows")) {
                String[] cmds = {"cmd.exe", "/c", cmd};
                process = Runtime.getRuntime().exec(cmds);
            } else {
                process = Runtime.getRuntime().exec(cmd);
            }

            String s;
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            while ((s = br.readLine()) != null) {
                log.debug("line: " + s);
            }
            process.waitFor();
            if (process.exitValue() == 1) {
                log.error("ERROR of execute command!");
                process.destroy();
                throw new Exception();
            }

        } catch (Exception ex) {
            log.error(ex);
        }
        log.debug("END exec cmd.");
        try {
            sleep(sleep);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
