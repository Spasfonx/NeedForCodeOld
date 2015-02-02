package fr.needforcode.ihm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.needforcode.ihm.MainApp;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * Controller de la fenêtre principale. Contient les composants permettant de 
 * customiser la fenêtre générale.
 * @author Christophe
 * @version 1.0
 */
public class MainAppWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane btnControlPane;
    
    @FXML
    private Button btnControlClose;

    @FXML
    private Label mainAppTitle;

    @FXML
    private AnchorPane mainAppTopBar;

    @FXML
    private AnchorPane mainAppWindow;

    @FXML
    private Pane mainContentPane;

    // Référence à l'application principale
    private MainApp mainApp;
    
    // Variables utiles pour gérer le déplacement de la fenêtre
    private static double xOffset = 0;
    private static double yOffset = 0;

    @FXML
    void initialize() {
    	initDragableWindow();
    	initClosableWindow();
    }
    
    /**
     * Appelée par l'application principale. Permet la référence au point d'entrée de l'application.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }
    
    /**
     * Permet de rendre la fenêtre déplaçable.
     */
    public void initDragableWindow() {
    	mainAppTopBar.setOnMousePressed(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			xOffset = event.getX();
    			yOffset = event.getY();
    		}
    	});
    	
    	mainAppTopBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			mainApp.getPrimaryStage().setX(event.getScreenX() - xOffset);
    			mainApp.getPrimaryStage().setY(event.getScreenY() - yOffset);
    		}
    	});
    }
    
    /**
     * Permet de fermer la fenêtre par clic sur le bouton : [x]
     */
    public void initClosableWindow() {
    	btnControlClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			mainApp.getPrimaryStage().close();
    		}
    	});
    }
    
    

}
