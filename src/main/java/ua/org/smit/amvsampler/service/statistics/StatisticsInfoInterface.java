/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.statistics;

/**
 *
 * @author smit
 */
public interface StatisticsInfoInterface {
    
    StatisticsInfo get();
    void addOneCreated();
    void addOneRemoved();
    void addOneProcessed();
    void addToProcessed(int size);
    void removeOneFromCreated();
    void removeOneFromRemoved();
    void removeOneFromProcessed();
    void removeFromCreated(int samplesCount);

    public void addToRemoved(int count);
    
}
