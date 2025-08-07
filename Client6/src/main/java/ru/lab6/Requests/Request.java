package ru.lab6.Requests;


import java.io.Serializable;

/**
 * Базовый класс для запроса
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private String command;
    private String arg;
    private boolean isScript;

    public Request(String command,String arg, boolean isScript) {
        this.command = command;
        this.arg = arg;
        this.isScript = isScript;
    }

    public Request() {
        this.command = "";
        this.arg = "";
        this.isScript = false;
    }

    public boolean isEmpty(){
        if (command == null){
            return true;
        }
        return command.isEmpty();
    }
}
