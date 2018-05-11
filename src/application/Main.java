package application;
	
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import controller.ValuesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import model.ValuesModel;

public class Main extends Application {
	
	private Stage primaryStage;
    private BorderPane root;
	
    public Main() {
    	ArrayList<String> values = new ArrayList<String>(5);
    	try {
			BufferedReader in = new BufferedReader(new FileReader("values"));
			String line;
			while((line = in.readLine()) != null)
			{
			    values.add(line);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ValuesModel.projectPath = values.get(0);
    	ValuesModel.recoverPath = values.get(1);
    	ValuesModel.archivesPath = values.get(2);
    	ValuesModel.filetype = values.get(3);
    }
    
	@Override
	public void start(Stage primaryStage) {
		try {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("BackupApp");

            initRoot();
            
            initChild();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initRoot() {
        try {
        	URL location = getClass().getResource("/view/Root.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
			root = (BorderPane) fxmlLoader.load();
			Scene scene = new Scene(root);
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initChild() {
        try {
            // Загружаем сведения об адресатах.
        	URL location = getClass().getResource("/view/Values.fxml");
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(location);
            AnchorPane mainLayout = (AnchorPane) loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            root.setCenter(mainLayout);
            
            // Даём контроллеру доступ к главному приложению.
            ValuesController controller = loader.getController();
            //controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
