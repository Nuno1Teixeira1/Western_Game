package org.bootcamp;

/**
 * Game developed by <Academia_de_Código_> cadets:
 * Igor Koury
 * Valentim Garcia
 * Yevheniy Shevchuk
 * Nuno Teixeira
 */

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

public class Server {

    private final int PORT_NUMBER = 8080;
    private Vector<ClientHandler> playersList = new Vector<>();
    private ClientHandler clientHandler;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {

            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server Port: " + PORT_NUMBER + "\n" + serverSocket);

            while (playersList.size() < 2) {
                Socket clientSocket = serverSocket.accept();
                Prompt prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
                StringInputScanner playerName = new StringInputScanner();
                playerName.setMessage("\nType your player name: ");
                String username = prompt.getUserInput(playerName);
                clientHandler = new ClientHandler(clientSocket);
                clientHandler.name = username;

                playersList.addElement(clientHandler);
                System.out.println("Player " + playersList.size() + " connected.");
            }

            for (ClientHandler ch : playersList) {
                Thread thread = new Thread(ch);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {

        private Socket clientSocket;
        private PrintStream printStream;
        private String name;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }

        @Override
        public void run() {
            System.out.println(clientSocket);
            asciiWestern();
            Thread.currentThread().setName(name);

            if (playersList.size() == 2) {
                tellEveryone("Welcome " + Thread.currentThread().getName() + ". Ready Yourself!");
                timer();
            }
            fight();
        }

        public void timer() {
            try {
                for (int i = 5; i > 0; i--) {
                    tellEveryone("" + i);
                    //antiCheat();
                    Thread.sleep(1000);
                }
                tellEveryone("FIRE!!!\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void fight() {
            try {

                long startTime = System.currentTimeMillis();
                String action = in.readLine();

                if (action.equals("")) {

                    long stopTime = System.currentTimeMillis();
                    long reactionTime = stopTime - startTime;

                    dontTell("You Lost :(\n" + Thread.currentThread().getName() + " won the duel! With: " + reactionTime + "ms\n", this);
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    writer.println("\nYou Won! With: " + reactionTime + "ms");
                    System.exit(1);
                } else {
                    tellEveryone(Thread.currentThread().getName() + " missed the shot.\n");
                    System.exit(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //fix
        public void antiCheat() {
            try {
                String action = in.readLine();

                if (action.equals("")) {
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    writer.println("You Lost!\nLet the timer end!");
                    tellEveryone(Thread.currentThread().getName() + " tried to shoot before the timer ended.");
                    System.exit(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void tellEveryone(String message) {
            for (ClientHandler clientHandler : playersList) {
                try {
                    if (this.equals(clientHandler)) {
                        continue;
                    }
                    PrintWriter writer = new PrintWriter(clientHandler.clientSocket.getOutputStream(), true);
                    writer.println(message);
                } catch (FileNotFoundException e) {
                    System.err.println("Something went wrong with the input of: " + Thread.currentThread().getName() + "\n" + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Something went wrong with: " + Thread.currentThread().getName() + "\n" + e.getMessage());
                }
            }
        }

        public void dontTell(String message, ClientHandler c) {
            for (ClientHandler clientHandler : playersList) {
                try {
                    PrintWriter writer = new PrintWriter(clientHandler.clientSocket.getOutputStream(), true);
                    if (!clientHandler.equals(c)) {
                        writer.println(message);
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("Something went wrong with the input of: " + Thread.currentThread().getName() + "\n" + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Something went wrong with: " + Thread.currentThread().getName() + "\n" + e.getMessage());
                }
            }
        }

        public void asciiWestern() {
            try {
                printStream = new PrintStream(clientSocket.getOutputStream());
                printStream.write("\n".getBytes(StandardCharsets.UTF_8));
                printStream.write("░██╗░░░░░░░██╗███████╗░██████╗████████╗███████╗██████╗░███╗░░██╗\n".getBytes(StandardCharsets.UTF_8));
                printStream.write("░██║░░██╗░░██║██╔════╝██╔════╝╚══██╔══╝██╔════╝██╔══██╗████╗░██║\n".getBytes(StandardCharsets.UTF_8));
                printStream.write("░╚██╗████╗██╔╝█████╗░░╚█████╗░░░░██║░░░█████╗░░██████╔╝██╔██╗██║\n".getBytes(StandardCharsets.UTF_8));
                printStream.write("░░████╔═████║░██╔══╝░░░╚═══██╗░░░██║░░░██╔══╝░░██╔══██╗██║╚████║\n".getBytes(StandardCharsets.UTF_8));
                printStream.write("░░╚██╔╝░╚██╔╝░███████╗██████╔╝░░░██║░░░███████╗██║░░██║██║░╚███║\n".getBytes(StandardCharsets.UTF_8));
                printStream.write("░░░╚═╝░░░╚═╝░░╚══════╝╚═════╝░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╚═╝░░╚══╝\n".getBytes(StandardCharsets.UTF_8));
                printStream.write("\nWelcome to the Western!\nThese are the game rules:\n--> The game requires 2 players.\n--> The 1st player to press ENTER wins.\n".getBytes(StandardCharsets.UTF_8));
                Thread.sleep(8000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}