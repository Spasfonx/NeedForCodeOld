package fr.needforcode.ihm.controller;

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
    	Image circuitImage = new Image(
    			pathImage,
    			300,
            	300,
            	true,
            	true
           );
    	
    	this.circuitContainer.setImage(circuitImage);
    	this.circuit = circuit;
    	
    	//this.courseContainer.setPrefSize(circuit.getWidth(), circuit.getHeight());
    	this.courseContainer.setPrefSize(300, 300);
    }
    
}
