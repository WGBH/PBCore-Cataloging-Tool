package digitalbedrock.software.pbcore.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SettingsVocabulariesController extends AbsController {


    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    
    @FXML
    private TreeView<String> treelist;
    
    
    private final TreeItem<String> rootElements=new TreeItem<>();
    private final TreeItem<String> rootAttributes=new TreeItem<>();
    
    
    // actions
    @FXML
    void onCancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    void onOkButtonClick(ActionEvent event) {
        // TODO: save
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController); 
        mainController.getRegistry().getControlledVocabularies().entrySet().forEach(i->{
            TreeItem<String> t = new TreeItem<>(i.getKey());
            
            
            if (i.getValue().isAttribute() ){
                 rootAttributes.getChildren().add(t);
            }else{
                 rootElements.getChildren().add(t);
            }
        });
        treelist.setShowRoot(false);
        treelist.setRoot(rootElements);
        
    }

    
    @FXML
    public void showElements(ActionEvent e){
        treelist.setRoot(rootElements);
       
    }
    
    @FXML
    public void showAttributes(ActionEvent e){
        treelist.setRoot(rootAttributes);
    }
    
    
    
    

}
