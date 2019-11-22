package dungeon;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import dnd.die.Percentile;
import dnd.models.Monster;
import dnd.models.Treasure;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.Label;

public class EditStage extends javafx.stage.Stage {

  private Scene editScene;
  private Button monsterButton;
  private Button treasureButton;
  private Button doorButton;
  private VBox edits;
  private HBox monsterHbox;
  private HBox hedits;
  private ObservableList<String> monsterList;
  private ListView<String> monsters;
  private Spinner<Integer> monsterSpinner;
  private Label monsterLabel;
  private HBox treasureHbox;
  private ObservableList<String> treasureList;
  private ListView<String> treasures;
  private Spinner<Integer> treasureSpinner;
  private Label treasureLabel;
  public EditStage(ListView<Space> spacesListView) {
    super();
    edits = new VBox(10);
    monsterHbox = new HBox(10);
    treasureHbox = new HBox(10);
    hedits = new HBox(20);
    monsterLabel = new Label("Pick monster with 1-100 roll");
    treasureLabel = new Label("Pick treasure with 1-100 roll");
    monsterButton = new Button("Add Monster");
    treasureButton = new Button("Add Treasure");
    doorButton = new Button("Add Door");
    monsterList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getMonsters());
    monsters = new ListView<String>(monsterList);
    monsterSpinner = new Spinner<Integer>(1, 100, 0);
    treasureList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getTreasureList());
    treasures = new ListView<String>(treasureList);
    treasureSpinner = new Spinner<Integer>(1, 100, 0);

    monsterHbox.getChildren().addAll(monsterSpinner, monsterButton);
    treasureHbox.getChildren().addAll(treasureSpinner, treasureButton);
    edits.getChildren().addAll(monsterLabel, monsterHbox, treasureLabel, treasureHbox);
    hedits.getChildren().addAll(edits, monsters, treasures);

    Percentile d100 = new Percentile();

    monsterButton.setOnAction(e->{
      Monster m = new Monster();
      m.setType(monsterSpinner.getValue());
      spacesListView.getSelectionModel().getSelectedItem().addMonster(m);
      monsterList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getMonsters());
      monsters.setItems(monsterList);
    });

    treasureButton.setOnAction(e->{
      Treasure t = new Treasure();
      t.chooseTreasure(d100.roll());
      spacesListView.getSelectionModel().getSelectedItem().addTreasure(t);
      treasureList = FXCollections.observableArrayList(spacesListView.getSelectionModel().getSelectedItem().getTreasureList());
      treasures.setItems(treasureList);
    });

    doorButton.setOnAction(e->{
        spacesListView.getSelectionModel().getSelectedItem().addDoor();
    });

    editScene = new Scene(hedits, 800, 150);
    this.setScene(editScene);
  }
}
