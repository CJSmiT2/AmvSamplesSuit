/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.smit.amvsampler.messages.MessagesService;
import ua.org.smit.amvsampler.messages.Type;
import ua.org.smit.amvsampler.service.OpenExportFolderInWindow;
import ua.org.smit.amvsampler.service.completesamples.CompleteSamplesInterface;
import ua.org.smit.amvsampler.service.completesamples.Sample;
import ua.org.smit.amvsampler.service.exportsamples.ExportSamplesService;
import ua.org.smit.amvsampler.service.groups.GroupType;
import ua.org.smit.amvsampler.service.groups.GroupsInterface;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.Console;
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
    private ExportSamplesService exportSamplesService;

    @RequestMapping(value = {"process_selected"})
    public String processSelected(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "folderName") String folderName,
            @RequestParam(value = "selected_action") String selectedAction,
            @RequestParam(value = "samples_group", required = false) String samplesGroup,
            @RequestParam(value = "title_group", required = false) String titleGroup,
            @RequestParam(value = "delete_samples", required = false) String deleteSamples) {

        if (selectedAction.equalsIgnoreCase("export_selected")) {

            ArrayList<File> samplesMp4Paths = SelectedSamples.fingFromRequestAsFiles(request);
            List<Sample> samplesExport = completeSamples.getSamplesByMP4(samplesMp4Paths);
            exportSamplesService.export(samplesExport, Settings.getExportFolder());
            messagesService.add(Type.success, samplesMp4Paths.size() + " samples success esported!");

        } else if (selectedAction.equalsIgnoreCase("delete_samples")) {

            if (deleteSamples.equals("delete_not_selected")) {
                int count = completeSamples.deleteNotSelectedSamples(folderName, request);
                messagesService.add(Type.warning, "'" + count + "' samples has deleted!");

            } else if (deleteSamples.equals("delete_samples_by_min_percent_limit")) {
                int count = completeSamples.deleteSamplesByLimits(folderName);
                messagesService.add(Type.info, "Deleted '" + count + "' samples");

            } else if (deleteSamples.equals("delete_all_samples")) {
                int count = completeSamples.deleteFolder(folderName);
                messagesService.add(Type.warning, "'" + folderName + "' has deleted!");
                return "redirect:base_of_samples_not_sorted";

            } else {
                messagesService.add(Type.danger, "Sub action for 'delete_samples' is not determined!");
            }

        } else if (selectedAction.equalsIgnoreCase("add_to_samples_group")) {

            ArrayList<String> samplesPath = completeSamples.getSelectedSamplesPaths(folderName, request);
            groups.addSamples(samplesPath, samplesGroup);
            messagesService.add(Type.success, "Samples added to group '" + samplesGroup + "' size=" + samplesPath.size());

        } else if (selectedAction.equalsIgnoreCase("add_folder_to_titles_group")) {

            groups.addInGroup(titleGroup, folderName, GroupType.TITLES);
            CookieUtil.write("lastTitleGroup", titleGroup, response);
            messagesService.add(Type.info, folderName + " added to group '" + titleGroup + "'");

        } else {
            messagesService.add(Type.danger, "Action is not determined!");
        }

        return "redirect:samples_folder?folderName=" + folderName;
    }

    @RequestMapping(value = "/open_export_sample_folder_in_explorer/{sampleFolder}")
    public String openExportSampleFolderInExplorer(@PathVariable String sampleFolder) {
        new OpenExportFolderInWindow(Settings.getExportFolder() + File.separator + sampleFolder);
        return "ok";
    }
}
