package fr.needforcode.ihm.controller;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.Terrain;
import fr.needforcode.circuit.TerrainTools;
import fr.needforcode.course.Course;
import fr.needforcode.course.CourseRunningException;
import fr.needforcode.course.EtatCourse;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.geometrie.vTools;
import fr.needforcode.ihm.MainApp;
import fr.needforcode.voiture.Voiture;
import fr.needforcode.voiture.VoitureException;
import fr.needforcode.voiture.VoitureImpl;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Controlleur du déroulement de la course.
 * @author Christophe
 * @version 1.0
 */
public class CourseRunningController {
	
	/**
	 * La marge X entre l'infobulle du nom de l'équipe et la voiture.
	 */
    private static final double TOOLTIP_OFFSET_X = 10;

    /**
	 * La marge Y entre l'infobulle du nom de l'équipe et la voiture.
	 */
	private static final double TOOLTIP_OFFSET_Y = 10;
	
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
    private Button btnStopCourse;
    
    /* Composants de classe */
    
    /**
     * Référence à l'application principale.
     */
    private MainApp mainApp;
    
    /**
     * Course en cours de déroulement.
     */
    private Course course;
    
    /**
     * Circuit.
     */
    private Circuit circuit;
    
    /**
     * Liste objets graphiques liés au voitures.
     */
    private HashMap<Voiture, Rectangle> listeVoitureGraphics;
    
    /**
     * Liste des tooltips contenant les noms des équipes. Ces tooltips suivent les voitures
     * sur le circuit.
     */
    private HashMap<Voiture, Text> listeVoitureTooltip;
    
    /**
     * Timeline pour l'animation des objets.
     */
    private Timeline timeline;
    
    /**
     * Echelle appliquée sur l'axe X. 
     */
    private double scaleX;
    
    /**
     * Echelle appliquée sur l'axe Y.
     */
    private double scaleY;
    
    /**
     * Deux clicks pour valider la fermeture de la course.
     */
    private boolean verifBtnStopCourse = false;

    /**
     * Initialisation du controlleur.
     */
    @FXML
    void initialize() {
    	this.listeVoitureGraphics = new HashMap<Voiture, Rectangle>();
    	this.listeVoitureTooltip = new HashMap<Voiture, Text>();
    	
    	initBtnStopCourseClick();
    }
    
    /**
     * Lance la course.
     */
   	public void launchCourse() {
    	this.course.setEtatCourse(EtatCourse.RUN);
    	
       	initVoituresGraphics();
       	buildAndSetGameLoop();
       	beginGameLoop();
    }
    
   	/**
   	 * Initialise la boucle de jeu.
   	 */
    private void buildAndSetGameLoop() {
    	
    	final Duration oneFrameAmt = Duration.millis(1000/MainApp.FRAMES_PER_SECOND);
   		final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
   	    	new EventHandler<ActionEvent>() {
   				
   				@Override
   				public void handle(ActionEvent arg0) {
   					try {
   						course.avancer();
   						updateVoituresGraphics(course.getListeVoitures());
   					} catch (VoitureException e) {
   						e.printStackTrace();
   					} 
   				}
   	    	});
       	
