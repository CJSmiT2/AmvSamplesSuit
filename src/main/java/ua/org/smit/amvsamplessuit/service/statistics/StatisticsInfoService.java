/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.statistics;

import ua.org.smit.amvsamplessuit.service.CompleteAndQueueListsImpl;


/**
 *
 * @author smit
 */
class StatisticsInfoService {
    
    StatisticsInfo get(){
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.setProcessedFilesCount(new CompleteAndQueueListsImpl().getCompletedFiles().size());
        return statisticsInfo;
    }
    
    void addOneCreated(){
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.addOneCreated();
        statisticsInfo.save();
    }
    
    void addCreated(int count) {
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.addCreated(count);
        statisticsInfo.save();
    }
    
    void addOneRemoved(){
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.addOneRemoved();
        statisticsInfo.save();
    }
    
    void addOneProcessed(){
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.addOneProcessed();
        statisticsInfo.save();
    }
    
    void removeOneFromCreated() {
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.removeOneFromCreated();
        statisticsInfo.save();
    }

    void removeOneFromRemoved() {
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.removeOneFromRemoved();
        statisticsInfo.save();
    }

    void removeOneFromProcessed() {
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.removeOneFromProcessed();
        statisticsInfo.save();
    }

    void removeFromCreated(int count) {
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.removeFromCreated(count);
        statisticsInfo.save();
    }

    void addToRemoved(int count) {
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        statisticsInfo.addToRemoved(count);
        statisticsInfo.save();
    }
    
}
