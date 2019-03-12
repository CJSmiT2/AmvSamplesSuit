/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.org.smit.amvsamplessuit.service.encodersamples.ExportEncodeSamplesQueue;
import ua.org.smit.amvsamplessuit.service.splitanalyzeengine.EngineSplitAnalyze;
import ua.org.smit.amvsamplessuit.util.Base64Util;

/**
 *
 * @author smit
 */
@Controller
public class AjaxController {
        
        @ResponseBody 
        @RequestMapping(value ="/get_current_cut_info")
        public String getCurrentCutInfo(){
            if (EngineSplitAnalyze.isInProgress() && (EngineSplitAnalyze.getLastGif() != null) ){
                return "inProgress=true&"
                        + "fileName=" + EngineSplitAnalyze.getCurrentVideoFile().getName() + "&"
                        + "samplesCount=" + EngineSplitAnalyze.getSamplesCount() + "&"
                        + "ss=" + EngineSplitAnalyze.getSs() + "&"
                        + "lastGifPath=" + new Base64Util().encode(EngineSplitAnalyze.getLastGif().getAbsolutePath());
            }
            return "inProgress=false";
        }
        
        @ResponseBody 
        @RequestMapping(value ="/get_current_export_queue_size")
        public String getCurrentExportQueueSize(){
            ExportEncodeSamplesQueue.instance();
            return String.valueOf(ExportEncodeSamplesQueue.samples.size());
        }
        
}
