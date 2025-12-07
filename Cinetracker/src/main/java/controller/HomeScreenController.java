package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import model.GerenciadorDeFilmes;
import model.Filme;


public class HomeScreenController {

    private GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();

    @FXML
    private TilePane tilePaneFilmes; // Variável ligada ao FXML

    @FXML
    public void initialize() {
        carregarFilmes();
    }

    private void carregarFilmes() {
        java.util.List<Filme> filmes = gerenciador.listarFilmes();
        tilePaneFilmes.getChildren().clear();

        for (Filme filme : filmes) {
            VBox card = criarCardFilme(filme);
            tilePaneFilmes.getChildren().add(card);
        }
    }

    private VBox criarCardFilme(Filme filme) {
        VBox card = new VBox(5);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setStyle("-fx-background-color: #333333; -fx-border-color: #F8B31C;");

        ImageView imagem = new ImageView();
        Image poster = null;
        String imageUrl = filme.getLinkImagem();

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            try {
                poster = new Image(imageUrl, true); // Carregamento assíncrono
            } catch (Exception e) {
                System.err.println("Falha de URL para " + filme.getTitulo() + ": " + e.getMessage());
            }
        }

        if (poster != null) {
            imagem.setImage(poster);
        } else {
            imagem.setStyle("-fx-background-color: black;");
        }

        imagem.setFitWidth(150);
        imagem.setFitHeight(250);

        Label tituloLabel = new Label(filme.getTitulo());
        tituloLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        Label notaLabel = new Label(filme.getNota() + " ★");
        notaLabel.setTextFill(javafx.scene.paint.Color.YELLOW);

        card.setOnMouseClicked(event -> {
            abrirDetalhesFilme(filme); // Chama o método de navegação
        });

        card.getChildren().addAll(imagem, tituloLabel, notaLabel);
        return card;
    }

    private void abrirDetalhesFilme(Filme filme) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetalhesFilme.fxml"));
            Parent root = loader.load();
            DetalhesFilmeController controller = loader.getController();
            controller.setFilme(filme);

            Stage stage = (Stage) tilePaneFilmes.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de detalhes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===============================================
    // LÓGICA DE NAVEGAÇÃO PARA FILTROS E HOME (MENU INFERIOR)
    // ===============================================

    @FXML // Chamado pelo Label 'HOME'
    private void handleNavegacaoHome() {
        System.out.println("Navegando para Home.");
    }

    @FXML // Chamado pelo Label 'SEARCH'
    private void handleNavegacaoAssistidos() {
        abrirTelaFiltrada(model.StatusFilme.ASSISTIDO);
    }


    private void abrirTelaFiltrada(model.StatusFilme status) {
        try {
            // Usa o FXML da tela de filtro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FiltroScreen.fxml"));
            Parent root = loader.load();

            FiltrarFilmesController controller = loader.getController();
            controller.setStatus(status);

            Stage stage = (Stage) tilePaneFilmes.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela filtrada: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAddNovoFilme() {
        // ESTE MÉTODO VAI, FUTURAMENTE, ABRIR A TELA DE ADICIONAR FILME
        System.out.println("DEBUG: Botão Adicionar Clicado.");
    }
}
