package fr.needforcode.ihm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.needforcode.ihm.MainApp;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur du menu principal.
 * @author Christophe
 * @version 1.0
 */
public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonLaunch;

    @FXML
    private Button buttonSettings;

    @FXML
    private Group groupButtons;

    @FXML
    private BorderPane mainMenu;
    
    private MainApp mainApp;

    /**
     * Initialise le controlleur du menu principal.
     */
    @FXML
    void initialize() {
    	initButtonLaunch();
    }
    
    /**
     * Initialise le bouton de lancement de lancement d'une partie. 
     * Redirige vers le pré-réglage d'une course.
     */
    private void initButtonLaunch() {
    	buttonLaunch.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					showConfigCourseMenu();
				} catch (Exception e) {
					mainApp.setError(e.getMessage());
				}
			}
    	});
    	
    	buttonSettings.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					mainApp.showCourseRunning_Test();
				} catch (Exception e) {
					//mainApp.setError(e.getMessage());
					e.printStackTrace();
				}
			}
    	});
    }

    /**
     * Affiche la vue de pré-réglage d'une course.
     */
    public void showConfigCourseMenu() {
    	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ConfigCourseMenuLayout.fxml"));
        BorderPane overviewPage = null;
        
    	try {
            overviewPage = (BorderPane) loader.load();
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
        ConfigCourseMenuController controller = loader.getController();
        
        controller.setMainApp(this.mainApp);
        
        mainApp.setMainContent(overviewPage);
    }
    
    /**
     * Sert à faire le lien entre le controlleur et le point d'entrée de l'application.
     * @param m Point d'entrée de l'application.
     */
    public void setMainApp(MainApp m) {
    	this.mainApp = m;
    }

}
