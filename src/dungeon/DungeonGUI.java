package dungeon;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class DungeonGUI extends Application {
    //Model
    ObservableList<Space> spaces;
    ObservableList<Door> doorList;
    private ListView<Space> spacesListView;
    private ListView<Door> doorListView;
    private Button addButton;
    private TextArea descriptionText;
    public DungeonGUI(){
        initChamber();
        spacesListView = new ListView<Space>(spaces);
        doorList = FXCollections.observableArrayList();
        doorListView = new ListView<Door>(doorList);
        addButton = new Button("Edit");
        descriptionText = new TextArea();
        //descriptionText.setPrefS(250);
    }
    public void initChamber() {

        ArrayList<Space> spaces2 = new ArrayList<>();
        Level newLevel = new Level();
        spaces2 = newLevel.generate();
        spaces = FXCollections.observableArrayList(spaces2);
    }
    @Override
    public void start(Stage stage) throws Exception {
        HBox rootNode = new HBox(20);
        VBox leftNode = new VBox(10);
        leftNode.getChildren().addAll(spacesListView, addButton);
        rootNode.getChildren().addAll(leftNode,descriptionText,doorListView);
        Scene scene = new Scene(rootNode, 1000, 400);

        spacesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        spacesListView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            doorList.clear();
            doorList.addAll(spacesListView.getSelectionModel().getSelectedItem().getDoors());
            descriptionText.setText(spacesListView.getSelectionModel().getSelectedItem().getDescription());
        });

        doorListView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Stage doorStage = new Stage();
            TextArea doorText = new TextArea();
            doorStage.setTitle("Door description");
            Scene doorScene = new Scene(doorText);
            doorText.setText(doorListView.getSelectionModel().getSelectedItem().getDescription());
            //doorList.addAll(spacesListView.getSelectionModel().getSelectedItem().getDoors());
            //descriptionText.setText(spacesListView.getSelectionModel().getSelectedItem().getDescription());
            doorStage.setScene(doorScene);
            doorStage.show();
        });

        addButton.setOnAction(e->{
            EditStage editStage = new EditStage(spacesListView);
            editStage.show();
        });
        stage.setTitle("Java FX demo");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String [] args) {
        Application.launch(args);
    }

}
