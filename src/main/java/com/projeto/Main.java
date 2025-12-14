package com.projeto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image; 
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/main-view.fxml"));
        Scene scene = new Scene(loader.load());

        String cssPath = getClass().getResource("/css/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        try {
            Image appIcon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            stage.getIcons().add(appIcon);
        } catch (Exception e) {
            System.out.println("Aviso: Ícone não encontrado ou erro ao carregar. Usando padrão.");
        }

        stage.setTitle("Gerenciador de Tarefas");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}