       	this.timeline = new Timeline(oneFrame);
       	this.timeline.setCycleCount(Animation.INDEFINITE);
       	
    }
    
    /**
     * Lance l'animation la boucle de jeu.
     */
    public void beginGameLoop() {
    	this.timeline.play();
    }
    
    /**
     * Met à jour la position des composants graphiques associés aux voitures.
     * @param voitures Liste des voitures concernées.
     */
    public void updateVoituresGraphics(HashMap<Equipe, Voiture> voitures) {
    	for(Voiture v : voitures.values()) {
    		setPositionVoiture(v, listeVoitureGraphics.get(v));
			setDirectionVoiture(v);
    	}
    }
    
    public void checkVoitureHorsCircuit(Voiture v) {
    	Terrain currentPixel = course.getCircuit().getTerrain(v.getPosition());
    	if (!TerrainTools.isRunnable(currentPixel))
    		System.out.println("Voiture out");
    }
    
    /**
     * Change/associe la course au controlleur.
     * @param c Course
     * @throws CourseRunningException Si une course est déjà en cours.
     */
    public void setCourse(Course c) throws CourseRunningException {
    	if (this.course != null && this.course.getEtat().equals(EtatCourse.RUN))
    		throw new CourseRunningException("Impossible de modifier la course lorsque celle-ci est en cours");
    	
    	this.course = c;
    }
    
    /**
     * Charge le circuit sur lequel va se dérouler la course.
     * @param circuit Circuit sur lequel se déroule la course.
     * @param pathImage Chemin d'accès à l'image représentant le circuit.
     */
    public void setCircuit(Circuit circuit, String pathImage) {
    	/* On adapte le path pour le composant Image */
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
    }
    
    /**
     * Initialise la liste des composants graphiques associés aux voitures.
     */
    public void initVoituresGraphics() {
    	if (this.course != null) {
    		for( Entry<Equipe, Voiture> e : course.getListeVoitures().entrySet() ) {
    			Rectangle voitureGraphics = new Rectangle(
	    	    			(int) circuit.getPointDepart().getY() * scaleX, 
	    	    			(int) circuit.getPointDepart().getX() * scaleY, 
	    	    			23 * scaleY,
	    	    			12 * scaleX
						);
    			
    			Random r = new Random();
    			
    	    	voitureGraphics.setFill(Color.rgb(
    	    			r.nextInt(255), 
    	    			r.nextInt(255),
    	    			r.nextInt(255))
    	    	);
    	    	
    	    	Text tooltip = new Text(e.getKey().getNom());
    	    	tooltip.setFill(Color.WHITE);
    	    	tooltip.setX(voitureGraphics.getX() - TOOLTIP_OFFSET_X);
    	    	tooltip.setY(voitureGraphics.getY() - TOOLTIP_OFFSET_Y);
    	    	
    	    	this.courseContainer.getChildren().add(tooltip);
    	    	this.courseContainer.getChildren().add(voitureGraphics);
    			this.listeVoitureGraphics.put(e.getValue(), voitureGraphics);
    			this.listeVoitureTooltip.put(e.getValue(), tooltip);
    		}
    	}
    }
    
    private void initBtnStopCourseClick() {
    	btnStopCourse.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		
			@Override
			public void handle(MouseEvent arg0) {
				if (!verifBtnStopCourse) {
					btnStopCourse.setText("Etes-vous sûr ?");
					btnStopCourse.setStyle("-fx-background-color: firebrick");
					verifBtnStopCourse = true;
				} else {
					btnStopCourse.setText("Arrêter la course");
					btnStopCourse.setStyle("-fx-background-color: #3e3e3e");
					course.setEtatCourse(EtatCourse.END);
					timeline.stop();
					mainApp.showMainMenu();
				}
			}
			
    	});
    	
    	btnStopCourse.setOnMouseExited(new EventHandler<MouseEvent>() {
    		
			@Override
			public void handle(MouseEvent arg0) {
				verifBtnStopCourse = false;
				btnStopCourse.setText("Arrêter la course");
				btnStopCourse.setStyle("-fx-background-color: #3e3e3e");
			}
			
    	});
    }
    
    /**
     * Change la position d'un composant graphique associé à une voiture en fonction de la position
     * de l'objet Voiture.
     * @param v Voiture associée
     * @param r Composant graphique concerné
     */
    private void setPositionVoiture(Voiture v, Rectangle r) {
    	r.setX(v.getPosition().getY() * scaleX); // Les X et les Y sont inversés dans l'objet voiture
		r.setY(v.getPosition().getX() * scaleY);
		this.listeVoitureTooltip.get(v).setX(r.getX() - TOOLTIP_OFFSET_X);
		this.listeVoitureTooltip.get(v).setY(r.getY() - TOOLTIP_OFFSET_Y);
    }
    
    /**
     * Effectue une rotation sur le composant graphique associé à la voiture en fonction de la direction de
     * l'objet Voiture.
     * @param v Voiture associée
     */
    private void setDirectionVoiture(Voiture v) {
    	double angle = Math.toDegrees(vTools.angle(circuit.getDirectionDepart(), v.getDirection()));
    	this.listeVoitureGraphics.get(v).setRotate(-angle);
    }
    
    /**
     * Sert à faire le lien entre le controlleur et le point d'entrée de l'application.
     * @param m Point d'entrée de l'application.
     */
    public void setMainApp(MainApp m) {
    	this.mainApp = m;
    }
    
    /**
     * Rafraichi l'échelle en fonction de la taille de la fenêtre.
     */
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
