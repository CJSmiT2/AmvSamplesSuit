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
import ua.org.smit.amvsamplessuit.service.CompleteAndQueueListsInterface;
import ua.org.smit.amvsamplessuit.service.queuemanager.CuttingFilesQueue;
import ua.org.smit.amvsamplessuit.service.splitanalyzeengine.EngineSplitAnalyze;
import ua.org.smit.amvsamplessuit.service.settings.Settings;
import ua.org.smit.amvsamplessuit.util.Access;

/**
 *
 * @author smit
 */
@Controller
public class QueueController {
    
        @Autowired
        private Settings settingsService;
        @Autowired
        private MessagesService messagesService;
        @Autowired
        private CompleteAndQueueListsInterface completeAndQueueList;

        @RequestMapping(value = { "/queue" })
        public String queue(Model model, HttpServletRequest request) {
            Access.check(request, settingsService.isLocalhostOnly());
            
            CuttingFilesQueue.instance();

            model.addAttribute("files", completeAndQueueList.getFilesFromQueue());
            model.addAttribute("messages", messagesService.getMessagesAndClear());
            return "queue";
        }

        @RequestMapping(value = { "/add_in_queue" }, method = RequestMethod.POST)
        public String addInQueue(HttpServletRequest request) {
            Access.check(request, settingsService.isLocalhostOnly());
            
            ArrayList<File> unprocessedFiles = completeAndQueueList.getUnprocessedVideoFiles();
            for (File videoFile : unprocessedFiles){
                String name = request.getParameter(videoFile.getName());
                if (name != null){
                    completeAndQueueList.addToQueueFile(videoFile);
                }
            }
            return "redirect:queue";
        }
        
        @RequestMapping(value = { "/add_all_in_queue"})
        public String addAllInQueue(HttpServletRequest request) {
            Access.check(request, settingsService.isLocalhostOnly());
            
            ArrayList<File> unprocessedFiles = completeAndQueueList.getUnprocessedVideoFiles();
            for (File videoFile : unprocessedFiles){
                completeAndQueueList.addToQueueFile(videoFile);
            }
            messagesService.add(Type.success, "In queue added " + unprocessedFiles.size() + " files!");
            return "redirect:queue";
        }
        
        @RequestMapping(value = {"/remove_from_queue" })
        public String removeFromQueue(
                @RequestParam("fileName") String videoFileName,
                HttpServletRequest request) {
            Access.check(request, settingsService.isLocalhostOnly());
            
            if (completeAndQueueList.isExistInQueueFiles(videoFileName)){
                completeAndQueueList.removeFromQueueFile(videoFileName);
                if (EngineSplitAnalyze.isInProgress() 
                        && EngineSplitAnalyze.getCurrentVideoFile().getName().equals(videoFileName)){
                    EngineSplitAnalyze.cancel();
                }
                
                messagesService.add(Type.success, "File '" + videoFileName + "' has removed from queue.");
            } else {
                messagesService.add(Type.warning, "File '" + videoFileName + "' cannot removed from queue, because cannot found!");
            }

            return "redirect:queue";
        }
        
        @RequestMapping(value = { "/remove_all_from_queue" })
        public String removeAllFromQueue(HttpServletRequest request) {
            Access.check(request, settingsService.isLocalhostOnly());
            
            ArrayList<File> filesFromQueue = completeAndQueueList.getFilesFromQueue();
            for (File videoFile : filesFromQueue){
                completeAndQueueList.removeFromQueueFile(videoFile.getName());
                if (EngineSplitAnalyze.isInProgress() 
                        && EngineSplitAnalyze.getCurrentVideoFile().getName().equals(videoFile.getName())){
                    EngineSplitAnalyze.cancel();
                }
            }
            
            messagesService.add(Type.success, "From queue removed " + filesFromQueue.size() + " files!");
            
            return "redirect:queue";
        }
    
}
