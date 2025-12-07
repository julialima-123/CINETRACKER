package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeFilmes {
    private List<Filme> filmes;

    // CONSTANTES DE CAMINHO
    private static final String ARQUIVO_JSON_DISCO = "meus_filmes.json"; // Para SALVAR no disco (raiz do projeto)
    private static final String NOME_RECURSO_CLASSPATH = "/view/meus_filmes.json"; // Para CARREGAR (leitura)

    private final Gson gson;

    // construtor
    public GerenciadorDeFilmes() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.filmes = carregarDados(); // carrega ao iniciar
    }

    /**
     * Tenta carregar os dados do JSON usando o Classpath (Recursos) ou diretamente do Disco.
     */
    private List<Filme> carregarDados() {
        // 1. Tenta ler o JSON do Classpath (Recursos Internos)
        try (InputStream inputStream = getClass().getResourceAsStream(NOME_RECURSO_CLASSPATH)) {

            if (inputStream != null) {
                try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                    Type listType = new TypeToken<ArrayList<Filme>>() {}.getType();
                    List<Filme> dados = gson.fromJson(reader, listType);
                    System.out.println("DEBUG: Filmes carregados do CLASSPATH com sucesso.");
                    return (dados != null) ? dados : new ArrayList<>();
                }
            }
        } catch (Exception e) {
            // Ignora falha de leitura via Classpath e tenta o disco
            System.err.println("Aviso: Falha ao ler JSON do Classpath. Tentando Disco...");
        }

        // 2. Tenta ler o JSON do Disco (onde o arquivo é salvo)
        return carregarDadosDoDisco();
    }

    /**
     * Tenta carregar dados diretamente do disco (útil se o arquivo foi criado pelo programa).
     */
    private List<Filme> carregarDadosDoDisco() {
        File arquivo = new File(ARQUIVO_JSON_DISCO);

        if (!arquivo.exists() || arquivo.length() == 0) {
            System.err.println("Arquivo JSON não encontrado ou vazio no disco. Iniciando com lista vazia.");
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(ARQUIVO_JSON_DISCO)) {
            Type listType = new TypeToken<ArrayList<Filme>>() {}.getType();
            List<Filme> dados = gson.fromJson(reader, listType);
            System.out.println("DEBUG: Filmes carregados do DISCO com sucesso.");
            return (dados != null) ? dados : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON do disco: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Salva a lista de filmes no arquivo JSON no disco (usa ARQUIVO_JSON_DISCO).
     */
//    private void salvarDados() {
//        try (Writer writer = new FileWriter(ARQUIVO_JSON_DISCO)) {
//            gson.toJson(filmes, writer);
//        } catch (IOException e) {
//            System.err.println("Erro ao salvar o arquivo JSON: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    // GerenciadorDeFilmes.java (Substitua este método)

//    private void salvarDados() {
//        System.out.println("DEBUG: Tentando salvar no disco em: " + ARQUIVO_JSON_DISCO);
//        try (Writer writer = new FileWriter(ARQUIVO_JSON_DISCO)) {
//            gson.toJson(filmes, writer);
//            System.out.println("SUCESSO: Escrita no JSON concluída.");
//        } catch (IOException e) {
//            // Lança uma exceção para o console para identificar a FALHA DE PERMISSÃO
//            System.err.println("FALHA CRÍTICA DE ESCRITA: Nao foi possivel salvar o JSON. Verifique permissões.");
//            e.printStackTrace(); // <-- Isto nos dirá se é permissão ou path
//        }
//    }

    // GerenciadorDeFilmes.java (Substitua este método)

    private void salvarDados() {
        // Usamos ARQUIVO_JSON_DISCO, que deve apontar para a raiz do projeto.
        try (FileWriter fileWriter = new FileWriter(ARQUIVO_JSON_DISCO);
             BufferedWriter writer = new BufferedWriter(fileWriter)) { // Usamos BufferedWriter

            gson.toJson(filmes, writer);

            writer.flush(); // Força a gravação imediata no arquivo antes de fechar.

            System.out.println("DEBUG: Escrita concluída no disco. Verifique o JSON.");

        } catch (IOException e) {
            System.err.println("FALHA CRÍTICA DE ESCRITA: Nao foi possivel salvar a review.");
            e.printStackTrace();
        }
    }

    // LISTAR TODOS OS FILMES
    public List<Filme> listarFilmes() {
        return new ArrayList<>(filmes);
    }

    // DIVIDIR AS LISTAS DE FILME DE ACORDO COM SEU STATUS
    public List<Filme> listarPorStatus(StatusFilme statusDesejado) {
        List<Filme> resultado = new ArrayList<>();
        for (Filme f : filmes) {
            if (f.getStatus() == statusDesejado) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    public void adicionarFilme(Filme filme) {
        filmes.add(filme);
        salvarDados();
    }

    public void removerFilme(Filme filme) {
        filmes.remove(filme);
        salvarDados();
    }

    public boolean editarFilme(String id, String novoTitulo, double novaNota, String novaReview, StatusFilme novoStatus) {
        for (Filme f : filmes) {
            if (f.getId().equals(id)) {
                if (novoTitulo != null && !novoTitulo.isEmpty()) f.setTitulo(novoTitulo);
                if (novaNota >= 0) f.setNota(novaNota);
                if (novaReview != null) f.setReview(novaReview);
                if (novoStatus != null) f.setStatus(novoStatus);
                salvarDados();
                return true;
            }
        }
        return false;
    }

    public boolean marcarComoAssistido (String id,boolean assistido){
        for (Filme f : filmes) {
            if (f.getId().equals(id)) {
                if (assistido) {
                    f.setStatus(StatusFilme.ASSISTIDO);
                } else {
                    f.setStatus(StatusFilme.PARA_ASSISTIR);
                }
                salvarDados();
                return true;
            }
        }
        return false;
    }
}

