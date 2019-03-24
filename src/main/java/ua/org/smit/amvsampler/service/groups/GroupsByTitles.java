/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.groups;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import ua.org.smit.amvsampler.util.TextOnFile;

/**
 *
 * @author smit
 */
public class GroupsByTitles extends Groups{

    public GroupsByTitles(File groupsFolder) {
        super(groupsFolder);
    }

    public void addInGroup(String groupName, String titleFolder){
        ArrayList<String> titles = TextOnFile.readByLine(getGroupFile(groupName));
        titles.add(titleFolder);
        TextOnFile.reWriteTextInFile(getGroupFile(groupName), titles);
    }
    
    public ArrayList<String> getFromGroupFile(String groupName){
        return TextOnFile.readByLine(getGroupFile(groupName));
    }
    
    public void removeFromGroup(String groupName, String titleFolder){
        ArrayList<String> titles = TextOnFile.readByLine(getGroupFile(groupName));
        Iterator<String> iterator = titles.iterator();
        while (iterator.hasNext()) {
           String title = iterator.next();
           if (title.equals(titleFolder)) {
               iterator.remove();
           }
        }
        TextOnFile.reWriteTextInFile(getGroupFile(groupName), titles);
    }

    public ArrayList<String> getFoldersSamplesFromAllGroups() {
        ArrayList<String> foldersSamples = new ArrayList();
        for (String groupName : getGroupsNames()){
            ArrayList<String> foldersSamplesInGroup = TextOnFile.readByLine(getGroupFile(groupName));
            foldersSamples.addAll(foldersSamplesInGroup);
        }
        return foldersSamples;
    }

    
}
