package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.net.Socket;
        import java.util.Scanner;

public class ClientThread {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 7000);

        System.out.println("== Cliente==");

        // Thread para enviar mensagens para o servidor
        Thread senderThread = new Thread(() -> {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("\nOpções disponíveis: readdir, rename, create, remove");
                    System.out.print("Digite um comando: ");
                    String comando = scanner.nextLine();
                    dos.writeUTF(comando);
                    dos.flush(); // Garante que a mensagem seja enviada imediatamente
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Thread para receber mensagens do servidor
        Thread receiverThread = new Thread(() -> {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                while (true) {
                    String mensagem = dis.readUTF();
                    System.out.println("Servidor: " + mensagem);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        senderThread.start();
        receiverThread.start();
    }
}

