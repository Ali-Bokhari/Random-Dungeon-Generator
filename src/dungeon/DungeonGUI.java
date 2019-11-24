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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.control.TextArea;
import java.io.*;

import java.util.ArrayList;

public class DungeonGUI extends Application {
    //Model
    ArrayList<Space> basicSpaces;
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

        basicSpaces = new ArrayList<>();
        Level newLevel = new Level();
        basicSpaces = newLevel.generate();
        spaces = FXCollections.observableArrayList(basicSpaces);
    }
    @Override
    public void start(Stage stage) throws Exception {
        HBox rootNode = new HBox(20);
        VBox leftNode = new VBox(10);
        Menu fileMenu = new Menu("File");
        MenuBar menubar = new MenuBar();
        menubar.getMenus().add(fileMenu);
        MenuItem m1 = new MenuItem("Save File");
        MenuItem m2 = new MenuItem("Load File");
        fileMenu.getItems().add(m1);
        fileMenu.getItems().add(m2);
        leftNode.getChildren().addAll(menubar, spacesListView, addButton);
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

        m1.setOnAction(e->{
            saveState(stage);
        });

        m2.setOnAction(e->{
            loadState(stage);
        });

        addButton.setOnAction(e->{
            EditStage editStage = new EditStage(spacesListView);
            editStage.show();
        });
        stage.setTitle("Java FX demo");
        stage.setScene(scene);
        stage.show();
    }

    public void saveState(Stage stage) {
      try {
        FileChooser choose = new FileChooser();
        File file = choose.showSaveDialog(stage);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        this.spaces = this.spacesListView.getItems();
        this.basicSpaces = new ArrayList<Space>(this.spaces);
        out.writeObject(this.basicSpaces);
        out.close();
      }
      catch (IOException e) {

      }
    }

    public void loadState(Stage stage) {
      try
        {
          FileChooser choose = new FileChooser();
          File file = choose.showOpenDialog(stage);
          ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
          this.basicSpaces = (ArrayList<Space>)in.readObject();
          this.spaces = FXCollections.observableArrayList(this.basicSpaces);
          this.spacesListView.setItems(this.spaces);
          in.close();
        }

        catch(IOException ex)
        {
          System.out.println(ex);
        }

        catch(ClassNotFoundException ex)
        {
          System.out.println(ex);
        }
    }
    public static void main(String [] args) {
        Application.launch(args);
    }

}
