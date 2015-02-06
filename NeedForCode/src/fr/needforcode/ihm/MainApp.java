package fr.needforcode.ihm;

import java.io.IOException;

import fr.needforcode.circuit.Circuit;
import fr.needforcode.circuit.CircuitImpl;
import fr.needforcode.circuit.factory.CircuitFactoryImage;
import fr.needforcode.ihm.controller.CourseRunningController;
import fr.needforcode.ihm.controller.MainAppWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	private Stage primaryStage;
    private AnchorPane mainAppWindow;
    private BorderPane mainContentPane;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        try {
            // Charge le layout de la fenêtre pricipale
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/MainAppWindowLayout.fxml"));
            
            this.mainAppWindow = (AnchorPane) loader.load();
            this.mainAppWindow.getStylesheets().add(MainApp.class.getResource("res/css/design.css").toExternalForm());
            
            MainAppWindowController controller = loader.getController();
            controller.setMainApp(this);
            
            this.mainContentPane = controller.getMainContentPane();
            
            this.primaryStage.setScene(new Scene(mainAppWindow));
            this.primaryStage.show();
        } catch (IOException e) {
        	// Exception levée si le fxml ne peut pas être chargé
            e.printStackTrace();
        }
        
        showCourseRunning();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
     * Shows the person overview scene.
     */
    public void showCourseRunning() {
        try {
        	
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader 		= new FXMLLoader(MainApp.class.getResource("view/CourseRunning.fxml"));
            AnchorPane overviewPage = (AnchorPane) loader.load();
            CourseRunningController controller = loader.getController();
            
            //String PATH_CIRCUIT = "../../../../../png/1_safe.trk.png";
            String PATH_CIRCUIT = MainApp.class.getResource("/png/1_safe.trk.png").toString();
            System.out.println(PATH_CIRCUIT);
            
            CircuitFactoryImage circuitFactory = new CircuitFactoryImage(PATH_CIRCUIT);
            Circuit circuit = circuitFactory.build();
            
            controller.setCircuit(PATH_CIRCUIT, circuit);

            //Image circuitImage = new Image(
            //		MainApp.class.getResource("res/img/background-cars.jpg").toString(),
        	//		300,
        	//		300,
        	//		true,
        	//		true
        	//	);
            
            //ImageView imgV = new ImageView(circuitImage);
            
            //overviewPage.getChildren().add(imgV);
            
            mainContentPane.setCenter(overviewPage);

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
