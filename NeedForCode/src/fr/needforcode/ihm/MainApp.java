package fr.needforcode.ihm;

import java.io.IOException;

import fr.needforcode.ihm.controller.MainAppWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
            // Charge le layout de la fenêtre pricipale
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainAppWindowLayout.fxml"));
            
            this.mainAppWindow = (AnchorPane) loader.load();
            this.mainAppWindow.getStylesheets().add(MainApp.class.getResource("res/css/design.css").toExternalForm());
            
            MainAppWindowController controller = loader.getController();
            controller.setMainApp(this);
            
            this.primaryStage.setScene(new Scene(mainAppWindow));
            this.primaryStage.show();
        } catch (IOException e) {
        	// Exception levée si le fxml ne peut pas être chargé
            e.printStackTrace();
        }
        
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
