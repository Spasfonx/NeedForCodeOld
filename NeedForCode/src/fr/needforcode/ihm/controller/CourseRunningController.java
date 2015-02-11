package fr.needforcode.ihm.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.CircuitImpl;
import fr.needforcode.ihm.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CourseRunningController {

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
    
    private Circuit circuit;

    @FXML
    void initialize() { 
    }
    
    public void setCircuit(Circuit circuit, String pathImage) {
    	// On adapte le path pour le composant Image
    	pathImage = "file:" + File.separator + pathImage;
    	
    	System.out.println(circuit.getHeight() + " " + circuit.getWidth());
    	
    	Image circuitImage = new Image(
    			pathImage,
    			circuit.getWidth(),
            	circuit.getHeight(),
            	true,
            	true
           );
    	
    	this.circuitContainer.setImage(circuitImage);
    	this.circuit = circuit;
    	
    	this.courseContainer.setPrefSize(circuit.getHeight(), circuit.getWidth());
    }
    
}
