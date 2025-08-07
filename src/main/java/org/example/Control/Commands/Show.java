package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Route;

import java.util.ArrayDeque;

public class Show implements Command {
    private ArrayDeque<Route> deque;

    public Show(ArrayDeque<Route> deque) {
        this.deque = deque;
    }

    @Override
    public void execute() {
        if (deque.isEmpty()){
            System.out.println("Коллекция пуста(");
        }
        else{
            for (Route route : deque) {
                System.out.println(route);
            }
        }
    }
}
