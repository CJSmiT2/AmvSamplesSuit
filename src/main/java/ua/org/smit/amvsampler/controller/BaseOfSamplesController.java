/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.io.File;
import ua.org.smit.amvsampler.util.NormalizeSampleUtil;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.smit.amvsampler.messages.MessagesService;
import ua.org.smit.amvsampler.messages.Type;
import ua.org.smit.amvsampler.service.completesamples.CompleteSamplesInterface;
import ua.org.smit.amvsampler.service.completesamples.Limits;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.service.groups.GroupType;
import ua.org.smit.amvsampler.service.groups.GroupsInterface;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.Access;
import ua.org.smit.amvsampler.util.Base64Util;
import ua.org.smit.amvsampler.util.CookieUtil;

/**
 *
 * @author smit
 */
@Controller
public class BaseOfSamplesController {
    
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private CompleteSamplesInterface completeSamples;
    @Autowired
    private GroupsInterface groups;
    
    @RequestMapping(value = { "/base_of_samples" }, method = RequestMethod.GET)
    public String baseOfSamples(
            Model model, 
            HttpServletRequest request) {
        Access.check(request, Settings.isLocalhostOnly());
        
        model.addAttribute("foldersWithSplitedFiles", completeSamples.getFoldersWithSplitedFiles());
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        
        return "folders_with_samples";
    }
    
    @RequestMapping(value = { "/base_of_samples_not_sorted" }, method = RequestMethod.GET)
    public String baseOfSamplesNotSorted(
            Model model, 
            HttpServletRequest request) {
        Access.check(request, Settings.isLocalhostOnly());
        
        ArrayList<File> allSamplesFolders = completeSamples.getFoldersWithSplitedFiles();
        ArrayList<File> foldersWithSplitedFiles = groups.findTitlesWithoutGroups(allSamplesFolders);
        
        model.addAttribute("foldersWithSplitedFiles", foldersWithSplitedFiles);
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        
        return "folders_with_samples";
    }
    
    @RequestMapping(value = { "/samples_folder" }, method = RequestMethod.GET)
    public String samplesFolder(
            @RequestParam("folderName") String folderName,
            Model model, 
            HttpServletRequest request) {
        Access.check(request, Settings.isLocalhostOnly());
        
        model.addAttribute("samples", completeSamples.getSamplesByLimits(folderName));
        model.addAttribute("limits", completeSamples.readLimits(folderName));
        model.addAttribute("folderName", folderName);
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        model.addAttribute("base64", new Base64Util());
        model.addAttribute("titlesGroups", groups.getGroups(GroupType.TITLES));
        model.addAttribute("samplesGroups", groups.getGroups(GroupType.SAMPLES));
        
        model.addAttribute("lastAddParamsForTitles", CookieUtil.read("lastAddParamsForTitles", request));
        
        return "samples_folder";
    }
    
    
    @RequestMapping(value = {"set_limits"})
    public String setLimits(
            Model model, 
            @RequestParam(value = "folderName") String folderName,
            @RequestParam(value = "limit", required = false, defaultValue = "400") int limit){
        
        ArrayList<Sample> samples = completeSamples.get(folderName);
        NormalizeSampleUtil.setMaxAvgPercentLimit(samples, limit);
        
        model.addAttribute("samples", samples);
        model.addAttribute("folderName", folderName);
        model.addAttribute("limits", completeSamples.readLimits(folderName));
        model.addAttribute("limit", limit);
        
        return "set_limits";
    }
    
    @RequestMapping(value = {"save_limits"})
    public String saveLimits(
            @RequestParam(value = "folderName") String folderName,
            @RequestParam(value = "rage") String rage,
            @RequestParam(value = "minAvgPercent") int minAvgPercent){
        
        completeSamples.saveLimits(new Limits(folderName, minAvgPercent, rage));
        
        return "redirect:samples_folder?folderName=" + folderName;
    }
    
    @RequestMapping(value = {"delete"})
    public String delete(
            Model model, 
            @RequestParam(value = "folderName") String folderName){

        model.addAttribute("folderName", folderName);

        return "delete";
    }

    @RequestMapping(value = {"delete_confirm"})
    public String deleteConfirm(
            @RequestParam(value = "folderName") String folderName){
        
        ArrayList<Sample> samples = completeSamples.get(folderName);
        groups.removeSamplesFromAllSamplesGroups(samples);

        completeSamples.deleteFolder(folderName);
        messagesService.add(Type.warning,  "'" + folderName + "' has deleted!");
        return "redirect:base_of_samples_not_sorted";
    }
    
}
