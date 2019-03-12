/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.statistics;

/**
 *
 * @author smit
 */
public class StatisticsInfoImpl implements StatisticsInfoInterface{

    @Override
    public StatisticsInfo get() {
        return new StatisticsInfoService().get();
    }

    @Override
    public void addOneCreated() {
        new StatisticsInfoService().addOneCreated();
    }

    @Override
    public void addOneRemoved() {
        new StatisticsInfoService().addOneRemoved();
    }

    @Override
    public void addOneProcessed() {
        new StatisticsInfoService().addOneProcessed();
    }
    
    @Override
    public void addToProcessed(int samplesCount) {
        new StatisticsInfoService().addCreated(samplesCount);
    }

    @Override
    public void removeOneFromCreated() {
        new StatisticsInfoService().removeOneFromCreated();
    }

    @Override
    public void removeOneFromRemoved() {
        new StatisticsInfoService().removeOneFromRemoved();
    }

    @Override
    public void removeOneFromProcessed() {
        new StatisticsInfoService().removeOneFromProcessed();
    }

    @Override
    public void removeFromCreated(int samplesCount) {
        new StatisticsInfoService().removeFromCreated(samplesCount);
    }

    @Override
    public void addToRemoved(int count) {
        new StatisticsInfoService().addToRemoved(count);
    }
}
