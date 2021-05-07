package ucm.erikkarl.server;

import ucm.erikkarl.common.ServerInformation;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        System.out.println("Server");

        try (var serverSocket = new ServerSocket(ServerInformation.PORT))
        {
            var socket = serverSocket.accept();
            var inputFromClient = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
            var outputToClient = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            outputToClient.println("Welcome to my Minecraft Server! UwU");
            outputToClient.println("Now, tell me your name: ");
            var name = inputFromClient.nextLine();
            outputToClient.println("Welcome " + name + "!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
