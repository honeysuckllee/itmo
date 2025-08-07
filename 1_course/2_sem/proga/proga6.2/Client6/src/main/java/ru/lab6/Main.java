package ru.lab6;

import ru.lab6.Service.ConfigReader;

public class Main {
    public static void main(String[] args) {
        Client client = new Client(ConfigReader.getPortFromConfig("C:\\Users\\Светлана\\IdeaProjects\\Client6\\src\\main\\java\\ru\\lab6\\Service\\config.json"));
        client.run();
    }
}