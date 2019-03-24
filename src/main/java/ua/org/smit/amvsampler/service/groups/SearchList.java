/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.service.groups;

import java.util.ArrayList;

/**
 *
 * @author smit
 */
class SearchList {

    static boolean isFound(String needFound, ArrayList<String> listForSearch) {
        for (String item : listForSearch){
            if (item.equals(needFound)){
                return true;
            }
        }
        return false;
    }
    
}
