/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsamplessuit.service.groups;

/**
 *
 * @author smit
 */
public class GroupInfo {
    
    private String name;
    private int groupSize;

    public GroupInfo(String name, int groupSize) {
        this.name = name;
        this.groupSize = groupSize;
    }

    public String getName() {
        return name;
    }

    public int getGroupSize() {
        return groupSize;
    }
}
