package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import model.GerenciadorDeFilmes;
import model.Filme;
import model.StatusFilme;

public class FiltrarFilmesController {

    // Variáveis ligadas ao FXML
    @FXML private TilePane tilePaneFilmes;
    @FXML private Label tituloTelaLabel;

    // Acessa o Gerenciador de Dados
    private GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();

    // Estado do filtro (guardado para futura referência)
    private StatusFilme statusAtual;


    /**
     * Recebe o status do filme que deve ser exibido (chamado pelo HomeScreenController).
     */
    public void setStatus(StatusFilme status) {
        this.statusAtual = status;
        carregarFilmesFiltrados(status);
    }

    private void carregarFilmesFiltrados(StatusFilme status) {
        // 1. Define o título da tela
        if (status == StatusFilme.ASSISTIDO) {
            tituloTelaLabel.setText("FILMES ASSISTIDOS");
        } else {
            tituloTelaLabel.setText("FILMES PARA ASSISTIR");
        }

        // 2. Obtém a lista filtrada
        java.util.List<Filme> filmes = gerenciador.listarPorStatus(status);

        // 3. Limpa e adiciona os cards
        tilePaneFilmes.getChildren().clear();
        for (Filme filme : filmes) {
            VBox card = criarCardFilme(filme);
            tilePaneFilmes.getChildren().add(card);
        }
    }

    /**
     * MÉTODOS DE CRIAÇÃO DE CARD (Copiado do HomeScreenController)
     */
    private VBox criarCardFilme(Filme filme) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #333333; -fx-border-color: #F8B31C;");

        ImageView imagem = new ImageView();
        Image poster = null;
        String imageUrl = filme.getLinkImagem();

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            try {
                poster = new Image(imageUrl, true);
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

        // Ação de clique para abrir detalhes
        card.setOnMouseClicked(event -> {
            // Você pode reutilizar a lógica de navegação aqui se quiser!
        });

        card.getChildren().addAll(imagem, tituloLabel, notaLabel);
        return card;
    }
}