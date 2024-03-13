package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7000);
        System.out.println("==Servidor==");
        NFS nfs = new NFS();
        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado.");

        while(true) {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            // Lê a mensagem do cliente
            String mensagem = dis.readUTF();
            System.out.println("Mensagem recebida do cliente: " + mensagem);

            // Processa a mensagem e executa a função correspondente do NFS
            String resposta = processarMensagem(nfs, mensagem);

            // Envia a resposta de volta para o cliente
            dos.writeUTF(resposta);
            dos.flush();
        }
    }

    private static String processarMensagem(NFS nfs, String mensagem) {
        String[] partes = mensagem.split(" ");
        String comando = partes[0];
        switch (comando) {
            case "readdir":
                return nfs.readdir().toString();
            case "rename":
                if (partes.length == 3) {
                    String nomeAntigo = partes[1];
                    String novoNome = partes[2];
                    return String.valueOf(nfs.rename(nomeAntigo, novoNome));
                }
                break;
            case "create":
                if (partes.length == 2) {
                    String nomeArquivo = partes[1];
                    return String.valueOf(nfs.create(nomeArquivo));
                }
                break;
            case "remove":
                if (partes.length == 2) {
                    String nomeArquivo = partes[1];
                    return String.valueOf(nfs.remove(nomeArquivo));
                }
                break;
            default:
                return "Comando inválido";
        }
        return "Argumentos inválidos para o comando";
    }



}
