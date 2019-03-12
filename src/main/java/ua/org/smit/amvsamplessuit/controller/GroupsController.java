/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.controller;

import java.io.File;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.smit.amvsamplessuit.messages.MessagesService;
import ua.org.smit.amvsamplessuit.messages.Type;
import ua.org.smit.amvsamplessuit.service.completesamples.CompleteSamplesInterface;
import ua.org.smit.amvsamplessuit.service.completesamples.Sample;
import ua.org.smit.amvsamplessuit.service.encodersamples.ExportEncodeSamplesQueue;
import ua.org.smit.amvsamplessuit.service.groups.GroupType;
import ua.org.smit.amvsamplessuit.service.groups.GroupsInterface;
import ua.org.smit.amvsamplessuit.service.settings.Settings;
import ua.org.smit.amvsamplessuit.util.Access;
import ua.org.smit.amvsamplessuit.util.Base64Util;
import ua.org.smit.amvsamplessuit.util.SelectedSamples;

/**
 *
 * @author smit
 */
@Controller
public class GroupsController {
    
    @Autowired
    private Settings settingsService;
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private CompleteSamplesInterface completeSamples;
    @Autowired
    private GroupsInterface groups;
    
    private final String titlesType = GroupType.TITLES.toString().toLowerCase();
    private final String samplesType = GroupType.SAMPLES.toString().toLowerCase();
    
    @RequestMapping(value = { "/view_group" }, method = RequestMethod.GET)
    public String baseOfSamplesNotSorted(
            @RequestParam("groupName") String groupName,
            @RequestParam("groupType") String groupType,
            Model model, 
            HttpServletRequest request) {
        Access.check(request, Settings.isLocalhostOnly());

        ArrayList<File> foldersWithSplitedFiles = groups.getTitles(groupName);
        
        model.addAttribute("foldersWithSplitedFiles", foldersWithSplitedFiles);
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        
        return "folders_with_samples";
    }
    
    @RequestMapping(value = { "/view_samples_in_group" }, method = RequestMethod.GET)
    public String viewSamplesInGroup(
            @RequestParam("groupName") String groupName,
            @RequestParam("groupType") String groupType,
            Model model, 
            HttpServletRequest request) {
        Access.check(request, Settings.isLocalhostOnly());
        
        ArrayList<Sample> samples = new ArrayList();

        if (groupType.equals(titlesType)){
            ArrayList<File> foldersWithSplitedFiles = groups.getTitles(groupName);
            samples = completeSamples.getSamples(foldersWithSplitedFiles);
        } else if (groupType.equals(samplesType)){
            ArrayList<String> samplesSsPath = groups.getSamples(groupName);
            samples = completeSamples.getSamplesByPaths(samplesSsPath);
        }
        
        
        model.addAttribute("samples", samples);
        model.addAttribute("groupName", groupName);
        model.addAttribute("groupType", groupType);
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        model.addAttribute("base64", new Base64Util());
        
        return "samples_folder_common";
    }
    
    @RequestMapping(value = { "/groups" }, method = RequestMethod.GET)
    public String groups(
            @RequestParam("groupType") String groupType,
            Model model, 
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        
        if (groupType.equalsIgnoreCase(titlesType)) {
            model.addAttribute("groups", groups.getGroups(GroupType.TITLES));
        } else if (groupType.equalsIgnoreCase(samplesType)){
            model.addAttribute("groups", groups.getGroups(GroupType.SAMPLES));
        }
        
        model.addAttribute("groupType", groupType);
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        return "groups_manage";
    }

    
    @RequestMapping(value = { "/create_group" }, method = RequestMethod.GET)
    public String createGroup(
            @RequestParam("groupName") String groupName,
            @RequestParam("groupType") String groupType,
            Model model, 
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        
        if (groupType.equalsIgnoreCase(titlesType)) {
            groups.createGroup(groupName, GroupType.TITLES);
            
        } else if (groupType.equalsIgnoreCase(samplesType)){
            groups.createGroup(groupName, GroupType.SAMPLES);
        }

        return "redirect:groups?groupType=" + groupType;
    }
    
    @RequestMapping(value = { "/delete_group" }, method = RequestMethod.GET)
    public String deleteGroup(
            @RequestParam("groupName") String groupName,
            @RequestParam("groupType") String groupType,
            Model model, 
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        
        model.addAttribute("groupName", groupName);
        model.addAttribute("groupType", groupType);

        return "delete_group";
    }
    
    @RequestMapping(value = { "/delete_group_confirm" }, method = RequestMethod.GET)
    public String deleteGroupConfirm(
            @RequestParam("groupName") String groupName,
            @RequestParam("groupType") String groupType,
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        
        if (groupType.equalsIgnoreCase(titlesType)) {
            groups.deleteGroup(groupName, GroupType.TITLES);
            messagesService.add(Type.info, "Group '" + groupName + "' has deleted!");
        } else if (groupType.equalsIgnoreCase(samplesType)){
            groups.deleteGroup(groupName, GroupType.SAMPLES);
            messagesService.add(Type.info, "Group '" + groupName + "' has deleted!");
        }

        return "redirect:groups?groupType=" + groupType;
    }
    
    @RequestMapping(value = { "/add_in_group_form" }, method = RequestMethod.GET)
    public String addInGroupForm(
            @RequestParam("folderName") String folderName,
            @RequestParam("groupType") String groupType,
            Model model, 
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        
        model.addAttribute("folderName", folderName);
        model.addAttribute("groupType", groupType);
        
        if (groupType.equalsIgnoreCase(titlesType)) {
            model.addAttribute("groups", groups.getGroups(GroupType.TITLES));
        } else if (groupType.equalsIgnoreCase(samplesType)){
            model.addAttribute("groups", groups.getGroups(GroupType.SAMPLES));
        }

        return "add_in_group_form";
    }
    
    @RequestMapping(value = {"process_selected_group"}, method = RequestMethod.POST)
    public String processSelectedGroup(
            HttpServletRequest request,
            @RequestParam(value = "groupName") String groupName,
            @RequestParam("groupType") String groupType,
            @RequestParam(value = "selected_action") String selectedAction){
        
        if (selectedAction.equalsIgnoreCase("export_selected")){
            ExportEncodeSamplesQueue.instance();
            ArrayList<File> titles = SelectedSamples.fingFromRequestAsFiles(request);
            ExportEncodeSamplesQueue.samples.addAll(titles);
            messagesService.add(Type.success, titles.size() + " samples added to export queue!");
            
        } else if (selectedAction.equalsIgnoreCase("delete_selected")){
            ArrayList<File> titles = groups.getTitlesFromSamplesGroup(groupName);
            int count = completeSamples.deleteSelectedSamples(titles, request);
            messagesService.add(Type.warning, "'" + count + "' samples has deleted!");
            
        } else if (selectedAction.equalsIgnoreCase("remove_from_samples_group")){
            ArrayList<String> selectedSamples = SelectedSamples.fingFromRequest(request);
            int count = groups.removeFromSamples(selectedSamples, groupName);
            messagesService.add(Type.info, "'" + count + "' samples has removed from group!");
            
        } else {
            messagesService.add(Type.danger, "Action is not determined!");
        }
        
        return "redirect:view_samples_in_group?groupName=" + groupName + "&groupType=" + groupType;
    }
    
}
