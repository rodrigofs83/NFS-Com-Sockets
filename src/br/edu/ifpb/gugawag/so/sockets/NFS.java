package br.edu.ifpb.gugawag.so.sockets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class NFS {
    private List<String> arquivos;
    private static final String RAIZ = System.getProperty("user.dir"); // Diretório raiz padrão relativo ao diretório do usuário
    private static final String PASTA_ARQUIVOS = "arquivos"; // Pasta onde os arquivos serão armazenados

    public NFS() {
        this.arquivos =new ArrayList<String>();
        File pastaArquivos = new File(RAIZ, PASTA_ARQUIVOS);
        if (!pastaArquivos.exists()) {
            if (pastaArquivos.mkdirs()) {
                System.out.println("Pasta de arquivos criada com sucesso.");
            } else {
                System.out.println("Falha ao criar a pasta de arquivos.");
            }
        }
        carregarListaArquivos();
    }

    // Método para criar um arquivo e adicionar seu caminho à lista
    public boolean create(String fileName) {
        String filePath =  RAIZ + File.separator + PASTA_ARQUIVOS + File.separator + fileName;
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                System.out.println("Arquivo criado com sucesso.");
                this.arquivos.add(file.getName());
                return true;
            } else {
                System.out.println("O arquivo já existe.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo: " + e.getMessage());
            return false;
        }
    }

    // Método para renomear um arquivo buscando pelo seu nome
    public boolean rename(String nome, String novoNome) {
        String caminhoArquivoAntigo = RAIZ + File.separator + PASTA_ARQUIVOS + File.separator + nome;
        String caminhoArquivoNovo = RAIZ + File.separator + PASTA_ARQUIVOS + File.separator + novoNome;

        File arquivoAntigo = new File(caminhoArquivoAntigo);
        File arquivoNovo = new File(caminhoArquivoNovo);

        if (arquivoAntigo.exists()) { // Verifica se o arquivo antigo existe
            if (arquivoAntigo.renameTo(arquivoNovo)) { // Renomeia o arquivo antigo para o novo nome
                // Atualiza o nome do arquivo na lista, se necessário

                if (arquivos.contains(caminhoArquivoAntigo)) {
                    int index = arquivos.indexOf(nome);
                    arquivos.set(index, novoNome); // Substitui pelo novo nome

                }
                System.out.println("Arquivo renomeado com sucesso.");
                return true;
            } else {
                System.out.println("Falha ao renomear o arquivo.");
                return false;
            }
        } else {
            System.out.println("Arquivo não encontrado.");
            return false;
        }
    }



    public List<String> readdir() {
        carregarListaArquivos();
        return this.arquivos;
    }

    public boolean remove(String nomeArquivo) {
        String caminhoArquivo = RAIZ + File.separator + PASTA_ARQUIVOS + File.separator + nomeArquivo;
        File arquivo = new File(caminhoArquivo);
        if (arquivo.exists()) {
            this.arquivos.remove(arquivo.getName());
            if (arquivo.delete()) {

                System.out.println("Arquivo removido com sucesso.");
                return true;
            } else {
                System.out.println("Falha ao remover o arquivo.");
                return false;
            }
        } else {
            System.out.println("Arquivo não encontrado.");
            return false;
        }
    }
        private void carregarListaArquivos(){
            File diretorio = new File(RAIZ + File.separator + PASTA_ARQUIVOS);
            File[] listaArquivos = diretorio.listFiles();
            List<String> arqNomes = new ArrayList<>();
            if (listaArquivos != null) {
                for (File arquivos : listaArquivos) {
                    if (arquivos.isFile()) {
                            arqNomes.add(arquivos.getName());
                    }
                }
                this.arquivos.clear();
                this.arquivos.addAll(arqNomes);
            } else {
                System.out.println("Não foi possível acessar o diretório.");
            }
    }


   /* public static void main(String[] args) {
        NFS nfs = new NFS();
        nfs.create("text1");
        //nfs.create("text3");
        //nfs.create("text6");
       // nfs.remove("text6");
       // nfs.remove("text10");
        //nfs.remove("text11");

        //nfs.rename("text1","text11");
        //System.out.println(nfs.readdir());
      //  nfs.rename("text","newText");
    }*/


}