package com.lkprogramator.tool_images.utils.messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LKprogramator on 17.11.2018.
 */
public class LoggerMessage {

    private String message;
    private List<String> atributes;

    public LoggerMessage(String message) {
        this.message = message;
        this.atributes = new ArrayList<String>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getAtributes() {
        return atributes;
    }

    public void setAtributes(List<String> atributes) {
        this.atributes = atributes;
    }

    public LoggerMessage addValue(String key, String value) {

        this.atributes.add("\"" + key + "\":" + value + "");

        return this;

    }

    @Override
    public String toString() {

        if (atributes.isEmpty()) {
            return "{" + "\"message\":\"" + message + "\"}";
        } else {
            return "{" + "\"message\":\"" + message + "\"," + atributes.toString() + '}';
        }

    }



}
