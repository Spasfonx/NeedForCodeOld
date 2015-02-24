package fr.needforcode.ihm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.CircuitImpl;
import fr.needforcode.circuit.factory.CircuitFactory;
import fr.needforcode.circuit.factory.CircuitFactoryImage;
import fr.needforcode.ihm.controller.CourseRunningController;
import fr.needforcode.ihm.controller.MainAppWindowController;
import fr.needforcode.ihm.listener.ResizeListener;
import fr.needforcode.ihm.model.CircuitLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	private Stage primaryStage;
	
    private AnchorPane mainAppWindow;
    private AnchorPane mainContentPane;

    private MainAppWindowController mainAppController;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        try {
            // Charge le layout de la fenêtre pricipale
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainAppWindowLayout.fxml"));
            
            this.mainAppWindow = (AnchorPane) loader.load();
            this.mainAppWindow.getStylesheets().add(MainApp.class.getResource("res/css/design.css").toExternalForm());
            
            this.mainAppController = loader.getController();
            this.mainAppController.setMainApp(this);
            
            Scene scene = new Scene(mainAppWindow);
            
            this.primaryStage.setScene(scene);
            this.primaryStage.setFullScreen(true);
            
            this.mainContentPane = this.mainAppController.getMainContentPane();
            
            this.primaryStage.show();
            
        } catch (IOException e) {
        	// Exception levée si le fxml ne peut pas être chargé
            e.printStackTrace();
        }
        
        try {
			showMainMenu();
		} catch (Exception e) {
			this.setError("Impossible de lancer la course : " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
     * Charge les composants et lance la vue qui permet de lancer la course
	 * @throws Exception 
     */
    public void showCourseRunning() throws Exception {
        try {
        	
            FXMLLoader loader 		= new FXMLLoader(MainApp.class.getResource("view/CourseRunning.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            CourseRunningController controller = loader.getController();
            CircuitLoader circuitLoader = new CircuitLoader();
            
            // Charge la liste des circuits
            try { circuitLoader.loadCircuits(); } 
            catch (Exception e) { e.printStackTrace(); }
            
            // Scratch
            String name = "1_safe";
    		String filename_trk = circuitLoader.getTrkPathFromName(name);
    		String filename_img = circuitLoader.getImagePathFromName(name);
    		CircuitFactory cF = new CircuitFactory(filename_trk);
    		Circuit track = cF.build();
    		
    		controller.setMainApp(this);
    		controller.setCircuit(track, filename_img);
    		controller.setVoiture();
            
            //mainContentPane.setCenter(overviewPage);
    		this.setMainContent(overviewPage);
            
            controller.launchCourse();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showMainMenu() {
    	try {
            FXMLLoader loader 		= new FXMLLoader(MainApp.class.getResource("view/MainMenuLayout.fxml"));
            BorderPane overviewPage = (BorderPane) loader.load();
            
            this.setMainContent(overviewPage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Inclus la vue principale.
     * @param n Layout à inclure
     */
    private void setMainContent(Node n) {
    	AnchorPane.setTopAnchor(n, 0d);
    	AnchorPane.setBottomAnchor(n, 0d);
    	AnchorPane.setLeftAnchor(n, 0d);
    	AnchorPane.setRightAnchor(n, 0d);
    	
    	this.mainContentPane.getChildren().clear();
    	this.mainContentPane.getChildren().add(n);
    }
    
    public void setError(String message) {
    	this.mainAppController.setError(message);
    }

    /**
     * Renvois la stage principale.
     * @return Stage
     */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	/**
	 * Renvois le conteneur principal.
	 * @return Conteneur principal
	 */
	public AnchorPane getMainContentPane() {
		return this.mainContentPane;
	}
}
