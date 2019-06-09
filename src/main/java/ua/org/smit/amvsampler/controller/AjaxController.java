/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.controller;

import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.org.smit.amvsampler.service.splitanalyzeengine.EngineSplitAnalyze;

/**
 *
 * @author smit
 */
@Controller
public class AjaxController {

    @ResponseBody
    @RequestMapping(value = "/get_current_cut_info")
    public String getCurrentCutInfo() {
        if (EngineSplitAnalyze.isInProgress() && (EngineSplitAnalyze.getLastGif() != null)) {
            return "inProgress=true&"
                    + "fileName=" + EngineSplitAnalyze.getCurrentVideoFile().getName() + "&"
                    + "samplesCount=" + EngineSplitAnalyze.getSamplesCount() + "&"
                    + "ss=" + EngineSplitAnalyze.getSs() + "&"
                    + "lastGifPath=" + UUID.randomUUID().toString(); // kostil for cache
        }
        return "inProgress=false";
    }

}
