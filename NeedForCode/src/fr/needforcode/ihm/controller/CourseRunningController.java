package fr.needforcode.ihm.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.CircuitImpl;
import fr.needforcode.geometrie.Vecteur;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.ihm.MainApp;
import fr.needforcode.voiture.Commande;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureException;
import fr.needforcode.voiture.factory.MiageCarFactory;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2DBuilder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class CourseRunningController {
	
	/* Composants FXML */
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane courseContainer;
    
    @FXML
    private ImageView circuitContainer;

    @FXML
    private Label labelTest;
    
    /* Composants de classe */
    
    private MainApp mainApp;
    
    private Circuit circuit;
    private Voiture voiture;
    
    private Rectangle voitureImg;
    
    private double scaleX;
    private double scaleY;
    
    private final int TAILLE_LONGUEUR_VOITURE = 23;
    private final int TAILLE_LARGEUR_VOITURE = 12;

    @FXML
    void initialize() {
    }
    
    @SuppressWarnings("deprecation")
	public void launchCourse() {
    	Timeline timeline = new Timeline();
    	
    	MiageCarFactory miageCarFactory = new MiageCarFactory(this.circuit);
    	this.voiture = miageCarFactory.build();
    	
    	final ArrayList<ArrayList<Commande>> coms = new ArrayList<ArrayList<Commande>>(); //Contiendra toutes les commandes a executer
		
		//on determine les ArrayList de commandes :
		final ArrayList<Commande> c1 = new ArrayList<Commande>();
        for(int i=0; i<200; i++) c1.add(new Commande(1,0.005)); //tout droit à fond en serrant a gauche
        
        final ArrayList<Commande> c2 = new ArrayList<Commande>();
        for(int i=0; i<200; i++) c2.add(new Commande(0.1,0.065)); // tourner large à gauche sans accélerer
        
        final ArrayList<Commande> c3 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<200; i++) c3.add(new Commande(1,0));
        
        final ArrayList<Commande> c4 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<200; i++) c4.add(new Commande(0.3,-0.03));
        
        final ArrayList<Commande> c5 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<100; i++) c5.add(new Commande(1,0.001));
        
        final ArrayList<Commande> c6 = new ArrayList<Commande>(); //etc....
        for(int i=0; i<100; i++) c6.add(new Commande(0.1,0.065));
        
        // on ajoute toutes les ArrayList de commande à l'ArrayList générale
        coms.add(c1);
        coms.add(c2);
        coms.add(c3);
        coms.add(c4);
        coms.add(c5);
        coms.add(c6);
    	
    	final Duration oneFrameAmt = Duration.millis(1000/60);
		final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
	    	new EventHandler<ActionEvent>() {
				private int indexComs = 0;
				private int indexCg = 0;
				private Vecteur prevDirection = voiture.getDirection().cloneAsVecteur();
				
				@Override
				public void handle(ActionEvent arg0) {
					Commande c = coms.get(indexComs).get(indexCg);
					
					if(indexCg < coms.get(indexComs).size() - 1) {
						indexCg++;
					}
					else {
						if (indexComs < coms.size() - 1)
							indexComs++;
							
						indexCg = 0;
					}
					
					try {
						voiture.piloter(c);
					} catch (VoitureException e) {
						e.printStackTrace();
					}
					
					setPositionVoiture(voiture.getPosition().getY(), voiture.getPosition().getX()); // Les X et les Y sont inversés dans l'objet voiture
					setDirectionVoiture( 
							Math.toDegrees( vTools.angle(prevDirection, voiture.getDirection()) ) 
						);
					
					this.prevDirection = voiture.getDirection().cloneAsVecteur(); 
				}
	    	}); // oneFrame
    	 
    	// Boucle de la course (Timeline)
    	timeline = (TimelineBuilder.create()
	    	.cycleCount(Animation.INDEFINITE)
	    	.keyFrames(oneFrame)
	    	.build());
    	
    	timeline.play();
    }
    
    /**
     * Charge le circuit sur lequel va se dérouler la course
     * 
     * @param circuit
     * @param pathImage
     */
    public void setCircuit(Circuit circuit, String pathImage) {
    	// On adapte le path pour le composant Image
    	pathImage = "file:" + File.separator + pathImage;
    	
    	this.circuit = circuit;
    	this.refreshScale();
    	
    	Image circuitImage = new Image(
    			pathImage,
    			circuit.getWidth() * scaleX,
            	circuit.getHeight() * scaleY,
            	false,
            	true
           );
    	
    	this.circuitContainer.setImage(circuitImage);
    	this.courseContainer.setPrefSize(circuit.getHeight() * scaleY, circuit.getWidth() * scaleX);
    	//this.circuit = circuit;
    }
    
    public void setVoiture() {
    	this.voitureImg = new Rectangle(
    			(int) circuit.getPointDepart().getY() * scaleX, 
    			(int) circuit.getPointDepart().getX() * scaleY, 
    			this.TAILLE_LONGUEUR_VOITURE * scaleY,
    			this.TAILLE_LARGEUR_VOITURE * scaleX
    		);
    	this.voitureImg.setFill(Color.BLUE);
    	this.courseContainer.getChildren().add(voitureImg);
    }
    
    /**
     * Règle la position de la voiture sur le circuit (graphiquement).
     * @param x
     * @param y
     */
    private void setPositionVoiture(double x, double y) {
    	this.voitureImg.setX(x * scaleX);
		this.voitureImg.setY(y * scaleY);
    }
    
    /**
     * Tourne la voiture en fonction de sa direction et de l'angle donné en paramètre (graphiquement).
     * @param angle Angle de rotation
     */
    private void setDirectionVoiture(double angle) {
    	this.voitureImg.setRotate(voitureImg.getRotate() - angle);
    }
    
    public void setMainApp(MainApp m) {
    	this.mainApp = m;
    }
    
    public void refreshScale() {
    	int borderWidth = MainAppWindowController.BORDER_LEFT + MainAppWindowController.BORDER_RIGHT;
    	int borderHeight = MainAppWindowController.BORDER_TOP + MainAppWindowController.BORDER_BOTTOM;    	
    	
    	// Règle de trois pour trouver la proportion du circuit en % par rapport à la taille de la fenêtre (moins les bordures)
    	double proportionX = ((circuit.getWidth() * 100) / 
    						 	(this.mainApp.getPrimaryStage().getWidth() - borderWidth));
    	double proportionY = ((circuit.getHeight() * 100) / 
    						 	(this.mainApp.getPrimaryStage().getHeight() - borderHeight));
    	
    	this.scaleX = 100 / proportionX;
    	this.scaleY = 100 / proportionY;
    }
    
}
