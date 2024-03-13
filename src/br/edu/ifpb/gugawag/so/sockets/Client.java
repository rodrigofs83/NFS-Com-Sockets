package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        // cria um novo socket e o conecta ao endereço localhost na porta 7000
        Socket socket = new Socket("localhost", 7000);
        System.out.println("== Cliente ==");
        //enviar dados para o servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        //receber dados do servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        while(true) {

            System.out.println("\nOpções disponíveis: readdir, rename, create, remove");
            System.out.print("Digite um comando: ");
            //ler a entrada do usuário a partir do console.
            Scanner teclado = new Scanner(System.in);
            String opcao = teclado.nextLine();
            // enviando a mensagem para servidor
            dos.writeUTF(opcao);
          //  lendo mensagem do servidor
            String mensagem = dis.readUTF();
            System.out.println("Servidor falou: " + mensagem);

        }

    }
}
