package ru.lab6;

import ru.lab6.Commands.*;
import  ru.lab6.Commands.Command;
import  ru.lab6.Requests.Request;
import  ru.lab6.exceptions.CommandException;
import ru.lab6.Model.Deque;
import ru.lab6.Response;
import lombok.Data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;



@Data
public class ServerApp {
    private static int port;
    private static HashMap<String, Command> commandHashMap;
    private static Deque deq;
    public static final Logger logger = Logger.getLogger("Server");

    public ServerApp(int port) throws IOException {
        this.port = port;
        Handler handler = new FileHandler("log.txt");
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public static void initCommandsMap(){

        deq = new Deque();

        commandHashMap = new HashMap<String, Command>();
        commandHashMap.put("help", new Help());
        commandHashMap.put("add", new Add(deq));
        commandHashMap.put("show", new Show(deq));
        commandHashMap.put("info", new Info(deq));
        commandHashMap.put("remove_first", new RemoveFirst(deq));
        commandHashMap.put("remove_lower", new RemoveLower(deq));
        commandHashMap.put("remove_by_id", new RemoveById(deq));
        commandHashMap.put("add_if_max", new AddIfMax(deq));
        commandHashMap.put("clear", new Clear(deq));
        commandHashMap.put("filter_starts_with_name", new FilterStartsWithName(deq));
        commandHashMap.put("print_unique_distance", new PrintUniqueDistance(deq));
        commandHashMap.put("print_field_descending_distance", new PrintFieldDescendingDistance(deq));
        commandHashMap.put("update", new Update(deq));
    }

    public static void run() throws IOException, ClassNotFoundException {
        initCommandsMap();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сервер начал работу.");
        logger.info("Сервер начал работу.");

        // Создаем серверный сокет и привязываем его к порту
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Сервер ожидает подключения на порту " + port);

        // Поток для обработки команд "exit"
        Thread exitThread = new Thread(() -> {
            while (true) {
                try {
                    String line = scanner.nextLine();
                    if (line.equals("exit")) {
                        logger.info("Сервер завершил работу.");
                        System.exit(0); //программа завершилась
                    }
                } catch (NoSuchElementException e) {
                    System.err.println("Не тыкай Ctrl+D\n");
                }
            }
        });
        exitThread.start(); //запуск потока

        // Основной цикл обработки клиентских подключений
        while (true) {
            // Ожидаем подключения клиента
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

            // Получаем входной и выходной потоки для обмена данными с клиентом
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            try {
                // Основной цикл обработки запросов от клиента
                while (true) {
                    // Чтение данных от клиента
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Request request;
                    try {
                        request = (Request) objectInputStream.readObject();
                    } catch (EOFException e) {
                        // Клиент отключился
                        System.out.println("Клиент отключился: " + clientSocket.getInetAddress());
                        break;
                    }

                    String command = request.getCommand();
                    Response response = null;
                    try {
                        if (commandHashMap.containsKey(command)) {
                            response = commandHashMap.get(command).execute(request, inputStream, outputStream);

                        } else {
                            System.err.println("Команда " + command + " не найдена.\n" );
                            response = new Response("Команда не найдена\n", true);
                        }
                    } finally {
                        if (response != null){
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                            objectOutputStream.writeObject(response);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при работе с клиентом: " + e.getMessage());
            } finally {
                // Закрываем соединение с клиентом
                clientSocket.close();
                System.out.println("Соединение с клиентом закрыто.");
            }
        }
    }

}

