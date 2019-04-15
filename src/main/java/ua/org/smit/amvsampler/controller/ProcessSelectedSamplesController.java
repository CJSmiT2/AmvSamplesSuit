/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.io.File;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.smit.amvsampler.messages.MessagesService;
import ua.org.smit.amvsampler.messages.Type;
import ua.org.smit.amvsampler.service.completesamples.CompleteSamplesInterface;
import ua.org.smit.amvsampler.service.encodersamples.ExportEncodeSamplesQueue;
import ua.org.smit.amvsampler.service.groups.GroupType;
import ua.org.smit.amvsampler.service.groups.GroupsInterface;
import ua.org.smit.amvsampler.service.statistics.StatisticsInfoInterface;
import ua.org.smit.amvsampler.util.CookieUtil;
import ua.org.smit.amvsampler.util.SelectedSamples;

/**
 *
 * @author smit
 */
@Controller
public class ProcessSelectedSamplesController {
    
    @Autowired
    private MessagesService messagesService;
    @Autowired
    private CompleteSamplesInterface completeSamples;
    @Autowired
    private GroupsInterface groups;
    @Autowired
    private StatisticsInfoInterface statisticsInfo;
    
    @RequestMapping(value = {"process_selected"})
    public String processSelected(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "folderName") String folderName,
            @RequestParam(value = "selected_action") String selectedAction,
            @RequestParam(value = "samples_group", required = false) String samplesGroup,
            @RequestParam(value = "title_group", required = false) String titleGroup,
            @RequestParam(value = "delete_samples", required = false) String deleteSamples){
        
        if (selectedAction.equalsIgnoreCase("export_selected")){
            
            ExportEncodeSamplesQueue.instance();
            ArrayList<File> titles = SelectedSamples.fingFromRequestAsFiles(request);
            ExportEncodeSamplesQueue.samples.addAll(titles);
            messagesService.add(Type.success, titles.size() + " samples added to export queue!");
            
        } else if (selectedAction.equalsIgnoreCase("delete_samples")){
            
            if (deleteSamples.equals("delete_not_selected")){
                int count = completeSamples.deleteNotSelectedSamples(folderName, request);
                statisticsInfo.removeFromCreated(count);
                statisticsInfo.addToProcessed(count);
                statisticsInfo.addToRemoved(count);
                messagesService.add(Type.warning, "'" + count + "' samples has deleted!");
                
            } else if (deleteSamples.equals("delete_samples_by_min_percent_limit")){
                int count = completeSamples.deleteSamplesByLimits(folderName);
                statisticsInfo.removeFromCreated(count);
                statisticsInfo.addToProcessed(count);
                statisticsInfo.addToRemoved(count);
                messagesService.add(Type.info, "Deleted '" + count + "' samples");
                
            } else if (deleteSamples.equals("delete_all_samples")){
                int count = completeSamples.deleteFolder(folderName);
                statisticsInfo.removeFromCreated(count);
                statisticsInfo.addToProcessed(count);
                statisticsInfo.addToRemoved(count);
                messagesService.add(Type.warning,  "'" + folderName + "' has deleted!");
                return "redirect:base_of_samples_not_sorted";
                
            } else {
                messagesService.add(Type.danger, "Sub action for 'delete_samples' is not determined!");
            }
            
        } else if (selectedAction.equalsIgnoreCase("add_to_samples_group")){
            
            ArrayList<String> samplesPath = completeSamples.getSelectedSamplesPaths(folderName, request);
            groups.addSamples(samplesPath, samplesGroup);
            messagesService.add(Type.success, "Samples added to group '" + samplesGroup + "' size=" + samplesPath.size());
            
        } else if (selectedAction.equalsIgnoreCase("add_folder_to_titles_group")){
            
            groups.addInGroup(titleGroup, folderName, GroupType.TITLES);
            CookieUtil.write("lastAddParamsForTitles", "folderName=" + folderName + "&title_group=" + titleGroup, response);
            messagesService.add(Type.info, folderName + " added to group '" + titleGroup + "'");
            
        } else {
            messagesService.add(Type.danger, "Action is not determined!");
        }
        
        return "redirect:samples_folder?folderName=" + folderName;
    }
}
