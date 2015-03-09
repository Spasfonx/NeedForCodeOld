package fr.needforcode.ihm.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import fr.needforcode.ihm.MainApp;
import fr.needforcode.ihm.model.CircuitLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class ConfigCourseMenuController {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonSubmit;

    @FXML
    private BorderPane configCourseMenu;

    @FXML
    private GridPane gridPlayers;

    @FXML
    private Label labelCircuit;

    @FXML
    private Label labelNbTours;

    @FXML
    private HBox lineCircuit;

    @FXML
    private HBox lineNbTours;

    @FXML
    private VBox linesSettingsCourse;

    @FXML
    private VBox mainContentCourseMenu;

    @FXML
    private Text textChoosePlayers;

    @FXML
    private TextField textFieldNbTours;

    @FXML
    private HBox titleChoosePlayers;

    @FXML
    private HBox titleSettingsCourse;
    
    @FXML
    private ComboBox<String> comboBoxCircuit;
    
    @FXML
    private StackPane circuitPreviewPane;
    
    @FXML
    private ImageView circuitPreviewContainer;

	private MainApp mainApp;
	
	private ArrayList<String> circuitsData = new ArrayList<String>();
	
	private CircuitLoader circuitLoader = new CircuitLoader();

    @FXML
    void initialize() {
    	/* On nettois la liste des exemples javafx */
    	comboBoxCircuit.getItems().clear();
    	
    	loadCircuitsData();
    	initEventChangedComboCircuit();
    }
    
    public void setMainApp(MainApp m) {
    	this.mainApp = m;
    }
    
    public void loadCircuitsData() {
    	try {
    		/* Chargement des circuits */
			this.circuitLoader.loadCircuits();
		} catch (Exception e) {
			mainApp.setError(e.getMessage());
		}
    	
    	/* Tri la liste des circuits */
    	circuitsData.addAll(circuitLoader.getCircuits().keySet());
    	Collections.sort(circuitsData);
    	
    	/* Remplissage de la liste de déroulante */
    	for(String name : circuitsData) {
			comboBoxCircuit.getItems().add(name);
		}
    }
    
    public void initEventChangedComboCircuit() {
    	this.comboBoxCircuit.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String name) {
        		try {
					String filename_img = "file:" + File.separator + circuitLoader.getImagePathFromName(name);
					
					Image circuitPreview = new Image(
	        						filename_img, 
	        						500, 
	        						350, 
	        						false, 
	        						true
	        				);
					
					circuitPreviewPane.setVisible(true);
					circuitPreviewPane.setOpacity(0.8);
					
					circuitPreviewContainer.setImage(circuitPreview);
					circuitPreviewContainer.autosize();
				} catch (Exception e) {
					//mainApp.setError(e.getMessage());
					e.printStackTrace();
				}
        		
              }    
          });
    }

    public void initClickButtonSubmit() {
    }
}
