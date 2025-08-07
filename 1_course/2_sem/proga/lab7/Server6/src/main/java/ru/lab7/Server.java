package ru.lab7;

import ru.lab7.Service.ConfigReader;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerApp server = new ServerApp(ConfigReader.getPortFromConfig("..\\config.json"));
        server.run();
    }
}
