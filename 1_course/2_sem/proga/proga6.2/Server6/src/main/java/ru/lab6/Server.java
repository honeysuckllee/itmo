package ru.lab6;

import ru.lab6.Service.ConfigReader;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerApp server = new ServerApp(ConfigReader.getPortFromConfig("C:\\Users\\Светлана\\IdeaProjects\\Server6\\src\\main\\java\\ru\\lab6\\Service\\config.json"));
        server.run();
    }
}