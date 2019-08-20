/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.smit.amvsampler.messages.MessagesService;
import ua.org.smit.amvsampler.service.completesamples.CompleteSamplesInterface;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.service.groups.GroupType;
import ua.org.smit.amvsampler.service.groups.GroupsInterface;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.Access;
import ua.org.smit.amvsampler.util.Base64Util;

/**
 *
 * @author smit
 */
@Controller
public class TagController {

    @Autowired
    private Settings settingsService;
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private CompleteSamplesInterface completeSamples;
    @Autowired
    private GroupsInterface groups;
    
    private final Base64Util base64 = new Base64Util();

    private static ArrayList<Sample> allSamplesInstance;

    private static final Logger log = LogManager.getLogger(TagController.class);

    @RequestMapping(value = {"/view_sample_without_tag"}, method = RequestMethod.GET)
    public String baseOfSamplesNotSorted(
            Model model,
            HttpServletRequest request) {
        Access.check(request, Settings.isLocalhostOnly());

        if (allSamplesInstance == null) {
            log.info("START LOAD ALL SAMPLES...");
            allSamplesInstance = completeSamples.getAllSamples();
            log.info("ALL SAMPLES IS LOADED!!! size: " + allSamplesInstance.size());
        }

        for (Sample sample : allSamplesInstance) {
            if (!groups.isExistInAnyTagGroup(sample.getSsFolderString())) {
                model.addAttribute("sample", sample);
                break;
            }
        }

        model.addAttribute("base64", base64);
        model.addAttribute("groups", groups.getGroups(GroupType.TAGS));

        return "view_sample_without_tag";
    }

    @RequestMapping(value = {"add_sample_to_tag_groups"})
    public String processSelected(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "titleFolder") String titleFolder,
            @RequestParam(value = "ssFolder") String ssFolder) {

        Sample sample = completeSamples.getSample(titleFolder, Integer.valueOf(ssFolder));

        for (String group : groups.getGroups(GroupType.TAGS)) {
            String groupName = request.getParameter(group);
            if (groupName != null) {
                groups.addInGroup(group, sample.getSsFolder().getAbsolutePath(), GroupType.TAGS);
                log.info("\nAdded:\n " + sample.getTitle() + " ss=" + sample.getSs() + "\nto tag group: " + group);
            }
        }

        return "redirect:view_sample_without_tag";
    }
}
