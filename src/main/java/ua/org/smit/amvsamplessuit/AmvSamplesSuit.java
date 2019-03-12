/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit;

import ua.org.smit.amvsamplessuit.service.StartBrowser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.org.smit.amvsamplessuit.messages.MessagesService;
import ua.org.smit.amvsamplessuit.service.CompleteAndQueueListsImpl;
import ua.org.smit.amvsamplessuit.service.CompleteAndQueueListsInterface;
import ua.org.smit.amvsamplessuit.service.completesamples.CompleteSamplesImpl;
import ua.org.smit.amvsamplessuit.service.completesamples.CompleteSamplesInterface;
import ua.org.smit.amvsamplessuit.service.groups.GroupsImpl;
import ua.org.smit.amvsamplessuit.service.groups.GroupsInterface;
import ua.org.smit.amvsamplessuit.service.settings.Settings;
import ua.org.smit.amvsamplessuit.service.statistics.StatisticsInfoImpl;
import ua.org.smit.amvsamplessuit.service.statistics.StatisticsInfoInterface;
 
@Configuration
@SpringBootApplication
public class AmvSamplesSuit {
 
    public static void main(String[] args) {
        SpringApplication.run(AmvSamplesSuit.class, args);
    }
    
    @Bean
    public Settings getSettingsService(){
        return new Settings();
    }
    
    @Bean
    public MessagesService messagesService(){
        return new MessagesService();
    }
    
    @Bean
    public CompleteSamplesInterface completeSamples(){
        return new CompleteSamplesImpl();
    }
    
    @Bean
    public GroupsInterface groups(){
        return new GroupsImpl();
    }
    
    @Bean
    public CompleteAndQueueListsInterface completeAndQueueLists(){
        return new CompleteAndQueueListsImpl();
    }
    
    @Bean
    public StatisticsInfoInterface statisticsInfo(){
        return new StatisticsInfoImpl();
    }
    
    @Bean
    public StartBrowser startBrowser(){
        return new StartBrowser();
    }
}
