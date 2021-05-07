package ucm.erikkarl.client;

import ucm.erikkarl.common.ServerInformation;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        System.out.println("Client");
        var stdinScanner = new Scanner(System.in, StandardCharsets.UTF_8);

        try (var socket = new Socket(ServerInformation.IP, ServerInformation.PORT))
        {
            var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
            var output = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

            System.out.println(in.nextLine());
            System.out.println(in.nextLine());
            output.println(stdinScanner.nextLine());
            System.out.println(in.nextLine());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
