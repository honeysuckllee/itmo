package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Deque;
import org.example.Service.TransparentScannerWrapper;

import java.util.Scanner;

import static org.example.Service.Utilites.getValidInt;

public class RemoveLower implements Command {
    private Deque deque;
    private TransparentScannerWrapper scanner;
    private int id;

    public RemoveLower(TransparentScannerWrapper scanner, Deque deque, int id) {
        this.deque = deque;
        this.scanner = scanner;
        this.id = id;
    }

    @Override
    public void execute() {
        if (id == 0){
            id = getValidInt(scanner, "Введите id:");
        }
        deque.removeLower(id);
    }
}
