package fr.needforcode.ihm.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.factory.CircuitFactory;
import fr.needforcode.course.Course;
import fr.needforcode.course.CourseRunningException;
import fr.needforcode.equipe.Equipe;
import fr.needforcode.equipe.NomIncorrectException;
import fr.needforcode.ihm.MainApp;
import fr.needforcode.ihm.model.AssociatedImageException;
import fr.needforcode.ihm.model.CircuitLoader;
import fr.needforcode.ihm.model.EquipeLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class ConfigCourseMenuController {

	private static final int NB_COL_EQUIPES = 3;

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

	private ArrayList<Equipe> equipesData;
	
	private ArrayList<CheckBox> checkBoxesEquipes = new ArrayList<CheckBox>();

    @FXML
    void initialize() {
    	loadCircuitsData();
    	initEventChangedComboCircuit();
    	initClickButtonSubmit();
    	loadEquipesGraphics();
    }
    
    public void setMainApp(MainApp m) {
    	this.mainApp = m;
    }
    
    public void loadEquipesData() {
    	EquipeLoader el = new EquipeLoader();
    	try {
			this.equipesData = el.loadEquipe();
		} catch (NomIncorrectException e) {
			mainApp.setError(e.getMessage());
		}
    }
    
    public void loadEquipesGraphics() {
    	loadEquipesData();
    	
    	if (equipesData == null)
    		mainApp.setError("Il n'y a pas d'équipes à charger, veuillez vérifier votre dossier d'équipes");
    	
    	int column = 0;
    	int row = 0;
    	
    	for(Equipe e : equipesData) {
    		CheckBox cb = new CheckBox("Equipe " + e.getNom());
    		
    		cb.setId(Integer.toString(equipesData.indexOf(e)));
    		cb.setStyle("-fx-text-fill: white");
    		cb.setSelected(true);
    		
    		checkBoxesEquipes.add(cb);
    		gridPlayers.add(cb, column, row);
    		
    		column++;
    		if (column >= NB_COL_EQUIPES) {
    			column = 0;
    			row++;
    		}
    	}
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
    	this.buttonSubmit.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			int nbTours = 0;
    			String nameCircuit = comboBoxCircuit.getValue();
    			String pathImageCircuit = "";
    			
    			try {
    				nbTours = Integer.parseInt(textFieldNbTours.getText());
    				pathImageCircuit = circuitLoader.getImagePathFromName(nameCircuit);
    			} catch (NumberFormatException e) {
    				mainApp.setError("Le nombre de tour doit être un nombre entier");
    			} catch (AssociatedImageException e) {
					mainApp.setError(e.getMessage());
				} catch (IOException e) {
					mainApp.setError(e.getMessage());
				}
    			
    			if (nameCircuit == null)
    				mainApp.setError("Vous n'avez pas selectionné de circuit");
    			
    			if (nbTours <= 0)
    				mainApp.setError("Le nombre de tours ne peut être inférieur ou égal à 0");
    			
				try {
					
					/* Création du circuit */
	    			CircuitFactory cf = null;
	    			Circuit c = null;
	    			Course course = null;
					
					cf = new CircuitFactory(circuitLoader.getTrkPathFromName(nameCircuit));
					c  = cf.build();
					
					/* On instancie la course */
					course = new Course(c, nbTours);
					
					/* Ajout des equipes à la course */
					for(CheckBox cb : checkBoxesEquipes) {
						if (cb.isSelected()) {
							try {
								course.addEquipe(
									equipesData.get(Integer.parseInt(cb.getId()))
								);
							} catch (CourseRunningException e) {
								e.printStackTrace();
							}
						}
					}
					
					mainApp.showCourseRunning(course, pathImageCircuit);
					
				} catch (Exception e) {
					mainApp.setError(e.getMessage());
				}
    		}
    	});
    }
}
