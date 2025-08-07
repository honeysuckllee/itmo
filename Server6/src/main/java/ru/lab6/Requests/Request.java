package ru.lab6.Requests;
import lombok.Data;


import java.io.Serializable;

/**
 * Класс для запроса
 */
@Data
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
    }

    public boolean isEmpty(){
        return command.isEmpty();
    }
}
