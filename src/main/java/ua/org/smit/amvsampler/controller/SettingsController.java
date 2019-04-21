/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.smit.amvsampler.messages.MessagesService;
import ua.org.smit.amvsampler.messages.Type;
import ua.org.smit.amvsampler.service.settings.Resolution;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.Access;

/**
 *
 * @author smit
 */
@Controller
public class SettingsController {

    @Autowired
    private Settings settingsService;
    @Autowired
    private MessagesService messagesService;

    @RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
    public String settings(Model model, HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());

        if (settingsService.isSrcFolderExist()) {
            model.addAttribute("srcFolder", settingsService.getSrcFolder());
        }
        if (settingsService.isBaseOfSamplesFolderExist()) {
            model.addAttribute("baseOfSamplesFolder", settingsService.getBaseOfSamplesFolder());
        }
        if (settingsService.isSexportFolderExist()) {
            model.addAttribute("exportFolder", settingsService.getExportFolder());
        }

        model.addAttribute("sampleLengthInSec", settingsService.getSampleLengthInSec());
        model.addAttribute("resolutionForGifAndAnalyzing", settingsService.getResolutionForGifAndAnalyzing());
        model.addAttribute("localhostOnly", settingsService.isLocalhostOnly());
        model.addAttribute("messages", messagesService.getMessagesAndClear());

        return "settings";
    }

    @RequestMapping(value = {"/save_settings"}, method = RequestMethod.GET)
    public String saveSettings(
            @RequestParam("src_folder") String srcFolderInput,
            @RequestParam("base_of_samples") String baseOfSamplesFolderInput,
            @RequestParam("export_folder") String exportFolderInput,
            @RequestParam("samples_length") int sampleLengthInSecInput,
            @RequestParam("resolution_gif_analyzing") String resolutionGifAnalyzingInput,
            @RequestParam("localhostOnly") String localhostOnly,
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());

        boolean hasErrors = false;

        if (srcFolderInput.equals(baseOfSamplesFolderInput)) {
            messagesService.add(Type.warning, "Folders cannot be equals!");
            hasErrors = true;
        }

        File srcFolder = new File(srcFolderInput);
        if (srcFolder.exists()) {
            settingsService.setSrcFolder(srcFolder);
        } else {
            messagesService.add(Type.warning, "Folder not exist - " + srcFolder);
            hasErrors = true;
        }

        File baseOfSamplesFolder = new File(baseOfSamplesFolderInput);
        if (baseOfSamplesFolder.exists()) {
            settingsService.setBaseOfSamplesFolder(baseOfSamplesFolder);
        } else {
            messagesService.add(Type.warning, "Folder not exist - " + baseOfSamplesFolder);
            hasErrors = true;
        }

        File exportFolder = new File(exportFolderInput);
        if (exportFolder.exists()) {
            settingsService.setExportFolder(exportFolder);
        } else {
            messagesService.add(Type.warning, "Folder not exist - " + exportFolder);
            hasErrors = true;
        }

        settingsService.setSampleLengthInSec(sampleLengthInSecInput);

        int width = Integer.valueOf(resolutionGifAnalyzingInput.split("\\*")[0]);
        int height = Integer.valueOf(resolutionGifAnalyzingInput.split("\\*")[1]);
        settingsService.setResolutionForGifAndAnalyzing(new Resolution(width, height));

        settingsService.setLocalhostOnly(Boolean.valueOf(localhostOnly));

        settingsService.saveSettingsInFile();

        if (!hasErrors) {
            messagesService.add(Type.success, "Settings saved!");
        }

        return "redirect:settings";
    }
}
