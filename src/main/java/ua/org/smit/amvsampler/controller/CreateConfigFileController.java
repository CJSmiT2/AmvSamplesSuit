/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.io.File;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.org.smit.amvsampler.messages.MessagesService;
import ua.org.smit.amvsampler.messages.Type;
import ua.org.smit.amvsampler.service.settings.Settings;
import ua.org.smit.amvsampler.util.Access;

/**
 *
 * @author smit
 */
@Controller
public class CreateConfigFileController {

    @Autowired
    private Settings settingsService;
    @Autowired
    private MessagesService messagesService;

    @RequestMapping(value = {"/create_config_file_step1"}, method = RequestMethod.GET)
    public String step1(Model model, HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        return "create_config_file/step1";
    }

    @RequestMapping(value = {"/create_config_file_step1"}, method = RequestMethod.POST)
    public String step1Action(
            @RequestParam("src_folder") String srcFolderInput,
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());

        File srcFolder = new File(srcFolderInput);
        if (srcFolder.exists()) {
            if (!isHaveSpaces(srcFolder)) {
                Settings.setSrcFolder(srcFolder);
                return "redirect:create_config_file_step2";
            } else {
                messagesService.add(Type.warning, "Path must not contain spaces!");
                return "redirect:create_config_file_step1";
            }
        } else {
            messagesService.add(Type.warning, "Folder not exist '" + srcFolder + "'");
            return "redirect:create_config_file_step1";
        }
    }

    @RequestMapping(value = {"/create_config_file_step2"}, method = RequestMethod.GET)
    public String step2(Model model, HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        return "create_config_file/step2";
    }

    @RequestMapping(value = {"/create_config_file_step2"}, method = RequestMethod.POST)
    public String step2Action(
            @RequestParam("base_of_samples") String baseOfSamplesFolderInput,
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());

        File baseOfSamples = new File(baseOfSamplesFolderInput);
        if (baseOfSamples.exists()) {
            if (!isHaveSpaces(baseOfSamples)) {
                Settings.setBaseOfSamplesFolder(baseOfSamples);
                return "redirect:create_config_file_step3";
            } else {
                messagesService.add(Type.warning, "Path must not contain spaces!");
                return "redirect:create_config_file_step2";
            }
        } else {
            messagesService.add(Type.warning, "Folder not exist '" + baseOfSamples + "'");
            return "redirect:create_config_file_step2";
        }
    }

    @RequestMapping(value = {"/create_config_file_step3"}, method = RequestMethod.GET)
    public String step3(Model model, HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        return "create_config_file/step3";
    }

    @RequestMapping(value = {"/create_config_file_step3"}, method = RequestMethod.POST)
    public String step3Action(
            @RequestParam("export_folder") String exportFolderInput,
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());

        File exportFolder = new File(exportFolderInput);
        if (exportFolder.exists()) {
            if (!isHaveSpaces(exportFolder)) {
                Settings.setExportFolder(exportFolder);
                return "redirect:create_config_file_step4";
            } else {
                messagesService.add(Type.warning, "Path must not contain spaces!");
                return "redirect:create_config_file_step3";
            }
        } else {
            messagesService.add(Type.warning, "Folder not exist '" + exportFolder + "'");
            return "redirect:create_config_file_step3";
        }
    }

    @RequestMapping(value = {"/create_config_file_step4"}, method = RequestMethod.GET)
    public String step4(Model model, HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        model.addAttribute("messages", messagesService.getMessagesAndClear());
        return "create_config_file/step4";
    }

    @RequestMapping(value = {"/create_config_file_step4"}, method = RequestMethod.POST)
    public String step4Action(
            @RequestParam("nickname") String nickName,
            HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());

        Settings.setNickName(nickName);
        Settings.setUuid(UUID.randomUUID().toString());

        if (Settings.isValid()) {
            Settings.saveSettingsInFile();
            messagesService.add(Type.success, "Config file success created!");
            return "redirect:/";
        } else {
            messagesService.add(Type.warning, "Config file is not valid. You has not unique folders paths!");
            return "redirect:create_config_file_step1";
        }
    }

    private boolean isHaveSpaces(File srcFolder) {
        if (srcFolder.getAbsolutePath().toString().contains(" ")) {
            return true;
        }
        return false;
    }

}
