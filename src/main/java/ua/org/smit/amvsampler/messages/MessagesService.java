/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.org.smit.amvsampler.messages;

import java.util.ArrayList;

/**
 *
 * @author smit
 */
public class MessagesService {
    
    private final ArrayList<Message> messages = new ArrayList();
    
    public void add(Type type, String text){
        this.messages.add(new Message(type, text));
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
    public ArrayList<Message> getMessagesAndClear() {
        ArrayList<Message> messagesOutput = (ArrayList<Message>) messages.clone();
        messages.clear();
        return messagesOutput;
    }
    
    
}
