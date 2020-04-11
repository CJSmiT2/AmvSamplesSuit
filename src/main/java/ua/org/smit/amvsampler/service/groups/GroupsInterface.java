/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.groups;

import java.io.File;
import java.util.ArrayList;
import ua.org.smit.amvsampler.service.completesamples.Sample;

/**
 *
 * @author smit
 */
public interface GroupsInterface {

    void createGroup(String groupName, GroupType type);

    void addInGroup(String groupName, String folderOrSample, GroupType type);

    boolean isExistInGroup(String groupName, String folderOrSample, GroupType type);

    ArrayList<String> getGroups(GroupType type);

    ArrayList<String> getFromAllGroups(GroupType type);

    void removeFromGroup(String groupName, String folderName, GroupType type);

    void deleteGroup(String groupName, GroupType type);

    ArrayList<File> getTitles(String groupName);

    ArrayList<String> getSamples(String groupName);

    ArrayList<String> getSamplesFromAllGroups();

    void addSamples(ArrayList<String> samplesPath, String samplesGroup);

    ArrayList<GroupInfo> getGroupsInfo(GroupType type);

    void removeSamplesFromAllSamplesGroups(ArrayList<Sample> samplesForDelete);

    int removeFromSamples(ArrayList<String> pathsForDelete, String groupName);

    ArrayList<File> findTitlesWithoutGroups(ArrayList<File> allSamplesFolders);

    ArrayList<File> getTitlesFromSamplesGroup(String groupName);

    boolean isExistInAnyTagGroup(String sample);

}
