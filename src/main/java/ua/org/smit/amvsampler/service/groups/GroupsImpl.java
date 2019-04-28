/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.groups;

import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.service.completesamples.Sample;

/**
 *
 * @author smit
 */
public class GroupsImpl implements GroupsInterface {

    private static final Logger log = LogManager.getLogger(GroupsImpl.class);

    @Override
    public void createGroup(String groupName, GroupType type) {
        if (type == GroupType.TITLES) {
            GroupsService.createGroupForTitles(groupName);
        } else if (type == GroupType.TAGS) {
            GroupsService.createGroupForSamples(groupName);
        }
    }

    @Override
    public void addInGroup(String groupName, String folderOrSample, GroupType type) {
        log.info("Add to group '" + groupName + "', type='" + type + "', folderOrSample = '" + folderOrSample + "'");
        if (type == GroupType.TITLES) {
            GroupsService.addInGroupForTitles(groupName, folderOrSample);
        } else if (type == GroupType.TAGS) {
            GroupsService.addInGroupForSamples(groupName, folderOrSample);
        }
    }

    @Override
    public boolean isExistInGroup(String groupName, String folderOrSample, GroupType type) {
        if (type == GroupType.TITLES) {
            return GroupsService.isExistInTitlesGroup(groupName, folderOrSample);
        } else if (type == GroupType.TAGS) {
            return GroupsService.isExistInSamplesGroup(groupName, folderOrSample);
        }
        return false;
    }

    @Override
    public ArrayList<String> getGroups(GroupType type) {
        if (type == GroupType.TITLES) {
            return GroupsService.getGroupsForTitles();
        } else {
            return GroupsService.getGroupsForSamples();
        }
    }

    @Override
    public ArrayList<String> getFromAllGroups(GroupType type) {
        if (type == GroupType.TITLES) {
            return GroupsService.getFoldersSamplesFromAllTitlesGroups();
        } else {
            return GroupsService.getSamplesFromAllSamplesGroups();
        }
    }

    @Override
    public void removeFromGroup(String groupName, String folderName, GroupType type) {
        if (type == GroupType.TITLES) {
            GroupsService.removeFromTitlesGroup(groupName, folderName);
        } else {
            GroupsService.removeFromSamplesGroup(groupName, folderName);
        }
    }

    @Override
    public void deleteGroup(String groupName, GroupType type) {
        if (type == GroupType.TITLES) {
            GroupsService.deleteGroupForTitles(groupName);
        } else {
            GroupsService.deleteGroupForSamples(groupName);
        }
    }

    @Override
    public ArrayList<File> getTitles(String groupName) {
        return GroupsService.getTitlesFromGroup(groupName);
    }

    @Override
    public ArrayList<String> getSamples(String groupName) {
        return GroupsService.getFromSamplesGroup(groupName);
    }

    @Override
    public ArrayList<String> getSamplesFromAllGroups() {
        return GroupsService.getSamplesFromAllSamplesGroups();
    }

    @Override
    public void addSamples(ArrayList<String> samplesPath, String samplesGroup) {
        GroupsService.addSamplesToSamplesGroup(samplesPath, samplesGroup);
    }

    @Override
    public ArrayList<GroupInfo> getGroupsInfo(GroupType type) {
        if (type == GroupType.TITLES) {
            return GroupsService.getGroupsInfoForTitles();
        } else {
            return GroupsService.getGroupsInfoForSamples();
        }
    }

    @Override
    public void removeSamplesFromAllSamplesGroups(ArrayList<Sample> samplesForDelete) {
        GroupsService.removeFromAllSamplesGroups(samplesForDelete);
    }

    @Override
    public int removeFromSamples(ArrayList<String> pathsForDelete, String groupName) {
        return GroupsService.removeFromSamplesGroup(pathsForDelete, groupName);
    }

    @Override
    public ArrayList<File> findTitlesWithoutGroups(ArrayList<File> allSamplesFolders) {
        return GroupsService.findTitlesWithoutGroups(allSamplesFolders);
    }

    @Override
    public ArrayList<File> getTitlesFromSamplesGroup(String groupName) {
        return GroupsService.getTitlesFromSamplesGroup(groupName);
    }
    
    @Override
    public boolean isExistInAnyTagGroup(String sampleInput) {
        log.debug("Check in tag group: " + sampleInput);
        for (String sample : getFromAllGroups(GroupType.TAGS)){
            log.debug("\nneed: " + sampleInput + "\ncurr: " + sample);
            if (sample.equals(sampleInput)){
                log.debug("exist");
                return true;
            }
        }
        log.debug("NOT EXIST");
        return false;
    }
    
}
