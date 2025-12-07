// DetalhesFilmeController.java

package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Filme;

// Importar Stage e Scene
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.GerenciadorDeFilmes;

import java.io.IOException;

public class DetalhesFilmeController {
    private GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();


    @FXML private Label tituloNotaLabel;
    @FXML private ImageView posterImageView;
    @FXML private TextArea reviewTextArea;
    @FXML private Button marcarAssistidoButton;
    @FXML private Button salvarReviewButton;

    private Filme filmeDetalhe; // Objeto Filme que será exibido

    @FXML
    public void initialize() {

    }

    // MÉTODO CRUCIAL: Recebe o objeto Filme da tela anterior
    // ESTA É A ÚNICA VEZ QUE O setFilme DEVE APARECER
    public void setFilme(Filme filme) {
        this.filmeDetalhe = filme;
        exibirDetalhes();
    }

    private void exibirDetalhes() {
        if (filmeDetalhe != null) {
            // 1. Título e Nota
            tituloNotaLabel.setText(filmeDetalhe.getTitulo() + " (" + filmeDetalhe.getNota() + " ★)");

            // 2. Poster
            if (filmeDetalhe.getLinkImagem() != null && !filmeDetalhe.getLinkImagem().isEmpty()) {
                posterImageView.setImage(new Image(filmeDetalhe.getLinkImagem(), true));
            }

            // 3. Review
            if (filmeDetalhe.getReview() != null) {
                reviewTextArea.setText(filmeDetalhe.getReview());
            }

            // 4. Lógica de estado dos botões (Ex: Se já foi assistido)
            if (filmeDetalhe.getStatus() == model.StatusFilme.ASSISTIDO) {
                marcarAssistidoButton.setText("REMOVER MARCAÇÃO ASSISTIDO");
            }
        }
    }

    @FXML
    private void handleVoltar() {
        try {
            // Carrega o FXML da Home Screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeScreen.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tituloNotaLabel.getScene().getWindow();

            // Troca a Scene para a Home Screen
            stage.setScene(new Scene(root, 800, 600)); // Usar o mesmo tamanho da Home Screen
            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a Home Screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML // <--- NOVO MÉTODO
    private void handleSalvarReview() {
        // 1. Obter o texto do TextArea
        String novaReview = reviewTextArea.getText();

        if (filmeDetalhe != null) {
            // 2. Chamar o método de edição no Gerenciador
            boolean sucesso = gerenciador.editarFilme(
                    filmeDetalhe.getId(),
                    filmeDetalhe.getTitulo(), // Passa o título atual (sem alteração)
                    filmeDetalhe.getNota(),   // Passa a nota atual
                    novaReview,               // NOVA REVIEW
                    filmeDetalhe.getStatus()  // Passa o status atual
            );

            if (sucesso) {
                // Opcional: Atualizar o objeto localmente para refletir a mudança
                filmeDetalhe.setReview(novaReview);
                System.out.println("Review do filme " + filmeDetalhe.getTitulo() + " salva com sucesso no JSON.");
            }
        }
    }

    // DetalhesFilmeController.java

    @FXML
    private void handleMarcarAssistido() {
        if (filmeDetalhe != null) {
            // 1. Determina o novo status
            model.StatusFilme novoStatus;
            boolean atualmenteAssistido = (filmeDetalhe.getStatus() == model.StatusFilme.ASSISTIDO);

            if (atualmenteAssistido) {
                // Se já está assistido, muda para PARA_ASSISTIR
                novoStatus = model.StatusFilme.PARA_ASSISTIR;
            } else {
                // Se está para assistir, muda para ASSISTIDO
                novoStatus = model.StatusFilme.ASSISTIDO;
            }

            // 2. Chama a lógica no Gerenciador (que salva automaticamente)
            boolean sucesso = gerenciador.editarFilme(
                    filmeDetalhe.getId(),
                    filmeDetalhe.getTitulo(),
                    filmeDetalhe.getNota(),
                    filmeDetalhe.getReview(),
                    novoStatus // Atualiza o status
            );

            if (sucesso) {
                // 3. Atualiza o objeto local e o texto do botão
                filmeDetalhe.setStatus(novoStatus);

                if (novoStatus == model.StatusFilme.ASSISTIDO) {
                    marcarAssistidoButton.setText("REMOVER MARCAÇÃO ASSISTIDO");
                } else {
                    marcarAssistidoButton.setText("MARCAR COMO ASSISTIDO");
                }

                System.out.println("DEBUG: Status do filme " + filmeDetalhe.getTitulo() + " atualizado para: " + novoStatus);
            } else {
                System.err.println("FALHA: Nao foi possivel editar o status do filme.");
            }
        }
    }



}