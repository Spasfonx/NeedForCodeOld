package fr.needforcode.ihm.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainStage extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("NeedForCode");
        
        try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(MainStage.class.getResource("view/MainStageLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
        
        this.primaryStage.setWidth(1280);
        this.primaryStage.setHeight(720);
        
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
