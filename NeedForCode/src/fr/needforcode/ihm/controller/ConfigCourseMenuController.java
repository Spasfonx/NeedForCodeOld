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

/**
 * Controlleur de la vue ConfigCourseMenuLayout. Permet le pr�-r�glage d'une course avant son lancement.
 * @author Christophe
 * @version 1.0
 */
public class ConfigCourseMenuController {

	/**
	 * Nombre de colonnes dans la grille pour l'affichage des �quipes.
	 */
	private final static int NB_COL_EQUIPES = 3;
	
	/**
	 * Nombre de tour par defaut.
	 */
	private final static int NB_TOUR_DEFAUT = 20;

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

    /**
     * R�f�rence � l'application principale.
     */
	private MainApp mainApp;
	
	/**
	 * Liste des noms des circuits.
	 */
	private ArrayList<String> circuitsData = new ArrayList<String>();
	
	/**
	 * CircuitLoader. Utile pour charger les circuits ainsi que leurs images associ�es.
	 */
	private CircuitLoader circuitLoader = new CircuitLoader();

	/**
	 * Liste des �quipes dans le dossier concern�.
	 */
	private ArrayList<Equipe> equipesData;
	
	/**
	 * Liste des cases � coch�es associ�es aux �quipes.
	 */
	private ArrayList<CheckBox> checkBoxesEquipes = new ArrayList<CheckBox>();

	/**
	 * Initialisation du controlleur.
	 */
    @FXML
    void initialize() {
    	this.textFieldNbTours.setText(Integer.toString(NB_TOUR_DEFAUT));
    	
    	loadCircuitsData();
    	initEventChangedComboCircuit();
    	initClickButtonSubmit();
    	loadEquipesGraphics();
    }
    
    /**
     * Charge la liste des �quipes depuis le r�pertoire concern�.
     */
    public void loadEquipesData() {
    	EquipeLoader el = new EquipeLoader();
    	try {
			this.equipesData = el.loadEquipe();
		} catch (NomIncorrectException e) {
			mainApp.setError(e.getMessage());
		}
    }
    
    /**
     * Alimente la grille de choix d'�quipe avec des cases � cocher.
     */
    public void loadEquipesGraphics() {
    	loadEquipesData();
    	
    	if (equipesData == null)
    		mainApp.setError("Il n'y a pas d'�quipes � charger, veuillez v�rifier votre dossier d'�quipes");
    	
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
    
    /**
     * Charge les informations des circuits et alimente la liste d�roulante.
     */
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
    	
    	/* Remplissage de la liste de d�roulante */
    	for(String name : circuitsData) {
			comboBoxCircuit.getItems().add(name);
		}
    }
    
    /**
     * Initialise l'�v�nement sur la liste d�roulante des circuits. Lance une pr�visualisation du circuit
     * � chaque changement d'index.
     */
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
					mainApp.setError(e.getMessage());
				}
        		
              }    
          });
    }

    /**
     * Initialise le bouton de lancement de course apr�s que les r�glages soient faits.
     * NOTE : M�thode � refactoriser.
     */
    public void initClickButtonSubmit() {
    	this.buttonSubmit.setOnMouseClicked(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			int nbTours = 0;
    			int nbErreurs = 0;
    			String nameCircuit = comboBoxCircuit.getValue();
    			String pathImageCircuit = "";
    			
    			/* V�rification des champs */
    			try {
    				nbTours = Integer.parseInt(textFieldNbTours.getText());
    				pathImageCircuit = circuitLoader.getImagePathFromName(nameCircuit);
    			} catch (NumberFormatException e) {
    				mainApp.setError("Le nombre de tour doit �tre un nombre entier");
    				nbErreurs++;
    			} catch (AssociatedImageException e) {
					mainApp.setError(e.getMessage());
					nbErreurs++;
				} catch (IOException e) {
					mainApp.setError(e.getMessage());
					nbErreurs++;
				}
    			
    			if (nameCircuit == null) {
    				mainApp.setError("Vous n'avez pas selectionn� de circuit");
    				nbErreurs++;
    			}
    			
    			if (nbTours <= 0) {
    				mainApp.setError("Le nombre de tours ne peut �tre inf�rieur ou �gal � 0");
    				nbErreurs++;
    			}
    			
    			if (nbErreurs == 0) {
    				
					try {
						/* Cr�ation du circuit */
		    			CircuitFactory cf = null;
		    			Circuit c = null;
		    			Course course = null;
						
						cf = new CircuitFactory(circuitLoader.getTrkPathFromName(nameCircuit));
						c  = cf.build();
						
						/* On instancie la course */
						course = new Course(c, nbTours);
						
						/* Ajout des equipes � la course */
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
    		} 
    	});
    }
    
    /**
     * Sert � faire le lien entre le controlleur et le point d'entr�e de l'application.
     * @param m Point d'entr�e de l'application.
     */
    public void setMainApp(MainApp m) {
    	this.mainApp = m;
    }
}
