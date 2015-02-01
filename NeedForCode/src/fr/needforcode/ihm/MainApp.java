package fr.needforcode.ihm;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	private Stage primaryStage;
    private AnchorPane mainAppWindow;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        try {
            // Charge le layout pour la fenêtre pricipale
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainAppWindowLayout.fxml"));
            
            this.mainAppWindow = (AnchorPane) loader.load();
            this.mainAppWindow.getStylesheets().add(MainApp.class.getResource("res/css/design.css").toExternalForm());
            
            this.primaryStage.setScene(new Scene(mainAppWindow));
            this.primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
        
	}

	public static void main(String[] args) {
		launch(args);
	}
}
