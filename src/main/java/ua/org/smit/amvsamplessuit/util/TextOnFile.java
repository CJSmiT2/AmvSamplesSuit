/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author smit
 */
public class TextOnFile {
    
    public static synchronized ArrayList<String> readByLine(File file){
        ArrayList<String> lines = new ArrayList();
        
        if (file.exists() && !file.isFile()) throw new RuntimeException("Is not a file! " + file.getAbsolutePath());
        if (!file.exists()) return lines;
        
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                lines.add(strLine);
            }   
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TextOnFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TextOnFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(TextOnFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return lines;
    }
    
    public static synchronized void removeTextLineFromFile(File textFile, String text){
        ArrayList<String> textLines = readByLine(textFile);
        
        Iterator<String> iterator = textLines.iterator();
        while (iterator.hasNext()) {
           String textInLine = iterator.next();
           if (textInLine.equals(text)) {
               iterator.remove();
           }
        }
        
        reWriteTextInFile(textFile, textLines);
    }
    
    public static synchronized void addTextInFile(File textFile, String text){
        try {
            FileWriter writer = new FileWriter(textFile, true);
            writer.append(text);
            writer.append('\n');
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(TextOnFile.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }
    
    public static void addTextInFile(File groupFile, ArrayList<String> text) {
        for (String line : text){
            TextOnFile.addTextInFile(groupFile, line);
        }
    }
    
    public static synchronized void reWriteTextInFile(File textFile, ArrayList<String> textInLines){
        if (!textInLines.isEmpty()){
            File temporaryFile = writeTextInTmpFile(textFile, textInLines);
            removeFile(textFile);
            
            boolean result = temporaryFile.renameTo(textFile);
            if (result) temporaryFile.delete();
        } else {
            makeEmptyFile(textFile);
        }
    }
    
    public static void makeEmptyFile(File textFile) {
        removeFile(textFile);
        try {
            textFile.createNewFile();
        } catch (IOException ex) {
            throw new RuntimeException("Cant create file '" + textFile + "'");
        }
    }

    private static File writeTextInTmpFile(File textFile, ArrayList<String> textInLines) {
        String temporaryFileName = "interim-" + System.currentTimeMillis();
        File interimFile;
        if (textFile.getParentFile() != null){
            interimFile = new File(textFile.getParentFile() + File.separator + temporaryFileName);
        } else {
            interimFile = new File(temporaryFileName);
        }
            
        if (interimFile.exists()){
            throw new RuntimeException("Interim file already exist! But it should be not exist! " + interimFile.getAbsolutePath());
        }
        
        for (String text:textInLines){
            TextOnFile.addTextInFile(interimFile, text);
        }
        
        return interimFile;
    }

    private static void removeFile(File textFile) {
        if (textFile.exists()) {
            textFile.delete();
        }
    }

    
}
