package fr.needforcode.ihm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class MainAppWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private AnchorPane mainAppWindow;

    @FXML
    private AnchorPane mainAppTopBar;
    
    @FXML
    private Label mainAppTitle;

    @FXML
    private BorderPane mainContentPane;
    
    @FXML
    private AnchorPane btnControlPane;
    
    @FXML
    private Button btnControlClose;


    @FXML
    void initialize() { }
    
    public BorderPane getMainContentPane() {
    	return this.mainContentPane;
    }

}
