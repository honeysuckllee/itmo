package org.example.Control.Commands;

import org.example.Control.Command;
import org.example.Model.Deque;

public class Save implements Command {

    private Deque deq;

    public Save(Deque deque) {
        deq = deque;
    }

    @Override
    public void execute() {
        deq.save(deq.getCollectionFileName());
    }

}
