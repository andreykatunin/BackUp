package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.ValuesModel;
import backup.MainBackup;
import model.FoundFile;

public class ValuesController {

    @FXML
    private TextField projectPath;
    @FXML
    private TextField recoverPath;
    @FXML
    private TextField archivesPath;
    @FXML
    private TextField filetype;
    @FXML
    private TextField recoverArchieve;
    
    @FXML
    private Button btnSsave;
    @FXML
    private Button btnZip;
    @FXML
    private Button btnRecover;
    
    @FXML
    private TableView<FoundFile> tvFiles;
    @FXML
    private TableColumn<FoundFile, String> nameField;
    @FXML
    private TableColumn<FoundFile, String> pathField;

    // Ссылка на главное приложение.
    //private Main main;
    
    private ObservableList<FoundFile> filesInfo = FXCollections.observableArrayList();
    private Map<String, File> filesWithPath = new HashMap<String, File>();
	private Main main;
	
    public ValuesController() {
    }
    
    @FXML
    private void initialize() {
        // Инициализация таблицы адресатов с двумя столбцами.
    	System.out.println("initialize");
    	
    	nameField.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	pathField.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
    	
    	projectPath.setText(ValuesModel.projectPath);
    	recoverPath.setText(ValuesModel.recoverPath);
    	archivesPath.setText(ValuesModel.archivesPath);
    	filetype.setText(ValuesModel.filetype);
    	//recoverArchieve.setText(ValuesModel.recoverArchieve);
    	
    }
    
    @FXML
    private void btnSaveAction() throws FileNotFoundException {
    	System.out.println("Button click");
    	ValuesModel.projectPath = projectPath.getText();
    	ValuesModel.recoverPath = recoverPath.getText();
    	ValuesModel.archivesPath = archivesPath.getText();
    	ValuesModel.filetype = filetype.getText();
    	
    	PrintWriter writer = new PrintWriter("values");
    	writer.print("");
    	writer.print(ValuesModel.projectPath+ "\r\n");
    	writer.print(ValuesModel.recoverPath+ "\r\n");
    	writer.print(ValuesModel.archivesPath+ "\r\n");
    	writer.print(ValuesModel.filetype);
    	writer.close();
    }
    
    @FXML
    private void btnZipAction() throws IOException {
    	System.out.println("ZIP click");
    	MainBackup.main();
    	filesInfo.clear();
    	System.out.print(filesInfo.toString());
    	tvFiles.getItems().clear();
    	tvFiles.setItems(filesInfo);
    	filesWithPath = MainBackup.getMap();
    	for (String key : filesWithPath.keySet()) {
    		filesInfo.add(new FoundFile(filesWithPath.get(key).getName(), key));
    	}
    	tvFiles.setItems(filesInfo);
    }
    
    @FXML
    private void btnRecoverAction() throws IOException {
    	System.out.println("Recover click");
    	ValuesModel.recoverArchieve = recoverArchieve.getText();
    	MainBackup.recover();
    }

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.main = mainApp;
    }
}
