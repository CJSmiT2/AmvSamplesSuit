/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.org.smit.amvsamplessuit.messages.MessagesService;
import ua.org.smit.amvsamplessuit.messages.Type;
import ua.org.smit.amvsamplessuit.service.CompleteAndQueueListsInterface;
import ua.org.smit.amvsamplessuit.service.groups.GroupType;
import ua.org.smit.amvsamplessuit.service.groups.GroupsInterface;
import ua.org.smit.amvsamplessuit.service.queuemanager.CuttingFilesQueue;
import ua.org.smit.amvsamplessuit.service.settings.Settings;
import ua.org.smit.amvsamplessuit.service.statistics.StatisticsInfoInterface;
import ua.org.smit.amvsamplessuit.util.Access;
import ua.org.smit.amvsamplessuit.util.FilesUtil;

/**
 *
 * @author smit
 */
@Controller
public class MainController {
    
    @Autowired
    private Settings settingsService;
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private GroupsInterface groups;
    @Autowired
    private CompleteAndQueueListsInterface completeAndQueueLists;
    @Autowired
    private StatisticsInfoInterface statisticsInfo;
    
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String home(
            Model model, 
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        
        if (!Settings.isSettingsFileExist()){
            return "redirect:create_config_file_step1";
        }
        
        CuttingFilesQueue.instance();
        
        model.addAttribute("samplesInGroupsSize", groups.getSamplesFromAllGroups().size());
        model.addAttribute("statisticsInfo", statisticsInfo.get());
        model.addAttribute("groupsInfoTitles", groups.getGroupsInfo(GroupType.TITLES));
        model.addAttribute("groupsInfoSamples", groups.getGroupsInfo(GroupType.SAMPLES));
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        
        return "home";
    }
    
    @RequestMapping(value = { "/source_folder" }, method = RequestMethod.GET)
    public String sourceFolder(
            Model model,
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        
        if (!settingsService.getSrcFolder().exists()){
            messagesService.add(Type.warning, "Source folder not set in settings! Configure the program on Settings page!");
            model.addAttribute("messages", messagesService.getMessagesAndClear());
        } else {
            FilesUtil.getAllFilesRecurcive(settingsService.getSrcFolder());
            model.addAttribute("files", completeAndQueueLists.getUnprocessedVideoFiles());
        }
        return "source_folder";
    }
}
