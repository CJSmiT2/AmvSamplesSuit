/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.groups;

import java.io.File;
import java.util.ArrayList;
import ua.org.smit.amvsamplessuit.util.FilesUtil;
import ua.org.smit.amvsamplessuit.util.StringUtil;

/**
 *
 * @author smit
 */
public class Groups {
    
    private final File groupsFolder;

    public Groups(File groupsFolder) {
        this.groupsFolder = groupsFolder;
    }
    
    File getGroupFile(String groupName){
        return new File(groupsFolder + File.separator + groupName);
    }
    
    void createGroupFile(){
        if (!groupsFolder.exists()){
            groupsFolder.mkdir();
        }
    }
    
    public void createGroup(String groupName){
        createGroupFile();
        
        groupName = StringUtil.getWithAllowedSymbols(groupName);
        
        File groupTxtFile = getGroupFile(groupName);
        if (!groupTxtFile.exists()){
            FilesUtil.makeEmptyFile(groupTxtFile);
        }
    }
    
    public void deleteGroup(String groupName){
        File groupTxtFile = new File(groupsFolder + File.separator + groupName);
        groupTxtFile.delete();
    }
    
    public ArrayList<String> getGroupsNames(){
        ArrayList<String> groups = new ArrayList();
        for (File fileGroup : FilesUtil.getFiles(groupsFolder)){
            groups.add(fileGroup.getName());
        }
        return groups;
        
    }
    
}
