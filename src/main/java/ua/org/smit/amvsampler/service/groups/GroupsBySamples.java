/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.groups;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.org.smit.amvsampler.util.TextOnFile;

/**
 *
 * @author smit
 */
public class GroupsBySamples extends Groups {

    private static final Logger log = LogManager.getLogger(GroupsBySamples.class);

    public GroupsBySamples(File groupsFolder) {
        super(groupsFolder);
    }

    public void addInGroup(String groupName, String sampleFile) {
        log.info("Add '" + sampleFile + "' to group '" + groupName + "'");

        String pathOfSample = PathUtil.convertToRelative(sampleFile);

        ArrayList<String> samples = TextOnFile.readByLine(getGroupFile(groupName));
        samples.add(pathOfSample);
        TextOnFile.reWriteTextInFile(getGroupFile(groupName), samples);
    }

    public void removeFromGroup(String groupName, String sampleFile) {
        log.info("Remove '" + sampleFile + "' from group '" + groupName + "'");
        String pathOfSample = PathUtil.convertToRelative(sampleFile);

        ArrayList<String> samples = TextOnFile.readByLine(getGroupFile(groupName));
        Iterator<String> iterator = samples.iterator();
        while (iterator.hasNext()) {
            String title = iterator.next();
            if (title.equals(pathOfSample)) {
                iterator.remove();
            }
        }
        TextOnFile.reWriteTextInFile(getGroupFile(groupName), samples);
    }

    public void removeFromSamplesGroup(String groupName, String sampleSs) {
        log.info("Remove '" + sampleSs + "' from samples group '" + groupName + "'");

        ArrayList<String> samples = TextOnFile.readByLine(getGroupFile(groupName));
        Iterator<String> samplesSs = samples.iterator();
        while (samplesSs.hasNext()) {
            String sample = samplesSs.next();
            if (sample.equals(sampleSs)) {
                samplesSs.remove();
            }
        }
        TextOnFile.reWriteTextInFile(getGroupFile(groupName), samples);
    }

    void addInGroup(ArrayList<String> samplesPath, String samplesGroup) {
        PathUtil.convertToRelative(samplesPath);

        ArrayList<String> samplesInGroup = getSamplesPath(samplesGroup);
        Set<String> samplesSet = new HashSet<String>(samplesInGroup);
        samplesSet.addAll(samplesPath);

        ArrayList<String> samplesToGroup = new ArrayList();
        for (String samplePath : samplesSet) {
            samplesToGroup.add(samplePath);
        }
        TextOnFile.reWriteTextInFile(getGroupFile(samplesGroup), samplesToGroup);
    }

    public void removeFromGroup(ArrayList<String> samplesPath, String samplesGroup) {
        ArrayList<String> samplesPathInGroup = getSamplesPath(samplesGroup);
        samplesPathInGroup.removeAll(samplesPath);
        TextOnFile.reWriteTextInFile(getGroupFile(samplesGroup), samplesPathInGroup);
    }

    private ArrayList<String> getSamplesPath(String samplesGroup) {
        return TextOnFile.readByLine(getGroupFile(samplesGroup));
    }

}
