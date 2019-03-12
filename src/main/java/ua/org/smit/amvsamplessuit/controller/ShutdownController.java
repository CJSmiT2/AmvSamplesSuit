/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.org.smit.amvsamplessuit.service.settings.Settings;
import ua.org.smit.amvsamplessuit.util.Access;

/**
 *
 * @author smit
 */
@RestController
public class ShutdownController implements ApplicationContextAware {
    
    @Autowired
    private Settings settingsService;
     
    private ApplicationContext context;
     
    @GetMapping("/shutdown")
    public void shutdownContext(HttpServletRequest request) {
        Access.check(request, settingsService.isLocalhostOnly());
        ((ConfigurableApplicationContext) context).close();
    }
 
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
         
    }
}