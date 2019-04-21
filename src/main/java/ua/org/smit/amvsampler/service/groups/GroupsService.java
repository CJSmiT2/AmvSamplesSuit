/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.groups;

import com.google.common.collect.Ordering;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.service.statistics.StatisticsInfoImpl;

/**
 *
 * @author smit
 */
class GroupsService {

    static void createGroupForTitles(String groupName) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        groups.createGroup(groupName);
    }

    static boolean isExistInTitlesGroup(String groupName, String titleFolder) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        for (String fromGroupFile : groups.getFromGroupFile(groupName)) {
            if (fromGroupFile.equals(titleFolder)) {
                return true;
            }
        }
        return false;
    }

    static ArrayList<String> getGroupsForTitles() {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        ArrayList<String> groupNames = groups.getGroupsNames();
        Collections.sort(groupNames, Ordering.usingToString());
        return groupNames;
    }

    static ArrayList<String> getFoldersSamplesFromAllTitlesGroups() {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        return groups.getFoldersSamplesFromAllGroups();
    }

    static void addInGroupForTitles(String groupName, String folderName) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        groups.addInGroup(groupName, folderName);

        new StatisticsInfoImpl().addOneProcessed();
    }

    static void removeFromTitlesGroup(String groupName, String folderName) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        groups.removeFromGroup(groupName, folderName);
    }

    static void deleteGroupForTitles(String groupName) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        groups.deleteGroup(groupName);
    }

    static ArrayList<File> findTitlesWithoutGroups(ArrayList<File> allSamplesFolders) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        ArrayList<String> foldersSamplesInGroups = groups.getFoldersSamplesFromAllGroups();
        ArrayList<File> foldersSamplesWithoutGroups = new ArrayList();

        for (File folderForSample : allSamplesFolders) {
            if (!SearchList.isFound(folderForSample.getName(), foldersSamplesInGroups)) {
                foldersSamplesWithoutGroups.add(folderForSample);
            }
        }

        return foldersSamplesWithoutGroups;
    }

    static ArrayList<File> getTitlesFromGroup(String groupName) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_TITLES_FOLDER);
        ArrayList<File> foldersSamples = new ArrayList();
        for (String folderName : groups.getFromGroupFile(groupName)) {
            File folderForSamples = new File(Settings.GROUPS_BY_TITLES_FOLDER + File.separator + folderName);
            foldersSamples.add(folderForSamples);
        }
        return foldersSamples;
    }

    static void createGroupForSamples(String groupName) {
        GroupsBySamples groups = new GroupsBySamples(Settings.GROUPS_BY_SAMPLES_FOLDER);
        groups.createGroup(groupName);
    }

    static ArrayList<String> getGroupsForSamples() {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_SAMPLES_FOLDER);
        return groups.getGroupsNames();
    }

    static ArrayList<String> getFromSamplesGroup(String groupName) {
        GroupsByTitles groups = new GroupsByTitles(Settings.GROUPS_BY_SAMPLES_FOLDER);
        ArrayList<String> samplesPaths = groups.getFromGroupFile(groupName);
        PathUtil.convertToDirect(samplesPaths);
        return samplesPaths;
    }

    static ArrayList<String> getSamplesFromAllSamplesGroups() {
        ArrayList<String> samplesInGroups = new ArrayList();
        for (String group : getGroupsForSamples()) {
            samplesInGroups.addAll(getFromSamplesGroup(group));
        }
        return samplesInGroups;
    }

    static void addInGroupForSamples(String groupName, String sampleFile) {
        GroupsBySamples groups = new GroupsBySamples(Settings.GROUPS_BY_SAMPLES_FOLDER);
        groups.addInGroup(groupName, sampleFile);

        new StatisticsInfoImpl().addOneProcessed();
    }

    static void removeFromSamplesGroup(String groupName, String sampleFile) {
        GroupsBySamples groups = new GroupsBySamples(Settings.GROUPS_BY_SAMPLES_FOLDER);
        groups.removeFromSamplesGroup(groupName, sampleFile);
    }

    static void deleteGroupForSamples(String groupName) {
        GroupsBySamples groups = new GroupsBySamples(Settings.GROUPS_BY_SAMPLES_FOLDER);
        groups.deleteGroup(groupName);
    }

    static void addSamplesToSamplesGroup(ArrayList<String> samplesPath, String samplesGroup) {
        GroupsBySamples groups = new GroupsBySamples(Settings.GROUPS_BY_SAMPLES_FOLDER);
        groups.addInGroup(samplesPath, samplesGroup);
        new StatisticsInfoImpl().addToProcessed(samplesPath.size());
    }

    static ArrayList<GroupInfo> getGroupsInfoForTitles() {
        ArrayList<GroupInfo> groupsInfo = new ArrayList();

        ArrayList<String> groups = getGroupsForTitles();
        for (String group : groups) {
            int size = getTitlesFromGroup(group).size();
            groupsInfo.add(new GroupInfo(group, size));
        }

        return groupsInfo;
    }

    static ArrayList<GroupInfo> getGroupsInfoForSamples() {
        ArrayList<GroupInfo> groupsInfo = new ArrayList();

        ArrayList<String> groups = getGroupsForSamples();
        for (String group : groups) {
            int size = getFromSamplesGroup(group).size();
            groupsInfo.add(new GroupInfo(group, size));
        }

        return groupsInfo;
    }

    static void removeFromAllSamplesGroups(ArrayList<Sample> samplesForDelete) {
        for (String group : getGroupsForSamples()) {
            ArrayList<String> fromGroup = getFromSamplesGroup(group);
            for (String sampleSs : fromGroup) {
                for (Sample sample : samplesForDelete) {
                    if (sample.getSsFolder().getAbsolutePath().equals(sampleSs)) {
                        deleteFromSamplesGroup(sampleSs, group);
                    }
                }
            }
        }
    }

    private static void deleteFromSamplesGroup(String sampleSs, String group) {
        GroupsBySamples groups = new GroupsBySamples(Settings.GROUPS_BY_SAMPLES_FOLDER);
        groups.removeFromGroup(group, sampleSs);

        new StatisticsInfoImpl().removeOneFromProcessed();
    }

    static int removeFromSamplesGroup(ArrayList<String> pathsForDelete, String groupName) {
        int count = 0;
        ArrayList<String> samplesPaths = getFromSamplesGroup(groupName);
        for (String samplePathSs : samplesPaths) {
            for (String samplesPathSsForDelete : pathsForDelete) {
                if (samplePathSs.equals(samplesPathSsForDelete)) {
                    deleteFromSamplesGroup(samplePathSs, groupName);
                    count++;
                }
            }

        }
        return count;
    }

    static boolean isExistInSamplesGroup(String groupName, String folderOrSample) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static ArrayList<File> getTitlesFromSamplesGroup(String groupName) {
        ArrayList<String> samplesPath = getFromSamplesGroup(groupName);
        PathUtil.convertToDirect(samplesPath);

        ArrayList<File> titles = new ArrayList();
        for (String titlePath : samplesPath) {
            titles.add(new File(titlePath).getParentFile());
        }

        Set<File> samplesPathUniqe = new HashSet<File>(titles);
        titles.clear();
        titles.addAll(samplesPathUniqe);
        return titles;
    }

}
