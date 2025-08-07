package ru.lab6.Commands;

import ru.lab6.Requests.Request;
import ru.lab6.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Command {

    public abstract Response execute(Request request, InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException;

}