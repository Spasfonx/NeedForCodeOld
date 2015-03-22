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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Controller de la fen�tre principale. Contient les composants permettant de 
 * customiser la fen�tre g�n�rale.
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
    private Button btnErrorClose;
    
    @FXML
    private AnchorPane mainAppWindow;

    @FXML
    private AnchorPane mainAppTopBar;
    
    @FXML
    private Label mainAppTitle;

    @FXML
    private AnchorPane mainContentPane;
    
    @FXML
    private AnchorPane errorContentPane;
    
    @FXML
    private Text errorText;

    /**
     * R�f�rence � l'application principale
     */
    private MainApp mainApp;
    
    // Variables utiles pour g�rer le d�placement de la fen�tre
    private static double xOffset = 0;
    private static double yOffset = 0;
    
    // Padding
    public static int BORDER_BOTTOM = 5;
    public static int BORDER_TOP   	= 32;
    public static int BORDER_LEFT  	= 5;
    public static int BORDER_RIGHT 	= 5;

    /**
     * Point d'entr�e du controlleur.
     */
    @FXML
    void initialize() {
    	initDragableWindow();
    	initClosableWindow();
    	initClosableBtnErrorError();
    }

    /**
     * Renvois le panel principal. C'est ce panel qui est remplis avec les vues de l'application.
     */
	public AnchorPane getMainContentPane() {
		return this.mainContentPane;
	}
	
	/**
	 * R�gle le clip du Main Content Pane. Tous les composants qui d�passent
	 * des bornes du contenant sont coup�s.
	 */
	public void setClip() {
        this.mainContentPane.setClip(new Rectangle(
        		this.mainApp.getPrimaryStage().getWidth() - BORDER_LEFT + BORDER_RIGHT, // On enl�ve les marges (right)
        		this.mainApp.getPrimaryStage().getHeight() - BORDER_TOP + BORDER_BOTTOM // On enl�ve les marges (top + bottom)
        	));
	}
	
	/**
	 * Affiche une erreur dans le cadre pr�vu � cet effet dans la partie inf�rieure de l'application.
	 * @param message
	 */
	public void setError(String message) {
		this.errorContentPane.setVisible(true);
		this.errorText.setText(message);
		this.errorContentPane.autosize();
	}
	
	/**
	 * Ferme la zone d'erreur.
	 */
	public void closeError() {
		this.errorContentPane.setVisible(false);
		this.errorText.setText("");
	}
	
	 /**
     * Initialise la fermeture de la zone d'erreur sur le click du bouton [x].
     */
    public void initClosableBtnErrorError() {
    	btnErrorClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			closeError();
    		}
    	});
    }
    
    /**
     * Permet de rendre la fen�tre d�pla�able. M�thode non termin�e, m�canisme � revoir.
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
    			if (mainApp.getPrimaryStage().isFullScreen()) {
    				mainApp.getPrimaryStage().setFullScreen(false);
    				setClip();
    			}
    			mainApp.getPrimaryStage().setX(event.getScreenX() - xOffset);
    			mainApp.getPrimaryStage().setY(event.getScreenY() - yOffset);
    		}
    	});
    }
    
    /**
     * Permet de fermer la fen�tre par clic sur le bouton : [x].
     */
    public void initClosableWindow() {
    	btnControlClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			mainApp.getPrimaryStage().close();
    		}
    	});
    }
    
    /**
     * Appel�e par l'application principale. Permet la r�f�rence au point d'entr�e de l'application.
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
    	this.mainApp = mainApp;
    }

}
