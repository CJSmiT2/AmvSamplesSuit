/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author smit
 */
public class SelectedSamples {

    public static ArrayList<String> fingFromRequest(HttpServletRequest request) {
        ArrayList<String> samplesPath = new ArrayList();

        Enumeration<String> parmsNames = request.getParameterNames();
        while (parmsNames.hasMoreElements()) {
            String param = (String) parmsNames.nextElement();
            File sample = new File(new Base64Util().decode(param));
            if (sample.exists()) {
                System.out.println("Found: " + sample);
                samplesPath.add(sample.getParentFile().getAbsolutePath().toString());
            }
        }

        return samplesPath;
    }

    public static ArrayList<File> fingFromRequestAsFiles(HttpServletRequest request) {
        ArrayList<File> samplesPath = new ArrayList();

        Enumeration<String> parmsNames = request.getParameterNames();
        while (parmsNames.hasMoreElements()) {
            String param = (String) parmsNames.nextElement();
            File sample = new File(new Base64Util().decode(param));
            if (sample.exists()) {
                System.out.println("Found: " + sample);
                samplesPath.add(sample);
            }
        }

        return samplesPath;
    }
}
