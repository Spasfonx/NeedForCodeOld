package fr.needforcode.ihm;

import java.io.IOException;

import fr.needforcode.course.Course;
import fr.needforcode.ihm.controller.CourseRunningController;
import fr.needforcode.ihm.controller.MainAppWindowController;
import fr.needforcode.ihm.controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Application principale. Sert de controlleur principal.
 * @author Christophe
 * @version 1.0
 */
public class MainApp extends Application {
	
	/**
	 * Stage principale.
	 */
	private Stage primaryStage;
	
	/**
	 * Panel de la fen�tre principale
	 */
    private AnchorPane mainAppWindow;
    
    /**
     * Panel du contenu principal
     */
    private AnchorPane mainContentPane;

    /**
     * Controlleur de la fen�tre principale
     */
    private MainAppWindowController mainAppController;
    
    /**
     * Nombre d'images par secondes. Vitesse de la boucle de jeu
     */
    public final static int FRAMES_PER_SECOND = 60;

    /**
     * Lancement de l'application.
     */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        /* Charge le layout de la fen�tre pricipale */
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainAppWindowLayout.fxml"));
        
        try {   
            this.mainAppWindow = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /* Charge les feuilles de styles */
        this.mainAppWindow.getStylesheets().add(MainApp.class.getResource("res/css/design.css").toExternalForm());
        
        /* Charge le controller */
        this.mainAppController = loader.getController();
        this.mainAppController.setMainApp(this);
        
        this.mainContentPane = this.mainAppController.getMainContentPane();
        
        Scene scene = new Scene(mainAppWindow);
        
        /* Configuration et affichage de la fen�tre */
        this.primaryStage.setScene(scene);
        this.primaryStage.setFullScreen(true);
        this.primaryStage.show();
            
        /* Affichage du menu principal */
		showMainMenu();
	}

	/**
	 * Point d'entr�e du programme.
	 * @param args - Arguments d'entr�e 
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
     * Charge les composants et lance la vue qui permet de lancer la course.
	 * @throws Exception 
     */
	public void showCourseRunning(Course course, String pathImageCircuit) throws Exception {
        try {
        	
            FXMLLoader loader 		= new FXMLLoader(MainApp.class.getResource("view/CourseRunning.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            CourseRunningController controller = loader.getController();
    		
    		controller.setMainApp(this);
    		controller.setCircuit(course.getCircuit(), pathImageCircuit);
    		controller.setCourse(course);
            
    		this.setMainContent(overviewPage);
            
            controller.launchCourse();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Affiche la vue du menu principal.
	 */
    public void showMainMenu() {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainMenuLayout.fxml"));
        BorderPane overviewPage = null;
        
    	try {
            overviewPage = (BorderPane) loader.load();
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
        MainMenuController controller = loader.getController();
        
        controller.setMainApp(this);
        
        this.setMainContent(overviewPage);
    }
    
    /**
     * Inclus la vue principale.
     * @param n Layout � inclure
     */
    public void setMainContent(Node n) {
    	AnchorPane.setTopAnchor(n, 0d);
    	AnchorPane.setBottomAnchor(n, 0d);
    	AnchorPane.setLeftAnchor(n, 0d);
    	AnchorPane.setRightAnchor(n, 0d);
    	
    	this.mainContentPane.getChildren().clear();
    	this.mainContentPane.getChildren().add(n);
    }
    
    /**
     * Affiche les messages d'erreurs.
     * @param message Message de l'erreur.
     */
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
